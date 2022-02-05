package com.itmo.managers;

import java.io.PrintWriter;

public class StringManager {
    private PrintWriter printWriter;

    public StringManager(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    private int numOfLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }
    public void multiLine(String str){
        printWriter.println(numOfLines(str));
        printWriter.println(str);
    }
    public void multiLine(int str){
        printWriter.println(1);
        printWriter.println(str);
    }

    public static void multiLine(PrintWriter printWriter, String str){
        String[] lines = str.split("\r\n|\r|\n");
        printWriter.println(lines.length);
        printWriter.println(str);
    }
}
