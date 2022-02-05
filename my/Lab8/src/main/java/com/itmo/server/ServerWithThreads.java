package com.itmo.server;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerWithThreads {
    private ServerSocketChannel ssc;
    private final Logger log;
    private boolean serverOn = true;
    private final int port;
    public static ExecutorService executorService = Executors.newCachedThreadPool();

    public void run(Application application) {
        log.info("Запуск сервера, порт: " + port);
        setupNet();
        SocketChannel clientSocketChannel = null;

        while (serverOn) {
            try{
                clientSocketChannel = accept();
                if(clientSocketChannel!=null){
                    new Thread(new ReadRequestThread(clientSocketChannel, application)).start();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        closeEverything();
        System.exit(0);
    }


    private void closeEverything() {
        try {
            if (ssc != null) ssc.close();
        } catch (IOException ignored) {
        }
        ssc = null;
    }

    public SocketChannel accept() throws IOException {
        SocketChannel channel = ssc.accept();
        if (channel != null) {
            log.info("accepted client");
            try {
                channel.configureBlocking(false);
                channel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
                return channel;
            } catch (IOException e) {
                log.error("Unable to accept channel");
            }
        }
        return null;
    }

    public void setupNet(){
        ssc = null;
        try {
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
            ssc.socket().bind(inetSocketAddress);
        } catch (IOException e) {
            System.out.println("Most likely the server is already running");
        }
    }

    public ServerWithThreads(int port, Logger log){
        this.port = port;
        this.log = log;
    }
}