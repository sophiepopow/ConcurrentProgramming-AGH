package com.company;

//import org.junit.Test;

public class BinarySemafor {

    private boolean value;
    private Object lock;

    public BinarySemafor() {
        this.value = true;
    }
    // zmniejsza wartosc = opusc
    public synchronized void P(Buffor buffor , int index, int condition) throws InterruptedException {
        while (!(value && buffor.checkCondition(index, condition))) {
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










