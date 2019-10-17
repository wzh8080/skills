package test.io.file;
/**
 * Math.round() 四舍五入
 * 取一位小数
 * @author King
 *
 */
public class MathTest {
	public static void main(String[] args) {
		int a = 10;
		int b = 3;
		double n1 = Math.round(a*10/b)/10;  //33/10会先得到int型的3，然后强转成double 3.0
		double n2 = Math.round(a*10/b)/10.0;
		double m = Math.round(a*10/b);
		System.out.println(n1);	//3.0
		System.out.println(n2); //3.3
		System.out.println(m);	//33.0
	}
}
