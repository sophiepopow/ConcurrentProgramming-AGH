import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class DrawPartOfMandelbrot implements Callable<Boolean> {
    int startX, startY, endX, endY;
    int MAX_ITER;
    BufferedImage image;
    DrawPartOfMandelbrot(BufferedImage image, int startX, int endX, int startY, int endY, int MAX_ITER) {
        this.image= image;
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.MAX_ITER = MAX_ITER;
    }

    @Override
    public Boolean call() throws Exception {
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                double zy;
                double zx = zy = 0;
                double ZOOM = 150;
                double cX = (x - 400) / ZOOM;
                double cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                image.setRGB(x, y, iter | iter << 8);
            }
        }
        return true;
    }
}
