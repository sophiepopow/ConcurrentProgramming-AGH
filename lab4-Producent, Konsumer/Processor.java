package com.company;

import java.nio.Buffer;

class Processor extends Thread{
    private final Buffor buffer;
    private final int name;
    private final int workingTime;
    private final int action;
    private final int bufforLength;

    Processor(Buffor buffer, int name, int workingTime, int action) {
        this.buffer = buffer;
        this.name = name;
        this.workingTime = workingTime;
        this.bufforLength = buffer.getSize();
        this.action = action;
    }

    @Override
    public void run() {
        for(int i = 0;  i < 120;   i++) {
            try {
                buffer.put(i % bufforLength,action-1,action);
                    sleep(workingTime);
                }
             catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
