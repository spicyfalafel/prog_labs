package com.itmo.example;

public class RunnableGuide implements Runnable{
    @Override
    public void run() {
        for(int i = 1; i<= 10;i++){
            System.out.println("А это из RunnableGuide");
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){

            }
        }
    }
}
