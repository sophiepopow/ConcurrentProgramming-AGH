package com.company;

import java.nio.Buffer;
import java.util.concurrent.ThreadLocalRandom;

public class GrubiChudzi {
}

class Producent extends Thread {
    private final IntBuffer buffer;
    private final int bufferSize;
    private final char type;

    public Producent(IntBuffer buffer, char type) {
        this.buffer = buffer;
        this.bufferSize = buffer.getSize();
        this.type = type;
    }
    public void run() {
        int count;
        for(int i = 0;  i < 10000;   i++) {
            // count = randomInt <1,M)
            count = ThreadLocalRandom.current().nextInt(1, (bufferSize/2) + 1);
            try {
//                System.out.println("Producent: " + count);
                long  time =  System.nanoTime();
                if (type == 'N') {

                    // time = getTime()
                    buffer.put(count);
                    // print('P', count, getTime() - time)
                    System.out.println("PN " + count + " " +  (System.nanoTime() - time));
                }
                else if (type == 'S') {
                    buffer.putSprawiedliwie(count);
                    System.out.println("PS " + count + " " +  (System.nanoTime() - time));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Konsument extends Thread {
    private final IntBuffer buffer;
    private final int bufferSize;
    private final char type;

    public Konsument(IntBuffer buffer, char type) {
        this.buffer = buffer;
        this.bufferSize = buffer.getSize();
        this.type = type;
    }
    public void run() {
        int count;
        for(int i = 0;  i < 10;   i++) {
            // count = randomInt <1,M)
            count = ThreadLocalRandom.current().nextInt(1, (bufferSize/2) + 1);
            try {
//                System.out.println("Konsument: " + count);
                long  time =  System.nanoTime();
                if (type == 'N') {
                    buffer.take(count);
                    System.out.println("CN " + count + " " +  (System.nanoTime() - time));
                }
                else if (type == 'S') {
                    buffer.takeSprawiedliwie(count);
                    System.out.println("CS " + count + " " +  (System.nanoTime() - time));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}