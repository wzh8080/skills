package wow.fish;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import demo.sound.EngineeCore;
/**
 * 桌面程序，窗口启动
 * 开始后最小化至任务栏
 * 监听鼠标右键点击后，退出程序
 * @author Administrator
 *
 */
public class EngineeCoreTest {
	String filePath = "F:\\Ai\\test\\voice_cache.wav";
	private FishHandler fishHandler;
	public EngineeCoreTest(){
		this.fishHandler = new FishHandler();
	}
	
	AudioFormat audioFormat;
	TargetDataLine targetDataLine;
	boolean flag = true;


	private void stopRecognize() {
		flag = false;
		targetDataLine.stop();
		targetDataLine.close();
	}
	private AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		// 8000,11025,16000,22050,44100
		int sampleSizeInBits = 16;
		// 8,16
		int channels = 1;
		// 1,2
		boolean signed = true;
		// true,false
		boolean bigEndian = false;
		// true,false
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);//创建音频对象
	}// end getAudioFormat

	private void startRecognize() {
		String filePath = "F:\\Ai\\test\\voice_cache.wav";
		try {
			// 获得指定的音频格式
			audioFormat = getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
			flag = true;
			new CaptureThread().start();
		} catch (Exception e) {
			e.printStackTrace();
		} // end catch
	}

	class CaptureThread extends Thread {
		public void run() {
			File audioFile = new File(filePath);
			//声音录入的权值
			int weight = 2;
			//判断是否停止的计数
			int downSum = 0;

			AudioInputStream ais = null;
			ByteArrayInputStream bais = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				targetDataLine.open(audioFormat); //打开输入设备
				targetDataLine.start(); //开始录音
				byte[] fragment = new byte[1024];

				ais = new AudioInputStream(targetDataLine);
				System.out.println("start...");
				System.out.println();
				int i = 0;
				long start = System.currentTimeMillis();
				while(true) {
					targetDataLine.read(fragment, 0, fragment.length);
					baos.write(fragment);
					System.out.println("开始录入... " +(i++));
					System.out.println("首位："+fragment[0]+",末尾："+fragment[fragment.length-1]+",lenght"+fragment.length);
					System.out.println(Arrays.toString(fragment));
					System.out.println();
					long end = System.currentTimeMillis();
					if((end-start)>20000){
						break;
					}
				}
				
				//取得录音输入流
				audioFormat = getAudioFormat();
				byte audioData[] = baos.toByteArray();
				bais = new ByteArrayInputStream(audioData);
				ais = new AudioInputStream(bais, audioFormat, audioData.length / audioFormat.getFrameSize());
				//定义最终保存的文件名
				System.out.println("开始生成语音文件");
				AudioSystem.write(ais, AudioFileFormat.Type.WAVE, audioFile);
				downSum = 0;
				stopRecognize();

			stopRecognize();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//关闭流

				try {
					ais.close();
					bais.close();
					baos.reset();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("结束");
		}// end run
	}// end inner class CaptureThread
	
	//测试
	public static void main(String args[]) {
		EngineeCoreTest engineeCore = new EngineeCoreTest();
        engineeCore.startRecognize();
    }

}
