package com.company;

import static java.lang.Thread.sleep;

public class Consumer extends Thread {
    private final Buffor buffer;
    private final int bufforLength;

    public Consumer(Buffor buffer) {
        this.buffer = buffer;
        this.bufforLength = buffer.getSize();
    }
    public void run() {

        for(int i = 0;  i < 120;   i++) {
            try {
                buffer.put(i % bufforLength, 3,-1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }

}