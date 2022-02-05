package com.itmo.example;

import java.util.concurrent.Executor;

public class ExecutorGuide {
    public void showExecutor(){
        Runnable task = () -> System.out.println("Executor тоже тут");
        Executor executor = (runnable) -> {
            new Thread(runnable).start();
        };
        executor.execute(task);
    }
}
