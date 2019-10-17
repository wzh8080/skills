package demo.sound;

import java.io.File;
import java.io.FileOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
/**
 * java¼���������ɼ�������Ƶ���ݣ�
 * ����java�ٷ�API����TargetDataLine��
 * �������вɼ���Ƶ���ݴﵽ¼��Ч�����ɼ�������ΪPCM������ҪתΪwav��ʽ�Ļ����ա���PCMתWAV ��
 * 
 * ʵ�ʲ���Ч�������Ǻܺã���������
 * @author Administrator
 *
 */
public class DemoCore {
	private static FileOutputStream os;
	//������
	private static float RATE = 44100f;
	//�����ʽPCM
	private static AudioFormat.Encoding ENCODING = AudioFormat.Encoding.PCM_SIGNED;
	//֡��С 16 
	private static int SAMPLE_SIZE = 16;
	//�Ƿ���
	private static boolean BIG_ENDIAN = true;
	//ͨ����
	private static int CHANNELS = 2;
 
	public static void main(String[] args) throws Exception{
		if(args.length<1) {
			save("F:/audio/123.pcm");
		}else {
			save(args[0]);
		}
	}
 
	public static void save(String path) throws Exception {
		File file = new File(path);
		
		if(file.isDirectory()) {
			if(!file.exists()) {
				file.mkdirs();
			}
			file.createNewFile();
		}
		
		AudioFormat audioFormat = new AudioFormat(ENCODING,RATE, SAMPLE_SIZE, CHANNELS, (SAMPLE_SIZE / 8) * CHANNELS,
				RATE, BIG_ENDIAN);
		TargetDataLine targetDataLine = AudioSystem.getTargetDataLine(audioFormat);
		targetDataLine.open();
		targetDataLine.start();
		byte[] b = new byte[256];
		int flag = 0;
		os = new FileOutputStream(file);
		while((flag = targetDataLine.read(b, 0, b.length))>0) {//�������вɼ�����
			os.write(b);
			System.out.println(flag);
		}
	}
}
