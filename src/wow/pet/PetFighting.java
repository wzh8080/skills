package wow.pet;

import java.awt.AWTException;
import java.awt.Robot;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import demo.MouseHandler;

public class PetFighting {
	private MouseHandler mouse = null;
	public PetFighting(MouseHandler mouse) {
		this.mouse = mouse;
	}


	public static void main(String[] args) throws InterruptedException, AWTException {
		System.out.println("开始");
		
		Robot robot = new Robot();
		MouseHandler mouse = new MouseHandler(robot);
		PetFighting p = new PetFighting(mouse);
		
		long start = System.currentTimeMillis();
		long end = start + (4*60*1000);
		while(true){
			/*Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat();
			String endTime = sdf.format(d);  //19-11-4 下午11:40
			if(endTime.substring(0,endTime.length()-3).contains("6")){
				break;
			}*/
//			start = System.currentTimeMillis();
//			if(end<start){
//				Thread.sleep(3*60*1000+40*1000);
//				start = System.currentTimeMillis();
//				end = start + (4*60*1000);
//			}
			p.test(robot);
		}
		//System.out.println("结束");
	}
	
	
	public void test(Robot robot){
		
		//robot.delay(1500);
		int n = ThreadLocalRandom.current().nextInt(5)+1;
		int sleep = ThreadLocalRandom.current().nextInt(270)+100;  //80~220
//		if(sleep%7==3){
//			int move = ThreadLocalRandom.current().nextInt(6)+1;  //1~7
//			robot.mouseMove(x, y);
//		}
		robot.delay(sleep); //间隔时间
		robot.mouseWheel(n);//向上滑动滚轮
		sleep = ThreadLocalRandom.current().nextInt(240)+200;  //80~220
		//move = ThreadLocalRandom.current().nextInt(6)+1;  //1~7
		robot.delay(sleep); //间隔时间
		robot.mouseWheel(-n);//向下滑动滚轮
		//偶尔鼠标拖动，加跳动
		int m1 = ThreadLocalRandom.current().nextInt(30);
		if(m1 == 13){
			//拖动
			//mouse.
		}
		int m2 = ThreadLocalRandom.current().nextInt(20);
		if(m2 == 4){
			//跳一下
		}
	}
}
