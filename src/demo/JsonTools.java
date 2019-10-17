package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class JsonTools {
	/**
	 * 1.读取 Json文件，生成json字符串
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
