package test.io.file;
/**
 * Math.round() ��������
 * ȡһλС��
 * @author King
 *
 */
public class MathTest {
	public static void main(String[] args) {
		int a = 10;
		int b = 3;
		double n1 = Math.round(a*10/b)/10;  //33/10���ȵõ�int�͵�3��Ȼ��ǿת��double 3.0
		double n2 = Math.round(a*10/b)/10.0;
		double m = Math.round(a*10/b);
		System.out.println(n1);	//3.0
		System.out.println(n2); //3.3
		System.out.println(m);	//33.0
	}
}
