package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Kelner {
    final Lock lock = new ReentrantLock();
    final Condition freeTable  = lock.newCondition();
    final Condition[] waitngForPartners;
    final Boolean[] partners;
    private int peopleAtTable = 0;

    Kelner(int numOfPartners) {
        waitngForPartners = new Condition[numOfPartners];
        partners = new Boolean[numOfPartners];
        for (int i = 0; i < numOfPartners; i++) {
            waitngForPartners[i]  = lock.newCondition();
            partners[i] = false;
        }
    }


    public void iWantTable(int name, String name2) {
        try {
            lock.lock();
            if (!partners[name]) {
                System.out.println(name2 + " Czekam na partnera");
                partners[name] = true;
                waitngForPartners[name].await();
            }
            else {
                if(peopleAtTable > 0) {
                    System.out.println(name2 + " Czekam na stolik");
                    freeTable.await();
                }
                peopleAtTable = 2;
                partners[name] = false;
                waitngForPartners[name].signal();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void releaseTable(String name) {
        try {
            lock.lock();
            System.out.println(name + " Oddaje stolik");
            peopleAtTable -= 1;
            if (peopleAtTable == 0) {
                freeTable.signal();
            }
        } finally {
            lock.unlock();
        }

    }
}

class PartnerThread extends Thread{
    private final Kelner kelner;
    private final int name;
    private final String name2;

    PartnerThread(Kelner kelner, int name, String name2) {
        this.kelner = kelner;
        this.name = name;
        this.name2 = name2;
    }

    @Override
    public void run() {
        for(int i = 0;  i < 10;   i++) {
            try {
                kelner.iWantTable(name, name2);
                sleep(100);
                System.out.println(name2 + " JEMY");
                kelner.releaseTable(name2);
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}