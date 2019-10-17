package demo.sound;

import java.io.File;
import java.io.FileOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
/**
 * java录制声音（采集声卡音频数据）
 * 采用java官方API——TargetDataLine，
 * 从声卡中采集音频数据达到录音效果，采集的数据为PCM裸流需要转为wav格式的话参照——PCM转WAV 。
 * 
 * 实际测试效果并不是很好，杂音很重
 * @author Administrator
 *
 */
public class DemoCore {
	private static FileOutputStream os;
	//采样率
	private static float RATE = 44100f;
	//编码格式PCM
	private static AudioFormat.Encoding ENCODING = AudioFormat.Encoding.PCM_SIGNED;
	//帧大小 16 
	private static int SAMPLE_SIZE = 16;
	//是否大端
	private static boolean BIG_ENDIAN = true;
	//通道数
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
		while((flag = targetDataLine.read(b, 0, b.length))>0) {//从声卡中采集数据
			os.write(b);
			System.out.println(flag);
		}
	}
}
