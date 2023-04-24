package com.company;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Monitor_Drukarek {
    final Lock lock = new ReentrantLock();
    final Condition notReserved  = lock.newCondition();

    final Boolean[] printers;

    int count = 0;

    Monitor_Drukarek(int numOfPrinters) {
        this.printers = new Boolean[numOfPrinters];
        Arrays.fill(printers, true);
    }

    public int whichPrinterIsFree() {
        //reserve that printer
        int num;
        for (int i = 0; i < printers.length; i++) {
            if (printers[i]) {
                num = i;
                printers[i] = false;
                return num;
            }
        }

        return -1;
    }
    public int reserve() throws InterruptedException {
        int number;
        lock.lock();
        try {
            while(count == printers.length) {
                notReserved.await();
            }

            number = whichPrinterIsFree();

            ++count;

        } finally {
            lock.unlock();
        }

        return number;
    }
    public void print( int printNum, int name) throws InterruptedException {
        System.out.println(" on printer num: " + printNum + " Thread nr: " + name);
    }
    public void release(int printNum, int name) throws InterruptedException {
        lock.lock();
        try {
            --count;
            printers[printNum] = true;
            print(printNum, name);
            notReserved.signal();
        } finally {
            lock.unlock();
        }

    }
}

class PrintThread extends Thread{
    private Monitor_Drukarek monitor_drukarek;
    private int printerNumber;
    private final int name;

    PrintThread(Monitor_Drukarek monitor_drukarek, int name) {
        this.monitor_drukarek = monitor_drukarek;
        this.name = name;
    }

    @Override
    public void run() {
        for(int i = 0;  i < 10;   i++) {
            try {
                printerNumber = monitor_drukarek.reserve();
                System.out.println("Thread " + name + " Reserving printer " + printerNumber);
                sleep(500);
                System.out.println("-----Printing-------");
                monitor_drukarek.release(printerNumber, name);
                sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}