package test.io.file;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
/**
 * ��ȡ�ļ�
 * scanner.nextLine() ��ȡһ�к󣬵ڶ���ִ�л��ȡ��һ��
 * @author King
 *
 */
public class ScannerTest {
	public static void main(String[] args) throws IOException {
//		Scanner scanner = new Scanner(Paths.get("C:\\Users\\King\\Desktop\\�����ʲ��ƽ�.txt"),"UTF-8");
//		Scanner scanner = new Scanner("1\n2\n3\n4");
		Scanner scanner = new Scanner("1234"); 
		
        StringBuffer result = new StringBuffer();
        while (scanner.hasNextLine()) {
        	String str = scanner.nextLine();
        	System.out.println(str);
            result.append(str);
        }

        System.out.println("��"+result+"��");
	}
}
