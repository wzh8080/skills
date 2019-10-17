package test.ai;
import java.awt.AWTException;
import java.awt.Dimension;

import java.awt.Rectangle;

import java.awt.Robot;

import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;


public class Test2_GuiCamera {
	private String fileName; // 文件的前缀
	private String defaultName = "GuiCamera";
	static int serialNum = 0;
	private String imageFormat; // 图像文件的格式
	private String defaultImageFormat = "png";
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	public Test2_GuiCamera() {
		fileName = "C:/Users/King/Desktop/";
		imageFormat = defaultImageFormat;
	}
	public Test2_GuiCamera(String s, String format) {
		fileName = s;
		imageFormat = format;
	}
	/**
	 * 截屏（已整合进Screenshots）
	 * @return
	 */
	public String snapShot() {
		try {
			Thread.sleep(2*1000);
			// 拷贝屏幕到一个BufferedImage对象screenshot
			BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(50, 25, (int) d.getWidth()-100, (int) d.getHeight()-50));
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
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		return "";
	}
	/**
	 * 截屏（小）（已整合进Screenshots）
	 * @return
	 */
	public String snapShot(int x,int y,int w,int h) {
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
			return name;
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		return "";
	}
	/**
	 * 辨别图片中文字出现的位置，并标记
	 * @param result
	 * @param point	需要点击的目标
	 * @throws AWTException 
	 */
	private String getXY(String result,String point) throws AWTException {
		//{"location": {"width": 394, "top": 289, "left": 85, "height": 40}, "words": "来描述、发布、发现、协调"}, 
		double x = 0;
		double y = 0;
		/*JSONObject r = JSONObject.fromObject(result);
		JSONArray jsonArray = r.getJSONArray("words_result");
		for(int i=0;i<jsonArray.size();i++){
			JSONObject row = jsonArray.getJSONObject(i);
			String words = row.getString("words");
			if(!words.contains(point)){
				//System.out.println(words);
				continue;
			}
			
			JSONObject location = row.getJSONObject("location");
			int left = Integer.valueOf(location.getString("left")); //x
			int top = Integer.valueOf(location.getString("top"));   //y
			int width = Integer.valueOf(location.getString("width"));
			int height = Integer.valueOf(location.getString("height"));
			System.out.println(location.toString());
			System.out.println("截取的信息为："+words);
			int length = words.length();
			int indexOf = words.indexOf(point);
			System.out.println(indexOf);
			//单个词站位：大概
			x = (Math.round(width/length*(indexOf+point.length()/2)))+left;
			y = height/2 + top;
			System.out.println("X = "+x);
			System.out.println("Y = "+y);
		}*/
//		clickXY((int)x, (int)y);
		return ""+x+"@"+y;
	}
	/**
	 * 鼠标点击事件（已整合进 MouseHandler）
	 * @param x
	 * @param y
	 * @throws AWTException
	 */
	private static void clickXY(int x, int y) throws AWTException {
		Robot robot = new Robot();
		robot.delay(1000); 
		robot.mouseMove((int)x, (int)y); //移动到 mouseMove
		robot.delay(500); 
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);//按下左键（BUTTON后面的1改成2和3即可，2是按下滚轮，3是按下鼠标右键。）
		robot.delay(200); //间隔时间
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);  //鼠标抬起方法。
		robot.delay(1000);
	}
	/**
	 * 进行识别 图像识别
	 * @param name
	 * @return
	 */
	private String doCheck(String name) {
		TestAi t = new TestAi();
		//文字识别
        String clientId = "RMGSkRhPZVdS67XPmyXFM3pl";	// API Key
        String clientSecret = "G2pFTWiYMXx3LXwnRHWukHzoZrgZ05n7";	// Secret Key
		String token = AuthService.getAuth(clientId,clientSecret);
		System.out.println(token);
//      String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
//      String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general"; //通用物体和场景识别高级版
//      String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/object_detect"; //图像主体检测(长宽高)
//      String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/logo"; //图像主体检测(长宽高)
//		String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic"; //文字识别
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general";  //通用文字识别（含位置信息版）500次/天免费
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate";  //通用文字识别（高精度含位置版） 50次
        
		String result = t.testPhoto(token,name,url);
		return result;
	}
	public static void main(String[] args) throws Exception {
		String name = "1";
		
		Test2_GuiCamera t = new Test2_GuiCamera("E:\\Amarsoft\\My\\Game\\Photo\\"+name, "jpg");//C:/Users/King/Desktop
		
		Thread.sleep(1*1000);//等待开始
		t.clickXY(1780, 970); //点击 活动
//		Thread.sleep(1*1000);
		String nameTemp = t.snapShot(745, 470, 380, 220);//截取最前边的
		String result = t.doCheck(nameTemp); //辨别
		doAction(result);
		
//		String xy = t.getXY(result.toString(), point);
//		if(xy)
//		int x = Integer.valueOf(xy.split("@")[0]);
//		int y = Integer.valueOf(xy.split("@")[1]);
//		t.clickXY(x, y); 
		
//		String fileName = t.snapShot();  //截取
//		System.out.println(fileName);
//		String result = t.doCheck(fileName); //辨别
//		System.out.println(result);
		//画,读取json，生成json
//		String fileName = "E:\\Amarsoft\\My\\Game\\主界面.txt";
//		String result = getJsonString(fileName);
		
//		String point = "日任务";//需要点击的字
//		String xy = t.getXY(result.toString(), point);
//		System.out.println("xy=="+xy);
//		int x = Integer.valueOf(xy.split("@")[0]);
//		int y = Integer.valueOf(xy.split("@")[1]);
//		clickXY(x, y); 
		
	}
	/**
	 * 进行活动
	 * @param result
	 * @return
	 * @throws AWTException 
	 */
	private static String doAction(String result) throws AWTException {
		/*JSONObject r = JSONObject.fromObject(result);
		JSONArray jsonArray = r.getJSONArray("words_result");
		for(int i=0;i<jsonArray.size();i++){
			JSONObject row = jsonArray.getJSONObject(i);
			String words = row.getString("words");
			System.out.println(words);
			if(words.contains("讨伐任务")){
				//每日任务
				clickXY(1490, 510);  //R1
				doMRRW();
				
				continue;
			}else if(words.contains("每")){
				doMRRW();//每日任务
			}else if(words.contains("剧情")){
				//每日任务
				clickXY(525, 505);  //L1
				doMRRW();
			}else if(words.contains("角色传记")){
				//每日任务
				clickXY(610, 255);  //L2
				doMRRW();
			}else if(words.contains("航海")){
				//每日任务
				clickXY(1020, 190);  //end
				doMRRW();
			}else if(words.contains("竞技场")){
				//每日任务
				clickXY(1405, 275);  //R2
				doMRRW();
			}
		}*/
		return null;
	}
	/**
	 * 每日任务
	 * @throws AWTException 
	 */
	private static void doMRRW()  {
		try {
			
			clickXY(1000, 645);//点击原点任务
			clickXY(980, 870);//前往
			clickXY(1340, 760);	//接受任务
			clickXY(250, 400);	//执行任务
			//等待任务的执行  10:10分开始  10:27结束
			Thread.sleep(17*60*1000);
			clickXY(1120, 885);	//再来一轮
			clickXY(250, 400);	//执行任务
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 1.读取保存的Json文件，生成json字符串（已整合进JsonTools）
	 * @param fileName2
	 * @return
	 * @throws Exception 
	 */
	private static String getJsonString(String fileName2) throws Exception {
		File file = new File("E:\\Amarsoft\\My\\Game\\主界面.txt");
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String line;
        StringBuffer result = new StringBuffer();
        while ((line = br.readLine()) != null) {
            result.append(line);
		}
		System.out.println(result);
		br.close();
		return result.toString();
	}
}
