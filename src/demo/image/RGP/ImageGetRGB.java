package demo.image.RGP;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import test.ai.Test2_GuiCamera;

public class ImageGetRGB {
	public static void main(String args[]) throws Exception {
        BufferedImage image = null;
        File f = null;

        try {
        	Test2_GuiCamera t = new Test2_GuiCamera();
        	String snapShot = t.snapShot();
//            f = new File("C:\\Users\\King\\Desktop\\aaa.png");
        	System.out.println(snapShot);
            f = new File(snapShot);
            image = ImageIO.read(f);
            
            int width = image.getWidth();
            int height = image.getHeight();
            HashMap<Integer, ArrayList<int[]>> map = new HashMap<Integer,ArrayList<int[]>>(); //��ɫ  - ���ص�λ�� //��ɫһ�����Ĳ�ͬ���ص㼯��
            
            int sum = 0;
            HashMap<Object, Object> map2 = new HashMap<>();
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    int p = image.getRGB(i, j);
                    if(map.get(p) != null){
                    	map.get(p).add(new int[]{i,j});
                    }else{
                    	ArrayList<int[]> list = new ArrayList<int[]>();
                    	list.add(new int[]{i,j});
                    	map.put(p, list);
                    }
                }
            }
            
            //���µ�ͼ�� 	255,215,0
            BufferedImage imageNew = new BufferedImage(width, height, 1);
            int count = 0;
            for(Entry e:map.entrySet()){
            	count++;
            	int key = (int) e.getKey();
//            	if(key!=-11579569){
//            		continue;
//            	}
				 int a = (key >> 24) & 0xff;
				 int r = (key >> 16) & 0xff;
				 int g = (key >> 8) & 0xff;
				 int b = key & 0xff;
				 System.out.println("A="+a+"  R:"+r+" G:"+g+" B:"+b+"  P"+key);
            	ArrayList<int[]> list = (ArrayList<int[]>) e.getValue();
            	for(int[] i:list){
            		int p = (255 << 24) | (255 << 16) | (255 << 8) | 255;
//            		System.out.println("���� x = "+i[0]+" y = "+i[1]);
            		imageNew.setRGB(i[0], i[1], p);
            	}
            	if(count == 2){ //P-11579569 ���
            		break;
            	}
            }
            f = new File("C:\\Users\\King\\Desktop\\test2.jpg");
            ImageIO.write(imageNew, "jpg", f);
            
//            for(Entry e:map.entrySet()){
//            	int p = (int) e.getKey();
//            	int a = (p >> 24) & 0xff;
//                int r = (p >> 16) & 0xff;
//                int g = (p >> 8) & 0xff;
//                int b = p & 0xff;
//                int n = Math.abs((r+g+b)-sum);
//                String xy = (String) e.getValue();
//                int i = Integer.valueOf(xy.split("/")[0]);
//                int j = Integer.valueOf(xy.split("/")[1])+200;
//                if(n/3>35){
//                	map2.put(i+"/"+j ," {"+r+","+g+","+b+"}");
//                	System.out.println(i+"/"+j +"  "+" {"+r+","+g+","+b+"}");
////                	clickXY(i,j);
//                }
//            }
            System.out.println("width = "+width);
            System.out.println("height = "+height);
            System.out.println("size = "+map.size()); //691
            System.out.println("size = "+map2.size()); //691
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * ������¼�
	 * @param x
	 * @param y
	 * @throws AWTException
	 */
	private static void clickXY(int x, int y) throws AWTException {
		Robot robot = new Robot();
		robot.delay(200); 
		robot.mouseMove((int)x, (int)y); //�ƶ��� mouseMove
		robot.delay(200); 
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);//���������BUTTON�����1�ĳ�2��3���ɣ�2�ǰ��¹��֣�3�ǰ�������Ҽ�����
		robot.delay(200); //���ʱ��
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);  //���̧�𷽷���
		//robot.delay(200);
	}
}
