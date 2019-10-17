package test.io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
/**
 * ��ȡ�ļ�
 * br.readLine() ��ȡһ�к󣬵ڶ���ִ�л��ȡ��һ��
 * @author King
 *
 */
public class FileIOTest {
	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\King\\Desktop\\�����ʲ��ƽ�.txt");
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String line;
        StringBuffer result = new StringBuffer();
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            result.append(line);
        }
        System.out.println(result);
        br.close();
	}
}
