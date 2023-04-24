package com.company;

import org.junit.Test;

import java.util.concurrent.Semaphore;

public class BinarySemafor {

    private boolean value;
    private Object lock;

    public BinarySemafor() {
        this.value = true;
    }
    // zmniejsza wartosc = opusc
    public synchronized void P() throws InterruptedException {
        while (!value) {
            wait();
        }
        value = false;
    }

    //zwieksza wartosc = podnies
    public synchronized void V() {
        value = true;
        notifyAll();
    }
}

class DecrementThread extends Thread {
    private final CounterTest counter;

    public DecrementThread(CounterTest c) {
        this.counter = c;
    }

    public void run() {
        for (int i = 0; i < 100000000; i++) {

            try {
                counter.decrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class IncrementThread extends Thread {
    private final CounterTest counter;

    public IncrementThread(CounterTest c) {
        this.counter = c;
    }

    public void run() {
        for (int i = 0; i < 100000000; i++) {
            try {
                counter.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class CounterTest extends Thread {
    private int number;
    private BinarySemafor semafor;

    CounterTest() {
        number = 0;
        semafor = new BinarySemafor();
    }

    public void print() {
        System.out.println(number);
    }
    public void increment() throws InterruptedException {
        semafor.P();
        number++;
        semafor.V();
    }
    public void decrement() throws InterruptedException {
        semafor.P();
        number--;
        semafor.V();
    }


    @Test
    public void testingThreads() throws InterruptedException {
        CounterTest counter = new CounterTest();

        Thread t1 = new IncrementThread(counter);
        Thread t2 = new DecrementThread(counter);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        counter.print();
    }
}






