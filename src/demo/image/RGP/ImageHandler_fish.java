package demo.image.RGP;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

public class ImageHandler_fish {
	public static void main(String[] args) {
		System.out.println("Start");
		ImageHandler_fish imageHandler = new ImageHandler_fish();
		imageHandler.grayImage();
		System.out.println("End");
	}
	/**
	 * 将图片分解成黑白图片
	 * RGB权重公式如下： Y=0.2989∗R+0.5870∗G+0.1140∗B
	 */
	static int w = 0;
	static int h = 0;
	static HashMap<String,Integer> map = null;
	
	public static void grayImage() {
		OutputStream output = null;
		try {
			// read image
			BufferedImage img = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\wow_001.png"));
			int imageType = img.getType();
			w = img.getWidth();
			h = img.getHeight();
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
			// 将像素存放在 xy 象限 的map中，key为xy，value为像素值
			map = new HashMap<String,Integer>();
			int minX = 999;
			int minY = 999;
			int maxX = 0;
			int maxY = 0;
			for(int i=0; i<h-startY; i++) {     // 高 y
				for(int j=0; j<w-startX; j++) { // 宽 x
					c = new Color(rgbArray[i*dd + j]);
					//彩色图像转换成无彩色的灰度图像Y=0.299*R + 0.578*G + 0.114*B
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					if(red>200 && green<130 && blue<130){
						System.out.println(j+" - "+i);
						minX = minX<j?minX:j;
						minY = minY<i?minY:i;
						maxX = maxX>j?maxX:j;
						maxY = maxY>i?maxY:i;
						red = 0;
						green = 0;
						blue = 0;
					}
					newArray[i*dd + j] = new Color(red, green, blue).getRGB();
					//newArray[i*dd + j] = new Color(gray, gray, gray).getRGB();	//蓝色灰度图像
				}
			}
//			// 像素根据周围色素改变
//			for(Entry<String, Integer> e:map.entrySet()){
//				int g = e.getValue();
//				int x = Integer.valueOf(e.getKey().split(",")[0]);
//				int y = Integer.valueOf(e.getKey().split(",")[1]);
//				if(g<255){ //直接令Entry中的值，等于另一个值，map中会不会改变？
//					g = handler(x,y,g);
//					map.put(e.getKey(), g);
//				}
//			}
//			// 重塑 像素集合 newArray
//			for(int i=0; i<h; i++) {     // 高 y
//				for(int j=0; j<w; j++) { // 宽 x
//					int g = map.get(j+","+i);
//					newArray[i*dd + j] = new Color(g, g, g).getRGB();
//				}
//			}
//			
			// create and save to bmp
			File out = new File("C:\\Users\\Administrator\\Desktop\\wow_test.png");
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
	 * 如果是黑色，则周围有1个像素相同就不变
	 * 如果不是黑色，将其置为与其周围像素值最多的像素；
	 * @param x
	 * @param y
	 * @param g
	 * @return 
	 */
	private static int handler(int x, int y, int g) {
		/*
		    255
			211
			192
			169
		 */
		HashMap<Integer,Integer> count = new HashMap<Integer,Integer>(); // 像素，个数
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				if(i==x&&j==y)continue;
				int gray = getGray(x+i,y+j);
				
				if(count.get(gray) == null){
					count.put(gray, 1);
				}else{
					count.put(gray, count.get(gray)+1);
				}
			}
		}
		// 如果是黑色,且四周有黑色，则不变
		if(g==0 && count.get(0)>=1) return g;
		// 如果不是黑色，则按最多色素
		int max_gray = 0;
		int max_count = 0;
		for(Entry<Integer,Integer> e:count.entrySet()){
			if(max_count>e.getValue()){
				max_count = e.getValue();
				max_gray = e.getKey();
			}
		}
		return max_gray;
		
	}
	private static int getGray(int x, int y) {
		if(x<0||x>=w||y<0||y>=h)return -1;
		return map.get(x+","+y);
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
