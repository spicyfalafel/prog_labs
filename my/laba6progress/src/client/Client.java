package client;

import com.Request;
import com.Response;
import com.commands.CommandType;
import com.commands.ExecuteScriptCommand;
import com.exceptions.ConnectionErrorException;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Set;

public class Client {
    private DatagramSocket socket;
    private InetAddress address;

    private String serverAddress;
    private int port;
    private SocketChannel socketChannel;
    private ObjectOutputStream objectSender;
    private ObjectInputStream objectReader;
    private int reconnectionAttempts = 0;
    private int reconnectionTimeout = 6;
    private int maxAttemptNumber = 5;
    private CommandProcessor cm;
    private Selector selector;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(65536);

    public Client(String serverAddress, int port, CommandProcessor cm) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.cm = cm;
    }


    private byte[] buf;

    public Client() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String sendEcho(String msg) {
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 9999);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        packet = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String received = new String(
                packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }


    public void start() {
        try {
            boolean isRunning = true;
            while (isRunning) {
                try {
                    connect();
                    isRunning = exchangeDataWithServer();
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxAttemptNumber) {
                        System.out.println("Превышено количество попыток подключения!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout * 1000);
                        System.out.printf("Пытаюсь переподключиться (попытка %d).\n", reconnectionAttempts + 1);
                    } catch (Exception timeoutException) {
                        System.out.println("Произошла ошибка при попытке ожидания подключения!");
                        System.out.println("Повторное подключение будет произведено немедленно.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            System.out.println("Работа клиента завершена.");
        } catch (IOException exception) {
            System.out.println("Произошла ошибка при попытке завершить соединение с сервером!");
        }
    }

    public void connect() throws ConnectionErrorException {
        boolean tryingToConnect = true;
        do {
            try {
                if (reconnectionAttempts > 0) System.out.println("Попытка переподключения...");
                selector = Selector.open();
                socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress(serverAddress, port));
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_WRITE);
                System.out.println("Подключение установлено.");
                tryingToConnect = false;
                //objectSender = new ObjectOutputStream(socketChannel.socket().getOutputStream());
                //objectReader = new ObjectInputStream(socketChannel.socket().getInputStream());
                System.out.println("Готов к передаче данных.");
            } catch (IllegalArgumentException e) {
                System.out.println("Проверьте правильность введенного адреса.");
            } catch (IOException e) {
                System.out.println("Ошибка при соединении с сервером.");
                throw new ConnectionErrorException();
            }
        } while (tryingToConnect);


    }

    public boolean exchangeDataWithServer() {
        Request request = null;
        Response response = null;
        do {
            try {
                String[] commandSplit = cm.readCommand();
                request = cm.generateRequest(commandSplit);
                if (request.isEmpty()) continue;
                //sendRequest(request);
                //if (request.getCommand().getCommandType().equals(CommandType.EXECUTE_SCRIPT)) continue;
                //getResponse();
                send(request);
                byteBuffer.clear();
                //getResponse();
                response = receive();
                if (response == null) continue;
                System.out.println(response.getResponseInfo());
//                if (request.getCommand().getCommandType().equals(CommandType.EXECUTE_SCRIPT)) {
//                    ExecuteScriptCommand execScr = (ExecuteScriptCommand) request.getCommand();
//                    ArrayList<Request> script = cm.executeScript(execScr.getFilename());
//                    for (Request cmd: script) {
//                        if (cmd == null || cmd.isEmpty()) continue;
//                        objectSender.writeObject(cmd);
//                        response = (Response) objectReader.readObject();
//                        if (response == null) continue;
//                        if (!response.isEmpty()) System.out.println(response.getResponseInfo());
//                    }
//                } else {
//                    objectSender.writeObject(request);
//                    response = (Response) objectReader.readObject();
//                    if (!response.isEmpty()) System.out.println(response.getResponseInfo());
//                }
            } catch (InvalidClassException | NotSerializableException exception) {
                System.out.println("Произошла ошибка при отправке данных на сервер!");
            } catch (ClassNotFoundException exception) {
                System.out.println("Произошла ошибка при чтении полученных данных!");
            } catch (IOException exception) {
                System.out.println("Соединение с сервером разорвано!");
                try {
                    reconnectionAttempts++;
                    connect();
                } catch (ConnectionErrorException e) {
                    System.out.println("Ошибка передачи данных. Команда не была доставлена на сервер.");
                }
            }
        } while (request.isEmpty() || !request.getCommand().getCommandType().equals(CommandType.EXIT) );
        return false;
    }

    public void sendRequest(Request request) throws IOException, ClassNotFoundException {

        if (request.getCommand().getCommandType().equals(CommandType.EXECUTE_SCRIPT)) {
            ExecuteScriptCommand execScr = (ExecuteScriptCommand) request.getCommand();
            ArrayList<Request> script = cm.executeScript(execScr.getFilename());
            for (Request cmd : script) {
                if (cmd == null || cmd.isEmpty()) continue;
                objectSender.writeObject(cmd);
                getResponse();
            }
        }
        else {
            objectSender.writeObject(request);
        }
    }

    public void getResponse() throws IOException, ClassNotFoundException {
        Response response;
        response = (Response) objectReader.readObject();
        if (!response.isEmpty()) System.out.println(response.getResponseInfo());
    }

    private void makeByteBufferToRequest(Request request) throws IOException {
        byteBuffer.put(serialize(request));
        byteBuffer.flip();
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Response deserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        try {
            Response response = (Response) objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
            byteBuffer.clear();
            return response;
        } catch (ClassCastException e ){
            e.printStackTrace();
        }
        return new Response("lala");
    }

    private void send(Request request) throws IOException, ClassNotFoundException {
        makeByteBufferToRequest(request);
        SocketChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isWritable()) {
                    channel = (SocketChannel) key.channel();
                    if (request.getCommand().getCommandType().equals(CommandType.EXECUTE_SCRIPT)) {
                        ExecuteScriptCommand execScr = (ExecuteScriptCommand) request.getCommand();
                        ArrayList<Request> script = cm.executeScript(execScr.getFilename());
                        ByteBuffer execScrBuffer = ByteBuffer.allocate(65536);
                        for (Request cmd : script) {
                            if (cmd == null || cmd.isEmpty()) continue;
                            execScrBuffer.put(serialize(cmd));
                            execScrBuffer.flip();
                            channel.write(execScrBuffer);
                            channel.register(selector, SelectionKey.OP_READ);
                            receive().getResponseInfo();
                            channel.register(selector, SelectionKey.OP_WRITE);
                            byteBuffer.clear();
                            execScrBuffer.clear();
                        }
                    }
                    else {
                        channel.write(byteBuffer);
                    }
                    channel.register(selector, SelectionKey.OP_READ);
                    break;
                }
            }
        }
        byteBuffer.clear();
    }

    private Response receive() throws IOException, ClassNotFoundException {
        SocketChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isReadable()) {
                    //byteBuffer.clear();
                    channel = (SocketChannel) key.channel();
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    channel.register(selector, SelectionKey.OP_WRITE);
                    break;
                }
            }
        }
        return deserialize();
    }
}
