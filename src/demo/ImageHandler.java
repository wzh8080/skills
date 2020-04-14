package demo;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandler {
	
	private String fileName; // 文件的前缀
	private int serialNum = 0;
	private String imageFormat; // 图像文件的格式
	private String defaultImageFormat = "png";// 图像文件的默认格式
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //
	
	public ImageHandler() {
		fileName = "C:/Users/King/Desktop/";
		imageFormat = defaultImageFormat;
	}
	public ImageHandler(String s, String format) {
		fileName = s;
		imageFormat = format;
	}
	
	/**
	 * 截取整个屏幕
	 * @return 返回成功状态
	 */
	public String getScreen(){
		try {
			Thread.sleep(2*1000);
			
			Robot robot = new Robot();
			// 拷贝屏幕到一个BufferedImage对象screenshot
			BufferedImage screenshot = 
					robot.createScreenCapture(
							new Rectangle(50, 25, 
									(int) d.getWidth()-100, 
									(int) d.getHeight()-50)
							);
			serialNum++;
			// 根据文件前缀变量和文件格式变量，自动生成文件名
			String name = fileName + String.valueOf(serialNum) + "."
					+ imageFormat;
			File f = new File(name);
			System.out.print("Save File " + name);
			// 将screenshot对象写入图像文件
			ImageIO.write(screenshot, imageFormat, f);
			System.out.print("..Finished!\n");
			return name;
		}catch (InterruptedException e) {
			e.printStackTrace();//线程异常
		}catch (HeadlessException e) {
		e.printStackTrace();
		} catch (AWTException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		System.out.println("截图成功");
		return "";
	}
	
	/**
	 * 截取指定大小的屏幕
	 * @param x 左上角点的像素横坐标
	 * @param y 左上角点的像素纵坐标
	 * @param w 截取的宽度
	 * @param h 截取的高度
	 * @return 返回成功状态
	 */
	public String getScreen(int x,int y,int w,int h) {
		try {
			Thread.sleep(1*1500);
			// 拷贝屏幕到一个BufferedImage对象screenshot
			BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(x, y, w, h));
			serialNum++;
			// 根据文件前缀变量和文件格式变量，自动生成文件名
			String name = fileName + String.valueOf(serialNum) + "Temp."
					+ imageFormat;
			File f = new File(name);
			System.out.print("Save File " + name);
			// 将screenshot对象写入图像文件
			ImageIO.write(screenshot, imageFormat, f);
			System.out.print("..Finished!\n");
		}catch (InterruptedException e) {
			e.printStackTrace();//线程异常
		}catch (HeadlessException e) {
		e.printStackTrace();
		} catch (AWTException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		System.out.println("截图成功");
		return "";
	}
	
	
	
}
