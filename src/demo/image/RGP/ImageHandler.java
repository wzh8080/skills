package demo.image.RGP;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageHandler {
	public static void main(String[] args) {
		ImageHandler imageHandler = new ImageHandler();
		imageHandler.grayImage();
	}
	/**
	 * 将图片分解成黑白图片
	 * RGB权重公式如下： Y=0.2989∗R+0.5870∗G+0.1140∗B
	 */
	public static void grayImage() {
		OutputStream output = null;
		try {
			// read image
			BufferedImage img = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\1.png"));
			int imageType = img.getType();
			int w = img.getWidth();
			int h = img.getHeight();
			int startX = 0;
			int startY = 0;
			int offset = 0;
			int scansize = w;
			int dd = w-startX;
			int hh = h - startY;
			int x0 = w / 2;
			int y0 = h / 2;
			System.out.println("dd:" + dd + "  hh:" + hh);
			// rgb的数组,保存像素，用一维数组表示二位图像像素数组
			int[] rgbArray = new int[offset + hh * scansize + dd];
			//newArray 保存处理后的像素
			int[] newArray = new int[offset + hh * scansize + dd];
			img.getRGB(startX, startY, w, h, rgbArray, offset, scansize);
			
			int rgb = rgbArray[offset + (y0 - startY) * scansize + (x0 - startX)];
			Color c = new Color(rgb);
			System.out.println("中间像素点的rgb：" +c+" "+c.getRGB());
			for(int i=0; i<h-startY; i++) {
				for(int j=0; j<w-startX; j++) {
					c = new Color(rgbArray[i*dd + j]);
					//彩色图像转换成无彩色的灰度图像Y=0.299*R + 0.578*G + 0.114*B
					int gray = (int)(0.299*c.getRed() + 0.578*c.getGreen() + 0.114*c.getBlue());
					newArray[i*dd + j] = new Color(gray, gray, gray).getRGB();	//蓝色灰度图像
				}
			}
			
			// create and save to bmp
			File out = new File("C:\\Users\\Administrator\\Desktop\\2.png");
			if (!out.exists())
				out.createNewFile();
			output = new FileOutputStream(out);
			BufferedImage imgOut = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
			imgOut.setRGB(startX, startY, w, h, newArray, offset, scansize);
			ImageIO.write(imgOut, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null)
				try {
					output.close();
				} catch (IOException e) {
				}
		}
	}
	
	/**
	 * 将图片分解成R、G、B三种灰度图片
	 */
	public static void analyseRGB() {
		OutputStream output = null;
		try {
			// read image
			BufferedImage img = ImageIO.read(new File("F:\\image processing\\图1.jpg"));
			int imageType = img.getType();
			int w = img.getWidth();
			int h = img.getHeight();
			int startX = 0;
			int startY = 0;
			int offset = 0;
			int scansize = w;
			int dd = w-startX;
			int hh = h - startY;
			int x0 = w / 2;
			int y0 = h / 2;
			//System.out.println("dd:" + dd + "  hh:" + hh);
			// rgb的数组,保存像素，用一维数组表示二位图像像素数组
			int[] rgbArray = new int[offset + hh * scansize
					+ dd];
			//newArray 保存处理后的像素
			int[] newArray = new int[offset + hh * scansize
			     					+ dd];
			img.getRGB(startX, startY, w, h, rgbArray, offset, scansize);
			
			int rgb = rgbArray[offset + (y0 - startY) * scansize
					+ (x0 - startX)];
			Color c = new Color(rgb);
			//System.out.println("中间像素点的rgb：" + c);
			for(int i=0; i<h-startY; i++) {
				for(int j=0; j<w-startX; j++) {
					c = new Color(rgbArray[i*dd + j]);
					//newArray[i*dd + j] = new Color(c.getRed(), 0, 0).getRGB() ;	//红色灰度图像
					//newArray[i*dd + j] = new Color(0, c.getGreen(), 0).getRGB();	//绿色灰度图像
					newArray[i*dd + j] = new Color(0, 0, c.getBlue()).getRGB();	//蓝色灰度图像
				}
			}
			
			// create and save to bmp
			//File out = new File("F:\\image processing\\图1_red.jpg");
			//File out = new File("F:\\image processing\\图1_green.jpg");
			File out = new File("F:\\image processing\\图1_blue.jpg");
			if (!out.exists())
				out.createNewFile();
			output = new FileOutputStream(out);
			BufferedImage imgOut = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
			imgOut.setRGB(startX, startY, w, h, newArray, offset, scansize);
			ImageIO.write(imgOut, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null)
				try {
					output.close();
				} catch (IOException e) {
				}
		}
	}
}
