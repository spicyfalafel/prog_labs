package com.itmo.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) {
        CallableCheck check = new CallableCheck();
        check.start();
        new Thread(new RunnableGuide()).start();
        ExecutorGuide executorGuide = new ExecutorGuide();
        executorGuide.showExecutor();
        Callable<String> task = () -> {
            return "А я тут)";
        };
        FutureTask<String> futureTask = new FutureTask<>(task);
        new Thread(futureTask).start();
        try{
            System.out.println(futureTask.get());
        }catch (InterruptedException | ExecutionException ignored){
        }
        new Thread(new Runnable(){
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                System.out.println("Привет из побочного потока!");
            }
        }).start();
    }
}
