package demo.image.RGP;

import java.awt.Color;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

/**
 * https://blog.csdn.net/hayre/article/details/50610187
 * ���ʽ���:
 * ���Ͷ���ָɫ�ʵ����޳̶ȣ�Ҳ��ɫ�ʵĴ��ȡ�
 * �Ҷȣ�ʹ�ú�ɫ����ʾ���壬���ú�ɫΪ��׼ɫ����ͬ�ı��Ͷȵĺ�ɫ����ʾͼ��
 * ���أ���ͬ��Ӱ����Ƭһ��������Ӱ��Ҳ���������Ե�Ũ���׵�����������Ӱ��Ŵ�������
 * 	         �ᷢ����Щ����ɫ����ʵ�������ɫ�������С��������ɣ�
 * ��ЩС������ǹ���Ӱ�����С��Ԫ�������ء��Ƿֱ��ʵĳߴ絥λ��
 * �����ǻ���ԭɫ�ؼ���ҶȵĻ������롣���ǿ���������ͼƬ����һ����ά�����ؾ�����ɡ�
 * �����ڼ������ͨ����3���ֽ�24λ���棬
 * ��16-23 λ��ʾ��ɫ��R��������
 * 8-15 λ��ʾ��ɫ(G)������
 * 0-7 λ��ʾ��ɫ(B)������
 * ��ͼƬ�ߴ�������Ϊ��λʱ��ÿһ���׵���28���أ�����15*15���׳��ȵ�ͼƬ������420*420���صĳ��ȡ� 
 * һ���������ܱ��Ĳ�ͬ��ɫ��ȡ���ڱ���ÿ����(BPP)��
 * ��8bpp[2^8=256ɫ�� �Ҷ�ͼ��]��16bpp[2^16=65536ɫ����Ϊ�߲�ɫ]��24bpps[2^24=16777216ɫ����Ϊ���ɫ]��
 *  �ֱ��ʣ�ͼ�������صĶ��٣���Ϊͼ��ֱ��ʡ�
 *  RGB�� ��ɫģ�ͣ��ǽ���ɫ��ʾ��������ʽ��ģ�ͣ�����˵��һ�ּ�¼ͼ����ɫ�ķ�ʽ������ٶ�
 *  
 *  ���д����ǽ�һ��ͼƬ�ֽ��R,G,B����ɫ�ʻҶ�ͼƬ���㷨
 * Ҳ�ɲο�ԭ��Ϊ��http://blog.csdn.net/luoweifu/article/details/8042494
 */


public class MyImage {
	public static void main(String[] args) {
		String filePath = "C:\\Users\\Administrator\\Desktop\\test.png";
		String newFilePath = "C:\\Users\\Administrator\\Desktop\\test2.png";
		int type = 1;
		MyImage.analyseRGB(filePath, newFilePath, type);
	}
	// ��ͼƬ�ֽ�ΪR,G,B���ֻҶ�ͼƬ
	/**
	 * 
	 * @param filePath ԭͼƬ·��
	 * @param newFilePath ����Ҫ���ɵ�ͼƬ·��
	 * @param type ѡ����������1ΪR,2G,3ΪB
	 */
	
	public static void analyseRGB(String filePath, String newFilePath, int type) {
		OutputStream output = null;
		try {
			BufferedImage img = ImageIO.read(new File(filePath));
			int imageType = img.getType();// ��ȡͼƬ����
			int width = img.getWidth();// ��ȡͼƬ���
			int height = img.getHeight();// ��ȡͼƬ�߶�
			int startX = 0;// ��ʼ�ĺ�����
			int startY = 0;// ��ʼ��������
			int offset = 0;// ƫ����
			int scansize = width;// ɨ����
			int dd = width - startX;// �������Ŀ�ȼ��
			int hh = height - startY;// �������ĸ߶ȼ��
			int x0 = width / 2;// �����м��
			int y0 = height / 2;// �����м��
			System.out.println("dd:" + dd + " hh:" + hh);
			System.out.println("width:" + width + " height:" + height);
			System.out.println("imageType:" + imageType);
			System.out.println("size:"+(offset + hh * scansize + dd));
			// rgb�����飬�������أ���һά�����ʾ��λͼ����������
			int[] rgbArray = new int[offset + hh * scansize + dd];// ƫ����+����ʼ����*ɨ����+����ʼ����
			//�����Ҷ��о������Ϊʲô��������һ���㷨�أ�Ϊʲô��֪����width*height�͹����ˣ���������Ҳ�㲻������ֻҪĬ�ϼ�ס���������
			//Ȼ��ȡ���ʱ���������ȥȡ�Ϳ�����
			// newArray ���洦��������
			int[] newArray = new int[offset + hh * scansize + dd];// ƫ����+����ʼ����*ɨ����+����ʼ����
			/**
			 * getRGB public int[] getRGB(int startX, int startY, int w, int h,
			 * int[] rgbArray, int offset, int scansize)��ͼ�����ݵ�ĳһ���ַ���Ĭ�� RGB ��ɫģ��
			 * (TYPE_INT_ARGB) ��Ĭ�� sRGB ��ɫ�ռ��������������顣�����Ĭ��ģ�����ͼ��� ColorModel
			 * ��ƥ�䣬������ɫת������ʹ�ô˷��������ص������У�ÿ����ɫ����ֻ�� 8 λ���ȡ�ͨ��ͼ����ָ�������� (x, y)��ARGB
			 * ���ؿ��԰����·�ʽ���ʣ�
			 * 
			 * pixel = rgbArray[offset + (y-startY)*scansize + (x-startX)];
			 * ����������ڱ߽��ڲ������׳� ArrayOutOfBoundsException�����ǣ�����֤������ʽ�ı߽��顣
			 * 
			 * 
			 * ������
			 *  startX - ��ʼ X ���� 
			 *  startY - ��ʼ Y ���� 
			 *  w - ����Ŀ�� 
			 *  h - ����ĸ߶�
			 * rgbArray - �����Ϊ null�����ڴ�д�� rgb ���� 
			 * offset - rgbArray �е�ƫ����
			 * scansize - rgbArray ��ɨ���м�� 
			 * ���أ� RGB �������顣
			 */
			img.getRGB(startX, startY, width, height, rgbArray, offset,
					scansize); // �����ش浽�̶�����������ȥ
			int count=0;
			for(int i : rgbArray){
				System.out.println(i);
				if(i!=0){
					count=count+1;
				}
			}
			System.out.println(count);
			int rgb = rgbArray[offset + (y0 - startY) * scansize
					+ (x0 - startX)]; // λ��ͼƬ�������rgb���ص�
			Color c = new Color(rgb);
			System.out.println("�м����ص��rgb:"+c);
			for (int i = 0; i < height - startY; i++) {//����ÿһ��
				for (int j = 0; j < width - startX; j++) {//����ÿһ��
					c = new Color(rgbArray[offset+i * scansize + j]);
					switch (type) {
					case 1://��ɫ�Ҷ�ͼƬ
						newArray[i*dd + j] = new Color(c.getRed(), 0, 0).getRGB();
						break;
					case 2://��ɫ�Ҷ�ͼƬ
						 newArray[i*dd + j] = new Color(0, c.getGreen(), 0).getRGB(); 
						break;
					case 3://��ɫ�Ҷ�ͼƬ
						newArray[i * dd + j] = new Color(0, 0, c.getBlue()).getRGB();
						break;
 
					default:
						break;
					}
 
					
					
				}
			}
			// �½�һ��ͼ��
			File out = new File(newFilePath);
			if (!out.exists())
				out.createNewFile();
			output = new FileOutputStream(out);
			BufferedImage imgOut = new BufferedImage(width, height,
					BufferedImage.TYPE_3BYTE_BGR);
			imgOut.setRGB(startX, startY, width, height, newArray, offset,
					scansize);
			ImageIO.write(imgOut, "jpg", output);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}
