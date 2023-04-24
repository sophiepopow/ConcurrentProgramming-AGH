public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < 10000;   i++) {
            String message;
            do {
                message = buffer.take();
            } while (message.isEmpty());
            System.out.println(message);
        }

    }
}