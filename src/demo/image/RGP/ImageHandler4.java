package demo.image.RGP;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
/**
 * 相似度
 * 
	样例图片，中心点：x y 的像素值 = n
	
	目标图片：
	循环找与n相同的点，以该点为中心，探索周围像素与样例像素的差值
	
	背景有差异：
	将每个色素都换成黑 和 白 ，即：1 ，0 ；以中心色素为中心点，向外扩散，查看相邻色素与其的差异，相近则统一，不同则取其反
 * @author Administrator
 *
 */
public class ImageHandler4 {
	public static void main(String[] args) throws IOException {
		//目标
		BufferedImage img1 = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\1.png"));
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		int[] rgbArray1 = new int[h1 * w1 + w1];
		img1.getRGB(0, 0, w1, h1, rgbArray1, 0, w1);
		
		//样例
		BufferedImage img2 = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\2.png"));
		int w2 = img2.getWidth();
		int h2 = img2.getHeight();
		int x02 = w2/2;
		int y02 = h2/2;
		int[] rgbArray2 = new int[h2 * w2 + w2];
		img1.getRGB(0, 0, w2, h2, rgbArray2, 0, w2);
		
		//样例中间点像素：
		int rgb2 = rgbArray2[y02 * w2 + x02];
		Color color2 = new Color(rgb2);
		int red = color2.getRed();
		int green = color2.getGreen();
		int blue = color2.getBlue();
		
		//分析样例像素  由中点出发的色度变化，设中间点像素为黑，即
		int[] rgbArray2New = new int[h2 * w2 + w2];
		rgbArray2New[y02 * w2 + x02] = new Color(0, 0, 0).getRGB();
		
		
		//在目标中查找与之相近的像素
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
//			int imageType = img.getType();
			int w = img.getWidth();
			int h = img.getHeight();
			
			int x0 = w / 2;
			int y0 = h / 2;
			
			System.out.println("宽度 dd:" + w + "  高度 hh:" + h); //宽 高
			
			// rgb的数组,保存像素，用一维数组表示二位图像像素数组
			int[] rgbArray = new int[h * w + w];
			//newArray 保存处理后的像素
			int[] newArray = new int[h * w + w];
			img.getRGB(0, 0, w, h, rgbArray, 0, w);
			
			//获得像素：此点前边有多少个像素点，则它在像素数组中的下标就为几
			int rgb = rgbArray[y0 * w + (x0)];
			Color c = new Color(rgb);
			System.out.println("中间像素点的rgb：  "+c.getRGB());
			
			
			for(int i=0; i<h; i++) {
				for(int j=0; j<w; j++) {
					c = new Color(rgbArray[i*w + j]);
					//彩色图像转换成无彩色的灰度图像Y=0.299*R + 0.578*G + 0.114*B
					int gray = (int)(0.299*c.getRed() + 0.578*c.getGreen() + 0.114*c.getBlue());
					newArray[i*w + j] = new Color(gray, gray, gray).getRGB();	//蓝色灰度图像
				}
			}
			
			// create and save to bmp
			File out = new File("C:\\Users\\Administrator\\Desktop\\2.png");
			if (!out.exists())
				out.createNewFile();
			output = new FileOutputStream(out);
			BufferedImage imgOut = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
			imgOut.setRGB(0, 0, w, h, newArray, 0, w);
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
	 * 获得像素数组
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static int[] getRgbArray(String file) throws IOException{
		// read image
		BufferedImage img = ImageIO.read(new File(file));
//					int imageType = img.getType();
		int w = img.getWidth();
		int h = img.getHeight();
		
		System.out.println("宽度 dd:" + w + "  高度 hh:" + h); //宽 高
		
		// rgb的数组,保存像素，用一维数组表示二位图像像素数组
		int[] rgbArray = new int[h * w + w];
		//newArray 保存处理后的像素
		img.getRGB(0, 0, w, h, rgbArray, 0, w);
					
		return rgbArray;
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
