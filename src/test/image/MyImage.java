package test.image;

import java.awt.Color;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

/**
 * 名词解释:
 * 饱和度是指色彩的鲜艳程度，也称色彩的纯度。
 * 灰度：使用黑色调表示物体，即用黑色为基准色，不同的饱和度的黑色来显示图像。
 * 像素：如同摄影的相片一样，数码影像也具有连续性的浓淡阶调，我们若把影像放大数倍，会发现这些连续色调其实是由许多色彩相近的小方点所组成，
 * 这些小方点就是构成影像的最小单元——像素。是分辨率的尺寸单位。
 * 像素是基本原色素及其灰度的基本编码。我们看到的数字图片是有一个二维的像素矩阵组成。
 * 像素在计算机中通常用3个字节24位保存，如16-23 位表示红色（R）分量，8-15 位表示绿色(G)分量，0-7 位表示蓝色(B)分量；
 * 当图片尺寸以像素为单位时，每一厘米等于28像素，比如15*15厘米长度的图片，等于420*420像素的长度。 
 * 一个像素所能表达的不同颜色数取决于比特每像素(BPP)。如8bpp[2^8=256色， 灰度图像]、16bpp[2^16=65536色，称为高彩色]、24bpps[2^24=16777216色，称为真彩色]。
 *  分辨率：图像总像素的多少，称为图像分辨率。
 *  RGB： 颜色模型，是将颜色表示成数字形式的模型，或者说是一种记录图像颜色的方式。详情百度
 *  
 *  下列代码是将一个图片分解成R,G,B三种色彩灰度图片的算法
 * 也可参考原著为：http://blog.csdn.net/luoweifu/article/details/8042494
 */

public class MyImage {
	// 将图片分解为R,G,B三种灰度图片  
	public static void main(String[] args) {
		String filePath = "C:\\Users\\King\\Desktop\\test.png";
		String newFilePath = "C:\\Users\\King\\Desktop\\test2.png";
		int type = 1;
		analyseRGB(filePath, newFilePath, type);
	}
		/**
		 * 
		 * @param filePath 原图片路径
		 * @param newFilePath 您想要生成的图片路径
		 * @param type 选择生成类型1为R,2为G,3为B
		 */
		public static void analyseRGB(String filePath, String newFilePath, int type) {
			OutputStream output = null;
			try {
				BufferedImage img = ImageIO.read(new File(filePath));
				int imageType = img.getType();// 获取图片类型
				int width = img.getWidth();// 获取图片宽度
				int height = img.getHeight();// 获取图片高度
				int startX = 0;// 开始的横坐标
				int startY = 0;// 开始的纵坐标
				int offset = 0;// 偏移量
				int scansize = width;// 扫描间距
				int dd = width - startX;// 被遍历的宽度间距
				int hh = height - startY;// 被遍历的高度间距
				int x0 = width / 2;// 横向中间点
				int y0 = height / 2;// 纵向中间点
				System.out.println("dd:" + dd + " hh:" + hh);
				System.out.println("width:" + width + " height:" + height);
				System.out.println("imageType:" + imageType);
				System.out.println("size:"+(offset + hh * scansize + dd));
				// rgb的数组，保存像素，用一维数组表示二位图像像素数组
				int[] rgbArray = new int[offset + hh * scansize + dd];// 偏移量+纵向开始坐标*扫描间距+横向开始坐标
				//这里大家都感觉很奇怪为什么会是这样一个算法呢？为什么不知道用width*height就够用了，这里作者也搞不懂，你只要默认记住了这个规则，
				//然后取点的时候按这个规则去取就可以了
				// newArray 保存处理后的像素
				int[] newArray = new int[offset + hh * scansize + dd];// 偏移量+纵向开始坐标*扫描间距+横向开始坐标
				/**
				 * getRGB public int[] getRGB(int startX, int startY, int w, int h,
				 * int[] rgbArray, int offset, int scansize)从图像数据的某一部分返回默认 RGB 颜色模型
				 * (TYPE_INT_ARGB) 和默认 sRGB 颜色空间中整数像素数组。如果该默认模型与该图像的 ColorModel
				 * 不匹配，则发生颜色转换。在使用此方法所返回的数据中，每个颜色分量只有 8 位精度。通过图像中指定的坐标 (x, y)，ARGB
				 * 像素可以按如下方式访问：
				 * 
				 * pixel = rgbArray[offset + (y-startY)*scansize + (x-startX)];
				 * 如果该区域不在边界内部，则抛出 ArrayOutOfBoundsException。但是，不保证进行显式的边界检查。
				 * 
				 * 
				 * 参数：
				 *  startX - 起始 X 坐标 
				 *  startY - 起始 Y 坐标 
				 *  w - 区域的宽度 
				 *  h - 区域的高度
				 * rgbArray - 如果不为 null，则在此写入 rgb 像素 
				 * offset - rgbArray 中的偏移量
				 * scansize - rgbArray 的扫描行间距 
				 * 返回： RGB 像素数组。
				 */
				img.getRGB(startX, startY, width, height, rgbArray, offset,
						scansize); // 把像素存到固定的数组里面去
				int count=0;
				for(int i : rgbArray){
					System.out.println(i);
					if(i!=0){
						count=count+1;
					}
				}
				System.out.println(count);
				int rgb = rgbArray[offset + (y0 - startY) * scansize
						+ (x0 - startX)]; // 位于图片正中央的rgb像素点
				Color c = new Color(rgb);
				System.out.println("中间像素点的rgb:"+c);
				for (int i = 0; i < height - startY; i++) {//遍历每一行
					for (int j = 0; j < width - startX; j++) {//遍历每一列
						c = new Color(rgbArray[offset+i * scansize + j]);
						switch (type) {
						case 1://红色灰度图片
							newArray[i*dd + j] = new Color(c.getRed(), 0, 0).getRGB();
							break;
						case 2://绿色灰度图片
							 newArray[i*dd + j] = new Color(0, c.getGreen(), 0).getRGB(); 
							break;
						case 3://蓝色灰度图片
							newArray[i * dd + j] = new Color(0, 0, c.getBlue()).getRGB();
							break;
	 
						default:
							break;
						}
	 
						
						
					}
				}
				// 新建一个图像
				File out = new File(newFilePath);
				if (!out.exists())
					out.createNewFile();
				output = new FileOutputStream(out);
				BufferedImage imgOut = new BufferedImage(width, height,
						BufferedImage.TYPE_3BYTE_BGR);
				imgOut.setRGB(startX, startY, width, height, newArray, offset,
						scansize);
				ImageIO.write(imgOut, "jpg", output);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
}
