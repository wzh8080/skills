package test.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
图像取反相当于取底片，对于每个像素点的RGB来讲，就是: 
R′=255−R
R′=255−R
G′=255−G
G′=255−G
B′=255−B
B′=255−B
用R’，G’，B’来替换R，G，B。
 * @author King
 *
 */
public class ImageNegative {
	public static void main(String args[]) throws IOException {
        BufferedImage image = null;
        File f = null;

        try {
            f = new File("C:\\Users\\King\\Desktop\\test.png");
            image = ImageIO.read(f);

            int width = image.getWidth();
            int height = image.getHeight();

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    int p = image.getRGB(i, j);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    System.out.println("a===="+a);
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;

                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    image.setRGB(i, j, p);
                }
            }
            System.out.println("完成");
            f = new File("C:\\Users\\King\\Desktop\\Out2.jpg");
            ImageIO.write(image, "jpg", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
