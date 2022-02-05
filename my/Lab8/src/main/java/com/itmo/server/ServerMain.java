package com.itmo.server;

import ch.qos.logback.classic.Logger;
import com.itmo.utils.LocaleClass;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ServerMain {
    public static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ServerWithThreads.class);
    public static LocaleClass localeClass = new LocaleClass();
    public static void main(String[] args) throws SQLException {

        if(args.length!=1){
            System.out.println("Укажите в аргументах порт (8080)");
            System.exit(1);
        }else{
            int port = 8080;
            try{
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                System.out.println("Не целое число, использую 8080");
            }
            Application application = new Application();
            ServerWithThreads server = new ServerWithThreads(port, logger);
            server.run(application);
        }
    }
}
