package mayton.compression;

import mayton.image.standard.Resolutions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Relaxer {

    void renderFrame(int i) throws IOException {
        int x = Resolutions.FULL_HD.x;
        int y = Resolutions.FULL_HD.y;
        BufferedImage image = new BufferedImage(x, y, BufferedImage.TYPE_4BYTE_ABGR);
        ImageIO.write(image, "PNG", new FileOutputStream("/storage/video/probe-compression/dyn-relax-" + i + ".png"));
    }

    public static void main(String[] args) {

    }

}
