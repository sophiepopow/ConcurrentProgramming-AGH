package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        zad1();
//        zad2('N');
        zad2('S');
    }

    public static void zad1() throws InterruptedException {
        Buffor buffer = new Buffor(6);
        Thread t0 = new Producer(buffer);
        Thread t1 = new Processor(buffer,1, 10, 1);
        Thread t2 = new Processor(buffer,2, 20, 2);
        Thread t3 = new Processor(buffer, 1,15, 3);
        Thread consumer = new Consumer(buffer);

        t0.start();
        t1.start();
        t2.start();
        t3.start();
        consumer.start();

        t0.join();
        t1.join();
        t2.join();
        t3.join();
        consumer.join();
    }

    public static void zad2(char type) throws InterruptedException {
        int M = 100000;
        int P = 1000;
        int K = 1000;
        IntBuffer buffer = new IntBuffer(M);

        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for(int i = 0; i< P ;i++) {
            producers.add(new Producent(buffer, type));
        }
        for(int i = 0; i< K ;i++) {
            consumers.add(new Konsument(buffer, type));
        }

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        for(int i = 0; i< P; i++) {
            producers.get(i).join();
        }

        for(int i = 0; i< K; i++) {
            consumers.get(i).join();
        }


    }
}
