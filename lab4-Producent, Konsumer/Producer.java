package com.company;

import com.company.Buffor;

public class Producer extends Thread {
    private final Buffor buffer;
    private final int bufforLength;

    public Producer(Buffor buffer) {
        this.buffer = buffer;
        this.bufforLength = buffer.getSize();
    }

    public void run() {

        for(int i = 0;  i < 120;   i++) {
            try {
                buffer.put(i % bufforLength,-1,0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

