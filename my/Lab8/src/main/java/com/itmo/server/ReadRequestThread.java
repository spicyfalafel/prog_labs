package com.itmo.server;

import ch.qos.logback.classic.Logger;
import com.itmo.client.User;
import com.itmo.commands.Command;
import com.itmo.commands.ExitCommand;
import com.itmo.commands.SubscribeForNotificationsCommand;
import com.itmo.utils.SerializationManager;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

//	Для многопоточного чтения запросов


public class ReadRequestThread extends Thread {
    private final SocketChannel channel;
    private final Application application;
    private User user;
    ReadRequestThread(SocketChannel clientSocketChannel, Application application){
        this.channel = clientSocketChannel;
        this.application = application;
        user = new User("Unregistered", "");
    }

    private final int DEFAULT_BUFFER_SIZE = 4096;
    private final ByteBuffer b = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
    public final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ReadRequestThread.class);
    @Override
    public void run() {
        while(true){
            Command command = null;
            while(command==null) command = getCommandFromClient(channel);
            user = command.getUser();
            new Thread(new RequestExecutorThread(command, channel, application)).start();
            // if user wanted to exit or if it was Subscribing command
            // then stop thread
            if(command instanceof ExitCommand || command instanceof SubscribeForNotificationsCommand) break;
        }
    }

    // это лучше не трогать
    private Command getCommandFromClient(SocketChannel channel) {
        try {
            if(checkOneByte(channel)) {
                Command command;
                int got;
                while (b.position() == 1) {
                    got = channel.read(b);
                    if (got != 0) log.info("получено байт:" + got);
                }
                if (b.remaining() != 0) {
                    command = SerializationManager.readObject(Arrays.copyOfRange(b.array(),
                            1, b.array().length - 1));
                    log.info("Полученная команда: " + command.toString());
                    b.clear();
                    return command;
                }
            }
            return null;
        } catch (ClassNotFoundException e) {
            log.error("Error while serialization");
            return null;
        }catch (StreamCorruptedException e ){ // при попытке десериализации объекта, который не полностью передался
            System.out.println(e.getLocalizedMessage());
            return null;
        } catch(IOException e) {
            application.activeUsers.removeUserByName(user.getName());
            return null;
        }
    }

    private boolean checkOneByte(SocketChannel channel) throws IOException {
        int bytesFromClient = 0;
        if(channel!=null) bytesFromClient = channel.read(b);
        return (bytesFromClient==1);
    }
}