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
//		//图像识别
//		String clientId = "qjfnY6XcHdgje5lDLKW2mSoU";
//      String clientSecret = "TDsFFscEh89N7c39uYMVqsxBPgWy9XGM";
        //文字识别
        String clientId = "RMGSkRhPZVdS67XPmyXFM3pl";	// API Key
        String clientSecret = "G2pFTWiYMXx3LXwnRHWukHzoZrgZ05n7";	// Secret Key
		String token = AuthService.getAuth(clientId,clientSecret);
		System.out.println(token);
		String filePath ="C:/Users/King/Desktop/115.png";
//		t.testPhoto(token,filePath);
	}

	public String testPhoto(String token, String url, String filePath) {
		// 请求url
//        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
//        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general"; //通用物体和场景识别高级版
//        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/object_detect"; //图像主体检测(长宽高)
//        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/logo"; //图像主体检测(长宽高)
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic"; //文字识别
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic"; //文字识别(高精度版)
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general"; //文字识别(高精度版)
        
        try {
            // 本地文件路径
        	Thread.sleep(2*1000);
        	File file = new File(filePath);
        	FileInputStream  fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            //将文件内容写进字符数组：
            fis.read(bytes);
            fis.close();
            
            send(bytes,url,token);
          //对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            String imgStr = encoder.encode(bytes);//返回Base64编码过的字节数组字符串
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            //String imgParam = imgStr;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            //String accessToken = Token.getAuth();
            String accessToken = token;
            String param ="access_token=" + accessToken + "&image=" + imgParam;
            String result = sendPost(url, param);
            System.out.println(result);
            System.out.println("方法");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取不到");
            return "图片规格不符合";
        }
	}

	public void send(byte[] bytes, String url, String token) throws UnsupportedEncodingException {
		//对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String imgStr = encoder.encode(bytes);//返回Base64编码过的字节数组字符串
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");
        //String imgParam = imgStr;
        // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
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
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
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
