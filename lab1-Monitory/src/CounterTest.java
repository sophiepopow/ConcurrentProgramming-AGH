import org.junit.Test;

class DecrementThread extends Thread {
    private final CounterTest counter;

    public DecrementThread(CounterTest c) {
        this.counter = c;
    }

    public void run() {
        for (int i = 0; i < 100000000; i++) {
            counter.decrement();
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
            counter.increment();
        }
    }
}

public class CounterTest extends Thread {
    private int number;

    public void print() {
        System.out.println(number);
    }
    public void increment() {
        number++;
    }
    public void decrement() {
        number--;
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





