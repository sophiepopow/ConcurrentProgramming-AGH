package com.company;

public class Semafor {
    private int value;

    public Semafor(int number) {
        this.value = number;
        // gdy wartosc jest = 0 binarny semafor zamyka szlaban
        System.out.println("Liczba koszykow: " + number);
    }
    // zmniejsza wartosc = opusc
    public synchronized void P(int clientName) throws InterruptedException {
        while (value == 0) {
            System.out.println("------------Klient " + clientName + " czeka-------------");
            wait();
        }
        value--;
    }

    //zwieksza wartosc = podnies
    public synchronized void V() throws InterruptedException {
            value++;
            System.out.println("wartosc semafora: " + value);
            notifyAll();
    }
}

class Customer extends Thread {
    private final Shop shop;
    private final int name;
    public Customer(Shop s, int name) {
        this.shop = s;
        this.name=  name;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sleep(100);
                shop.shopping(name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Shop extends Thread {
    private int baskets;
    private final Semafor semafor;

    Shop(int maxBaskets) {
        baskets = 0;
        semafor = new Semafor(maxBaskets);
    }

    private synchronized void incrementBasket(int name) {
        baskets++;
        System.out.println("Klient " + name + " wzial koszyk, wziete koszyki:" + baskets);
    }
    private synchronized void decrementBasket(int name) {
        baskets--;
        System.out.println("Klient " + name + " oddal koszyk, wziete koszyki:" +  baskets);
    }

    public void shopping(int clientName) throws InterruptedException {
        semafor.P(clientName);
        incrementBasket(clientName);
        sleep(100);
        decrementBasket(clientName);
        semafor.V();
    }

}
