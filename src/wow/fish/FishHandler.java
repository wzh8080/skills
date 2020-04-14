package wow.fish;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

import demo.MouseHandler;

public class FishHandler {
	//private MouseHandler mouseHandler;
	private Robot robot;
	private int sleep=50;
//	public FishHandler() {
//		 this.mouseHandler= new MouseHandler(robot);
//	}
	
	/**
	 * 收杆
	 * @param x
	 * @param y
	 */
	public void pullRod(int x,int y){
		sleep = ThreadLocalRandom.current().nextInt(140)+80;  //80~220
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);//按下左键（BUTTON后面的1改成2和3即可，2是按下滚轮，3是按下鼠标右键。）
		robot.delay(sleep); //间隔时间
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);  //鼠标抬起方法。
	}
	/**
	 * 放杆
	 */
	public void pushRod(){
		sleep = ThreadLocalRandom.current().nextInt(61)+45;  //45~106
	    robot.keyPress(KeyEvent.VK_8);
	    robot.delay(sleep); //间隔时间
	    robot.keyRelease(KeyEvent.VK_8);
	}
	
	/**
	 * 获得浮漂位置
	 * @return
	 */
	public int[] getPoint(int x,int y,int width,int height){
		//截取屏幕
		//获得位置
		//仿人工优化
		return null;
	}
}
