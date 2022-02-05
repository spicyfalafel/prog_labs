package com.itmo.example;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableCheck extends Thread {
    @Override
    public void run() {
        for(int i = 1; i<=10;i++){
            System.out.println(i + " другой поток");
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){

            }
        }
    }
}
