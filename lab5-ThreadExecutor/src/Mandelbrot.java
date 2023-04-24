import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final BufferedImage I;
    int WIDTH = 800;
    int HEIGHT = 600;
    public Mandelbrot(int threadsCount, int tasksCountX, int maxIter, int tasksCountY) {
        super("Mandelbrot Set");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService pool = Executors.newFixedThreadPool(threadsCount);
        List<DrawPartOfMandelbrot> tasks = new ArrayList<>();
        List<Future<Boolean>> tasksResults = new ArrayList<>();
        long now = System.nanoTime();
        for(int i=0; i< tasksCountX; i++) {
            for(int j = 0; j<tasksCountY; j++) {
                tasks.add(new DrawPartOfMandelbrot(
                        I,
                        i*WIDTH/tasksCountX,
                        (i*WIDTH/tasksCountX) + WIDTH/tasksCountX,
                        j*HEIGHT/tasksCountY,
                        (j*HEIGHT/tasksCountY) + HEIGHT/tasksCountY,
                        maxIter));
            }

        }

        tasks.forEach(task -> tasksResults.add(pool.submit(task)));
        tasksResults.forEach(result -> {
            try {
                result.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
//        System.out.println("Time executed for " + threadsCount + " threads and " + tasksCount + " tasks: " + (System.nanoTime() - now));
        System.out.println((System.nanoTime() - now));
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {

//        new Mandelbrot(1,1, 20000,1).setVisible(true);
//        new Mandelbrot(1,10, 20000,1).setVisible(true);
//            new Mandelbrot(1, 800, 20000, 600).setVisible(true);
//
//        new Mandelbrot(2,2, 20000,1).setVisible(true);
//        new Mandelbrot(2,20, 20000,1).setVisible(true);
//        new Mandelbrot(2,800, 20000,600).setVisible(true);
//
//        new Mandelbrot(4,4, 20000,1).setVisible(true);
//        new Mandelbrot(4,40, 20000,1).setVisible(true);
//        new Mandelbrot(4,800, 20000, 600).setVisible(true);


    }
}