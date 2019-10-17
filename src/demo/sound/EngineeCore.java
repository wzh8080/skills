package demo.sound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;


/**
 * java实现麦克风自动录音
 * 保存音频文件，并且实现当有声音传入时自动生成音频文件。
 * 
 * 当有较高的声音传入麦克风时，targetDataLine读取的字节数组首位或末位绝对值会变大
 * （位置取决于音频格式中的一些参数，如bigEndian）。传入音量低，绝对值会变小
 * 
 * 录音开始。从targetDataLine中读取的音频数据被保存在ByteArrayOutputStream中。
 * 一段时间音量一直低于权值时，认为无声音传入，结束录音。从ByteArrayOutputStream取出字节数组，
 * 转为音频保存在本地文件中。
 * 
 * 注意：从targetDataLine读取的字节数组不能直接用于百度等语音识别，需要先转为音频文件，
 * 然后读取音频文件生成的字节数组，才可用于语音识别。
 * 
 * 文件wav都是杂音:麦克风是不是有问题，权值可以调高一点
 * 
 * 使用百度大的语音识别的时候要注意simpleRate参数与调用时给百度设置的Rate参数要一致，否则会出现3301错误
 * @author Administrator
 *
 */
public class EngineeCore {
	String filePath = "F:\\Ai\\test\\voice_cache.wav";

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
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}// end getAudioFormat


	private void startRecognize() {
		try {
			// 获得指定的音频格式
			audioFormat = getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);

			// Create a thread to capture the microphone
			// data into an audio file and start the
			// thread running. It will run until the
			// Stop button is clicked. This method
			// will return after starting the thread.
			flag = true;
			new CaptureThread().start();
		} catch (Exception e) {
			e.printStackTrace();
		} // end catch
	}// end captureAudio method

	class CaptureThread extends Thread {
		public void run() {
			AudioFileFormat.Type fileType = null;
			File audioFile = new File(filePath);

			fileType = AudioFileFormat.Type.WAVE;
			//声音录入的权值
			int weight = 4;
			//判断是否停止的计数
			int downSum = 0;

			ByteArrayInputStream bais = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			AudioInputStream ais = null;
			try {
				targetDataLine.open(audioFormat);
				targetDataLine.start();
				byte[] fragment = new byte[1024];

				ais = new AudioInputStream(targetDataLine);
				System.out.println("start...");
				while (flag) {

					targetDataLine.read(fragment, 0, fragment.length);
					//当数组末位大于weight时开始存储字节（有声音传入），一旦开始不再需要判断末位
					if (Math.abs(fragment[fragment.length-1]) > weight || baos.size() > 0) {
						System.out.println("开始录入...");
						baos.write(fragment);
						System.out.println("首位："+fragment[0]+",末尾："+fragment[fragment.length-1]+",lenght"+fragment.length);
						//判断语音是否停止
						if(Math.abs(fragment[fragment.length-1])<=weight){
							downSum++;
						}else{
							System.out.println("重置奇数");
							downSum=0;
						}
						//计数超过20说明此段时间没有声音传入(值也可更改)
						if(downSum>20){
							System.out.println("停止录入");
							break;
						}

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
        EngineeCore engineeCore = new EngineeCore();
            engineeCore.startRecognize();
    }
}