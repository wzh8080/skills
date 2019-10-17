package test.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import sun.misc.BASE64Encoder;

public class TestAi {
	public static void main(String[] args) {
		TestAi t = new TestAi();
//		//ͼ��ʶ��
//		String clientId = "qjfnY6XcHdgje5lDLKW2mSoU";
//      String clientSecret = "TDsFFscEh89N7c39uYMVqsxBPgWy9XGM";
        //����ʶ��
        String clientId = "RMGSkRhPZVdS67XPmyXFM3pl";	// API Key
        String clientSecret = "G2pFTWiYMXx3LXwnRHWukHzoZrgZ05n7";	// Secret Key
		String token = AuthService.getAuth(clientId,clientSecret);
		System.out.println(token);
		String filePath ="C:/Users/King/Desktop/115.png";
//		t.testPhoto(token,filePath);
	}

	public String testPhoto(String token, String url, String filePath) {
		// ����url
//        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
//        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general"; //ͨ������ͳ���ʶ��߼���
//        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/object_detect"; //ͼ��������(�����)
//        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/logo"; //ͼ��������(�����)
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic"; //����ʶ��
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic"; //����ʶ��(�߾��Ȱ�)
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general"; //����ʶ��(�߾��Ȱ�)
        
        try {
            // �����ļ�·��
        	Thread.sleep(2*1000);
        	File file = new File(filePath);
        	FileInputStream  fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            //���ļ�����д���ַ����飺
            fis.read(bytes);
            fis.close();
            
            send(bytes,url,token);
          //���ֽ�����Base64����
            BASE64Encoder encoder = new BASE64Encoder();
            String imgStr = encoder.encode(bytes);//����Base64��������ֽ������ַ���
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            //String imgParam = imgStr;
            // ע�������Ϊ�˼򻯱���ÿһ������ȥ��ȡaccess_token�����ϻ���access_token�й���ʱ�䣬 �ͻ��˿����л��棬���ں����»�ȡ��
            //String accessToken = Token.getAuth();
            String accessToken = token;
            String param ="access_token=" + accessToken + "&image=" + imgParam;
            String result = sendPost(url, param);
            System.out.println(result);
            System.out.println("����");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("��ȡ����");
            return "ͼƬ��񲻷���";
        }
	}

	public void send(byte[] bytes, String url, String token) throws UnsupportedEncodingException {
		//���ֽ�����Base64����
        BASE64Encoder encoder = new BASE64Encoder();
        String imgStr = encoder.encode(bytes);//����Base64��������ֽ������ַ���
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");
        //String imgParam = imgStr;
        // ע�������Ϊ�˼򻯱���ÿһ������ȥ��ȡaccess_token�����ϻ���access_token�й���ʱ�䣬 �ͻ��˿����л��棬���ں����»�ȡ��
        //String accessToken = Token.getAuth();
        String accessToken = token;
        String param ="access_token=" + accessToken + "&image=" + imgParam;
        String result = sendPost(url, param);
        System.out.println(result);
	}

	private String sendPost(String url, String param) {
		 PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // �򿪺�URL֮�������
	            URLConnection conn = realUrl.openConnection();
	            // ����ͨ�õ���������
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // ����POST�������������������
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // ��ȡURLConnection�����Ӧ�������
	            out = new PrintWriter(conn.getOutputStream());
	            // �����������
	            out.print(param);
	            // flush������Ļ���
	            out.flush();
	            // ����BufferedReader����������ȡURL����Ӧ
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("���� POST ��������쳣��"+e);
	            e.printStackTrace();
	        }
	        //ʹ��finally�����ر��������������
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	}
}
