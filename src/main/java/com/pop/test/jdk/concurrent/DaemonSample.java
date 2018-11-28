package com.pop.test.jdk.concurrent;

public class DaemonSample {
	public static void main(String[] args) throws Exception{
        DaemonThread t = new DaemonThread();  
//        t.setDaemon(true);//this is set t thread as a daemon thread.
        t.start();  
        Thread.sleep(2000);  
        System.out.println("main thread exit.");  
    }  
}

class DaemonThread extends Thread {  
    @Override  
    public void run() {  
        for(int i = 0; i < 10; i++) {  
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            System.out.println("i=" + i);  
        }  
    }  
}  