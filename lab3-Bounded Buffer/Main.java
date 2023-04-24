package com.company;


public class Main {

    public static void main(String[] args) throws InterruptedException {

//        Monitor_Drukarek monitor_drukarek = new Monitor_Drukarek(2);
//        Thread t1 = new PrintThread(monitor_drukarek,1);
//        Thread t2 = new PrintThread(monitor_drukarek,2);
//        Thread t3 = new PrintThread(monitor_drukarek, 3);
//        Thread t4 = new PrintThread(monitor_drukarek, 4);
//        Thread t5 = new PrintThread(monitor_drukarek, 5);
//        t1.start();
//        t2.start();
//        t3.start();
//        t4.start();
//        t5.start();
//        t1.join();
//        t2.join();
//        t3.join();
//        t4.join();
//        t5.join();

        Kelner kelner = new Kelner(8);
        Thread t1 = new PartnerThread(kelner,1, "1.1");
        Thread t2 = new PartnerThread(kelner,2, "2.1");
        Thread t3 = new PartnerThread(kelner, 1, "1.2");
        Thread t4 = new PartnerThread(kelner, 2, "2.2");
        Thread t5 = new PartnerThread(kelner, 3, "3.1");
        Thread t6 = new PartnerThread(kelner, 3, "3.2");
        Thread t7 = new PartnerThread(kelner, 4, "4.1");
        Thread t8 = new PartnerThread(kelner, 4, "4.2");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();


    }
}
