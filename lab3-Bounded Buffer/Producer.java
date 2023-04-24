package com.company;

public class Producer implements Runnable {
    private BoundedBuffer buffer;

    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < 10000;   i++) {
            try {
                buffer.put("message " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

