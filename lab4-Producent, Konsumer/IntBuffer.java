package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IntBuffer {
    private final int size;
    private int currentlyFree;
    private final Lock lock = new ReentrantLock();
    private final Condition firstProd = lock.newCondition();
    private final Condition firstCons = lock.newCondition();
    private final Condition restProd = lock.newCondition();
    private final Condition restCons = lock.newCondition();
    private boolean isFirstProd = false;
    private boolean isFirstCons = false;

    public IntBuffer(int size) {
        this.size = size;
        this.currentlyFree = size;
    }

    synchronized public void put(int count) throws InterruptedException {
//        System.out.println("Wolne " + currentlyFree);
        while(currentlyFree < count) {
//            System.out.println("Czekam na wolne");
            wait();
        }
        currentlyFree -= count;
//        System.out.println("Wolne " + currentlyFree);
        printBuffer();
        notifyAll();
    }

    synchronized public void take(int count) throws InterruptedException {
//        System.out.println("Zajete " + (size - currentlyFree));
        while((size - currentlyFree) < count ) {
//            System.out.println("Czekam na pełne");
            wait();
        }
        currentlyFree += count;
//        System.out.println("Zajete " + (size - currentlyFree));
        printBuffer();
        notifyAll();
    }

    public int getSize() {
        return size;
    }

    synchronized private void printBuffer() {
        for (int i = 0; i < (size - currentlyFree); i++) {
            System.out.print("🀫");
        }
        for (int i = 0; i < currentlyFree; i++) {
            System.out.print("🀆");
        }

        System.out.println("");

    }
    public void putSprawiedliwie(int count) {
        lock.lock();
        try {
            if (isFirstProd) {
                restProd.await();
            }
            isFirstProd = true;
            while(currentlyFree < count) {
                firstProd.await();
            }
            currentlyFree -= count;
//            System.out.println("Zajete P" + (size - currentlyFree));
            printBuffer();
            isFirstProd = false;
            restProd.signal();
            firstCons.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    public void takeSprawiedliwie(int count) {
        lock.lock();
        try {
            while(isFirstCons) {
                restCons.await();
            }
            isFirstCons = true;
            while((size - currentlyFree) < count ) {
                firstCons.await();
            }
            currentlyFree += count;
//            System.out.println("Zajete C " + (size - currentlyFree));
            printBuffer();
            isFirstCons = false;
            restCons.signal();
            firstProd.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
