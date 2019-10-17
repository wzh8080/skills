package test.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * �Ҷ�ͼֱ�۵ؽ����ǽ�ԭ����RGBͼ��ת��Ϊֻ�лҶȼ���ͼ������һ������Ҳ�Ƚϼ򵥣�
 * ֻҪ��ÿ�����ص��RGBֵ�ó�������һ�����ǵ�ƽ��ֵ(R+G+B)/3(R+G+B)/3��
 * Ȼ�����滻ԭ����RGBֵ��OK�ˡ�
 * https://blog.csdn.net/sinat_36246371/article/details/72823349
 * @author King
 *
 */
public class ImageGrayscale {
	public static void main(String args[]) throws IOException {
        BufferedImage image = null;
        File file = null;

        try {
            file = new File("C:/Users/King/Desktop/1.png");
            image = ImageIO.read(file);

            int width = image.getWidth();
            int height = image.getHeight();

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    int p = image.getRGB(i, j);

                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    int avg = (r + g + b) / 3;

                    p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                    image.setRGB(i, j, p);
                }
            }


            file = new File("C:/Users/King/Desktop/2.png");
            ImageIO.write(image, "jpg", file);
            System.out.println("���");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
