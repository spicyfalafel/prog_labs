package com.itmo.server;

import com.itmo.managers.StringManager;

import java.nio.channels.SocketChannel;

//3.	Для многопоточной отправки ответа использовать создание нового потока (java.lang.Thread)
public class SendResponseThread extends Thread{
    private String ans;
    private StringManager stringManager;
    public SendResponseThread(StringManager stringManager, String ans){
        this.ans = ans;
        this.stringManager = stringManager;
    }

    @Override
    public void run() {
        stringManager.multiLine(ans);
    }
}
