import org.junit.Test;

class DecrementThreadSynchronized extends Thread {
    private final CounterSynchronized counter;

    public DecrementThreadSynchronized(CounterSynchronized c) {
        this.counter = c;
    }

    public void run() {
        for (int i = 0; i < 100000000; i++) {
            counter.decrement();
        }
    }
}

class IncrementThreadSynchronized extends Thread {
    private final CounterSynchronized counter;

    public IncrementThreadSynchronized(CounterSynchronized c) {
        this.counter = c;
    }

    public void run() {
        for (int i = 0; i < 100000000; i++) {
            counter.increment();
        }
    }
}

public class CounterSynchronized extends Thread{
    private int number = 0;

    public void print() {
        System.out.println(number);
    }
    synchronized public void increment() {
            number++;
    }
    synchronized public void decrement() {
            number--;
    }


    @Test
    public void testingThreads() throws InterruptedException {
        CounterSynchronized counter = new CounterSynchronized();
        Thread t1 = new IncrementThreadSynchronized(counter);
        Thread t2 = new DecrementThreadSynchronized(counter);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        counter.print();
    }
}





