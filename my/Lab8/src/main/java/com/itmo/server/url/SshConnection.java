package com.itmo.server.url;

import com.itmo.utils.FileUtil;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshConnection implements UrlGetter {

    @Override
    public String getUrl(){
        String host = "se.ifmo.ru";
        int port = 2222;
        String[] acc = FileUtil.getFromFile("account");
        String user = acc[0];
        String pass = acc[1];

        String listenerHost = "localhost";
        int listenerPort = 8777;

        String listeningHost = "pg";
        int listeningPort = 5432;

        try{
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(pass);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(listenerPort, listeningHost, listeningPort);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return "jdbc:postgresql://" + listenerHost + ":" + listenerPort +"/studs";
    }
}
