package wow.fish;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GetFishRed {
	public void getRed() throws IOException{
		BufferedImage img = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\wow_001.png"));
		int w = img.getWidth();
		int h = img.getHeight();
		// rgb的数组,保存像素，用一维数组表示二位图像像素数组
		int[] rgbArray = new int[h * w];
		
		ArrayList<Integer[]> list = new ArrayList<Integer[]>();
		for(int i=0; i<h; i++) {     // 高 y
			for(int j=0; j<w; j++) { // 宽 x
				Color c = new Color(rgbArray[i*w + j]);
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();
				if(red>190 && green<140 && blue<140){
					Integer[] arr = new Integer[2];
					arr[0] = j;
					arr[1] = i;
					list.add(arr);
					System.out.println(j+" - "+i);
				}
			}
		}
		
		for(Integer[] arr :list){
			int x = arr[0];
			int y = arr[1];
			
		}
	}
}
