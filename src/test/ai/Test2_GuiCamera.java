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
	private String fileName; // �ļ���ǰ׺
	private String defaultName = "GuiCamera";
	static int serialNum = 0;
	private String imageFormat; // ͼ���ļ��ĸ�ʽ
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
	 * �����������Ͻ�Screenshots��
	 * @return
	 */
	public String snapShot() {
		try {
			Thread.sleep(2*1000);
			// ������Ļ��һ��BufferedImage����screenshot
			BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(50, 25, (int) d.getWidth()-100, (int) d.getHeight()-50));
			serialNum++;
			// �����ļ�ǰ׺�������ļ���ʽ�������Զ������ļ���
			String name = fileName + String.valueOf(serialNum) + "."
					+ imageFormat;
			File f = new File(name);
			System.out.print("Save File " + name);
			// ��screenshot����д��ͼ���ļ�
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
	 * ������С���������Ͻ�Screenshots��
	 * @return
	 */
	public String snapShot(int x,int y,int w,int h) {
		try {
			Thread.sleep(1*1500);
			// ������Ļ��һ��BufferedImage����screenshot
			BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(x, y, w, h));
			serialNum++;
			// �����ļ�ǰ׺�������ļ���ʽ�������Զ������ļ���
			String name = fileName + String.valueOf(serialNum) + "Temp."
					+ imageFormat;
			File f = new File(name);
			System.out.print("Save File " + name);
			// ��screenshot����д��ͼ���ļ�
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
	 * ���ͼƬ�����ֳ��ֵ�λ�ã������
	 * @param result
	 * @param point	��Ҫ�����Ŀ��
	 * @throws AWTException 
	 */
	private String getXY(String result,String point) throws AWTException {
		//{"location": {"width": 394, "top": 289, "left": 85, "height": 40}, "words": "�����������������֡�Э��"}, 
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
			System.out.println("��ȡ����ϢΪ��"+words);
			int length = words.length();
			int indexOf = words.indexOf(point);
			System.out.println(indexOf);
			//������վλ�����
			x = (Math.round(width/length*(indexOf+point.length()/2)))+left;
			y = height/2 + top;
			System.out.println("X = "+x);
			System.out.println("Y = "+y);
		}*/
//		clickXY((int)x, (int)y);
		return ""+x+"@"+y;
	}
	/**
	 * ������¼��������Ͻ� MouseHandler��
	 * @param x
	 * @param y
	 * @throws AWTException
	 */
	private static void clickXY(int x, int y) throws AWTException {
		Robot robot = new Robot();
		robot.delay(1000); 
		robot.mouseMove((int)x, (int)y); //�ƶ��� mouseMove
		robot.delay(500); 
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);//���������BUTTON�����1�ĳ�2��3���ɣ�2�ǰ��¹��֣�3�ǰ�������Ҽ�����
		robot.delay(200); //���ʱ��
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);  //���̧�𷽷���
		robot.delay(1000);
	}
	/**
	 * ����ʶ�� ͼ��ʶ��
	 * @param name
	 * @return
	 */
	private String doCheck(String name) {
		TestAi t = new TestAi();
		//����ʶ��
        String clientId = "RMGSkRhPZVdS67XPmyXFM3pl";	// API Key
        String clientSecret = "G2pFTWiYMXx3LXwnRHWukHzoZrgZ05n7";	// Secret Key
		String token = AuthService.getAuth(clientId,clientSecret);
		System.out.println(token);
//      String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
//      String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general"; //ͨ������ͳ���ʶ��߼���
//      String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/object_detect"; //ͼ��������(�����)
//      String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/logo"; //ͼ��������(�����)
//		String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic"; //����ʶ��
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general";  //ͨ������ʶ�𣨺�λ����Ϣ�棩500��/�����
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate";  //ͨ������ʶ�𣨸߾��Ⱥ�λ�ð棩 50��
        
		String result = t.testPhoto(token,name,url);
		return result;
	}
	public static void main(String[] args) throws Exception {
		String name = "1";
		
		Test2_GuiCamera t = new Test2_GuiCamera("E:\\Amarsoft\\My\\Game\\Photo\\"+name, "jpg");//C:/Users/King/Desktop
		
		Thread.sleep(1*1000);//�ȴ���ʼ
		t.clickXY(1780, 970); //��� �
//		Thread.sleep(1*1000);
		String nameTemp = t.snapShot(745, 470, 380, 220);//��ȡ��ǰ�ߵ�
		String result = t.doCheck(nameTemp); //���
		doAction(result);
		
//		String xy = t.getXY(result.toString(), point);
//		if(xy)
//		int x = Integer.valueOf(xy.split("@")[0]);
//		int y = Integer.valueOf(xy.split("@")[1]);
//		t.clickXY(x, y); 
		
//		String fileName = t.snapShot();  //��ȡ
//		System.out.println(fileName);
//		String result = t.doCheck(fileName); //���
//		System.out.println(result);
		//��,��ȡjson������json
//		String fileName = "E:\\Amarsoft\\My\\Game\\������.txt";
//		String result = getJsonString(fileName);
		
//		String point = "������";//��Ҫ�������
//		String xy = t.getXY(result.toString(), point);
//		System.out.println("xy=="+xy);
//		int x = Integer.valueOf(xy.split("@")[0]);
//		int y = Integer.valueOf(xy.split("@")[1]);
//		clickXY(x, y); 
		
	}
	/**
	 * ���л
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
			if(words.contains("�ַ�����")){
				//ÿ������
				clickXY(1490, 510);  //R1
				doMRRW();
				
				continue;
			}else if(words.contains("ÿ")){
				doMRRW();//ÿ������
			}else if(words.contains("����")){
				//ÿ������
				clickXY(525, 505);  //L1
				doMRRW();
			}else if(words.contains("��ɫ����")){
				//ÿ������
				clickXY(610, 255);  //L2
				doMRRW();
			}else if(words.contains("����")){
				//ÿ������
				clickXY(1020, 190);  //end
				doMRRW();
			}else if(words.contains("������")){
				//ÿ������
				clickXY(1405, 275);  //R2
				doMRRW();
			}
		}*/
		return null;
	}
	/**
	 * ÿ������
	 * @throws AWTException 
	 */
	private static void doMRRW()  {
		try {
			
			clickXY(1000, 645);//���ԭ������
			clickXY(980, 870);//ǰ��
			clickXY(1340, 760);	//��������
			clickXY(250, 400);	//ִ������
			//�ȴ������ִ��  10:10�ֿ�ʼ  10:27����
			Thread.sleep(17*60*1000);
			clickXY(1120, 885);	//����һ��
			clickXY(250, 400);	//ִ������
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 1.��ȡ�����Json�ļ�������json�ַ����������Ͻ�JsonTools��
	 * @param fileName2
	 * @return
	 * @throws Exception 
	 */
	private static String getJsonString(String fileName2) throws Exception {
		File file = new File("E:\\Amarsoft\\My\\Game\\������.txt");
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
