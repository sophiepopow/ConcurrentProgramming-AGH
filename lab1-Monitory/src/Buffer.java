import org.junit.Test;

import java.util.Objects;

import static java.lang.Thread.sleep;

public class Buffer {
    private String buffer;
    public Buffer() {
        this.buffer = "";
    }

    synchronized public String take() {
        String tmp = buffer;
        if(!this.buffer.isEmpty()) {
            buffer = "";
        }
        return tmp;
    }

    synchronized public boolean put(String msg) {
        if(!buffer.isEmpty()) {
            return false;
        }
        this.buffer = msg;
        return true;
    }


    @Test
    public void testingBuffer() throws InterruptedException {
        Buffer newBuffer = new Buffer();
        Producer producer = new Producer(newBuffer);
        Consumer consumer = new Consumer(newBuffer);
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

}

