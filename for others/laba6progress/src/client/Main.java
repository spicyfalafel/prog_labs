package client;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Client client = new Client();
	    Scanner in = new Scanner(System.in);
	    ConsoleManager consoleManager = new ConsoleManager();

	    while(true){
            System.out.println(client.sendEcho(consoleManager.getCommand().toString()));
        }



    }
}
