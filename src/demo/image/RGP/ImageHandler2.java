package demo.image.RGP;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageHandler2 {
	public static void main(String[] args) {
		System.out.println("Start");
//		ImageHandler2 imageHandler = new ImageHandler2();
//		imageHandler.grayImage();
		try {
			create();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("End");
	}
	/**
	 * 将图片分解成黑白图片
	 * RGB权重公式如下：      Y=0.2	989∗R+0.5870∗G+0.1140∗B
	 */
	public static void grayImage() {
		OutputStream output = null;
		try {
			// read image
			BufferedImage img = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\1.png"));
			//int imageType = img.getType();
			int w = img.getWidth();  // 图宽
			int h = img.getHeight(); // 图高
			int startX = 0;
			int startY = 0;
			int offset = 0;
			int scansize = w;
			int dd = w-startX;   //偏移剩余值
			int hh = h - startY;
			int x0 = w / 2; // 宽度中点
			int y0 = h / 2; // 高度中点
			System.out.println("dd:" + dd + "  hh:" + hh);
			// rgb的数组,保存像素，用一维数组表示二位图像像素数组
//			int[] rgbArray = new int[offset + hh * scansize + dd];
			int[] rgbArray = new int[h * w + w];
			// newArray 保存处理后的像素 
//			int[] newArray = new int[offset + hh * scansize + dd];
			int[] newArray = new int[h * w + w];
			img.getRGB(startX, startY, w, h, rgbArray, offset, scansize);
			
			int rgb = rgbArray[offset + (y0 - startY) * scansize + (x0 - startX)];
			Color c = new Color(rgb);
			System.out.println("中间像素点的rgb：" +c+" "+c.getRGB());
			/*
			 * 创建一个模板，里面是像素对象，包含属性x,y，
			 * 按原始顺序(一行一行)进行遍历，出现黑色则开始进行线索遍历
			 * 将遍历过的元素删掉，以便后续跳过对此像素的遍历
			 */
			// -- 做到了这里 start
			//创建 图形块 集合（黑像素的坐标集合）
			ArrayList<int[]> list = new ArrayList<int[]>(100);
			HashMap<Integer,ArrayList<int[]>> imgList = new HashMap<Integer,ArrayList<int[]>>();
			// -- 做到了这里 end
			//创建(坐标集合),遍历坐标集合,来辨别对应位置的像素，辨别过的坐标，设置为 -1
			HashMap<Integer,int[]> map = new HashMap<Integer,int[]>(h);
			for(int n=0;n<h;n++){
				int[] y_arr = new int[w]; //y坐标集合
				for(int m=0;m<w;m++){
					y_arr[m]=m;
				}
				map.put(n, y_arr);
			}
			for(int i=0; i<h-startY; i++) {
				for(int j=0; j<w-startX; j++) {
					//判断该像素点是否已经遍历过
					if(map.get(i)[j] == -1){
						continue;
					}
					c = new Color(rgbArray[i*dd + j]);
					//彩色图像转换成无彩色的灰度图像Y=0.299*R + 0.578*G + 0.114*B
					int gray = (int)(0.2989*c.getRed() + 0.587*c.getGreen() + 0.114*c.getBlue());
					//newArray[i*dd + j] = new Color(gray, gray, gray).getRGB();	//蓝色灰度图像
					//二值化 让灰度值小于等于127的变 为0（黑色），灰度值大于127的变为255（白色）
					//按像素灰度的二分值密度分区分块，以第一个黑色像素点位起点，按3像素宽度分出第一块像素块
					/*
					 * 从视觉上来看，我们时关注连续的整体，从而能感知到图形的曲线
					 * 而程序读取图片则是一行一行读取像素，中间断层太多，连不成一个整体
					 */
					gray = gray>200?255:0;
					if(gray>150){
						gray = 255; //白色点
					}else{
						gray = 0; //黑色点
						//当有黑色点出现时，就开始以此为起点，形成一个整体
					}
//					x = j;
//					y = i*dd;
					newArray[i*dd + j] = new Color(gray, gray, gray).getRGB();
				}
			}
			
			// create and save to bmp
			File out = new File("C:\\Users\\Administrator\\Desktop\\test2.png");
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
	 * 创建图片
	 * @throws IOException 
	 */
	@SuppressWarnings("null")
	public static void create() throws IOException{
		OutputStream output = null;
		int h = 400;  // i=高 ； j=宽
		int w = 200;
		int startX = 0;
		int startY = 0;
		int offset = 0;
		int scansize = w;
		int[] newArray = new int[h * w + w];
		// 0=黑   255=白
		/*
		 * 	纯白		255,255,255
		 *  浅灰色	211,211,211
		 *  银白色	192,192,192 (中间色)
		 *  深灰色	169,169,169
		 *  纯黑		0,0,0
		 *  
		 */
		for(int i=0; i<400; i++) {
			for(int j=0; j<200; j++) {
				newArray[i*w + j] = new Color(0, 0, 255).getRGB();
			}
		}
		File out = new File("C:\\Users\\Administrator\\Desktop\\new.png");
		if (!out.exists())
			out.createNewFile();
		output = new FileOutputStream(out);
		BufferedImage imgOut = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		imgOut.setRGB(startX, startY, w, h, newArray, offset, scansize);
		ImageIO.write(imgOut, "jpg", output);
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
