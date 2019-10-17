package wow.fish;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
/**
 * 桌面程序，窗口启动
 * 开始后最小化至任务栏
 * 监听鼠标右键点击后，退出程序
 * @author Administrator
 *
 */
public class AutoFishCore {
	
	private FishHandler fishHandler;
	public AutoFishCore(){
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
			//声音录入的权值
			int weight = 2;
			//判断是否停止的计数
			int downSum = 0;

			AudioInputStream ais = null;
			try {
				targetDataLine.open(audioFormat); //打开输入设备
				targetDataLine.start(); //开始录音
				byte[] fragment = new byte[1024];

				ais = new AudioInputStream(targetDataLine);
				System.out.println("start...");
				System.out.println();
				while (flag) {

					targetDataLine.read(fragment, 0, fragment.length);
					//当数组末位大于weight时开始存储字节（有声音传入），一旦开始不再需要判断末位
					if (Math.abs(fragment[fragment.length-1]) > weight ) {
						downSum++;
						//点击收杆 并防止多次点击
						System.out.println("声音录入...点击 第 "+downSum+" 次");
						System.out.println("首位："+fragment[0]+",末位："+fragment[fragment.length-1]+",lenght"+fragment.length);
						System.out.println();
						//放杆 并等待1秒
						Thread.sleep(1500);
						//判断鼠标右键被按下时 结束循环
						if(downSum == 3){
							break;
						}
					}
				}


			stopRecognize();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//关闭流

				try {
					ais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("任务结束");
		}// end run
	}// end inner class CaptureThread
	
	//测试
	public static void main(String args[]) {
		AutoFishCore autoFish = new AutoFishCore();
		autoFish.startRecognize();
    }

}
