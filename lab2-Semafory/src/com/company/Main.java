package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Zadanie 1 - semafor binarny
//        CounterTest counter = new CounterTest();
//
//        Thread t1 = new IncrementThread(counter);
//        Thread t2 = new DecrementThread(counter);
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//        counter.print();

        // Zadanie 2 - semafor licznikowy
        Shop shop = new Shop(2);
        Thread c1 = new Customer(shop, 1);
        Thread c2 = new Customer(shop, 2);
        Thread c3 = new Customer(shop, 3);
        Thread c4 = new Customer(shop, 4);
        Thread c5 = new Customer(shop, 5);
        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();
        c1.join();
        c2.join();
        c3.join();
        c4.join();
        c5.join();

    }
}
