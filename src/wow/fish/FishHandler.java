package wow.fish;

import java.util.concurrent.ThreadLocalRandom;

import demo.MouseHandler;

public class FishHandler {
	private MouseHandler mouseHandler;
	public FishHandler() {
		 this.mouseHandler= new MouseHandler();
	}
	
	public void pullRod(int x,int y){
		x += ThreadLocalRandom.current().nextInt(5);
		//鼠标定位：
		
		mouseHandler.click();
	}
}
