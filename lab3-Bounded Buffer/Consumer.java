package com.company;
import static java.lang.Thread.sleep;

public class Consumer implements Runnable {
    private BoundedBuffer buffer;

    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < 10000;   i++) {
            Object message;
            try {
                sleep(10);
                message = buffer.take();
                System.out.println(message.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            do {
//                message = buffer.take();
//            } while (message.isEmpty());

        }

    }
}