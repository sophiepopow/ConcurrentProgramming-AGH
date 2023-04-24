package com.company;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class Buffor {
    private final int[] buffer;
    private final int size;
    private final BinarySemafor[] semafor;

    public Buffor(int size) {
        this.size = size;
        this.buffer = new int[size];
        this.semafor = new BinarySemafor[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = -1;
            semafor[i] = new BinarySemafor();
        }
    }

    public boolean checkCondition(int index, int condition) {
        return buffer[index] == condition;
    }

    public void put(int index, int condition, int action) throws InterruptedException {

        semafor[index].P(this, index, condition);

        System.out.println("ACTION: " + action + "INDEX " + index);
        printBuffer();

        buffer[index] = action;
        semafor[index].V();
    }
    private void printBuffer() {
        for (int i = 0; i < size; i++) {
//            System.out.println(buffer[i]);
            System.out.print(buffer[i]);
        }
        System.out.println();
        System.out.println("--------------");
    }

    public int getSize() {
        return size;
    }

}

