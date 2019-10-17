package demo.sound;

import java.io.FileInputStream;
import java.io.FileOutputStream;
/**
 * 将 PCM 音频文件 转成 WAV 文件
 * @author Administrator
 *
 */
public class PcmToWav {
	public static void main(String[] args) throws Exception {
		if(args.length<2) {
			args = new String[2];
			args[0] = "F:\\audio\\123.pcm";//输入路径
			args[1] = "F:\\audio\\b.wav";//输出路径
		}
		convertAudioFiles(args);
	}
	
	
	
	
	public static void convertAudioFiles(String[] src) throws Exception {
		   FileInputStream fis = new FileInputStream(src[0]);
 
		   //计算长度
		   byte[] buf = new byte[1024 * 4];
		   int size = fis.read(buf);
		   int PCMSize = 0;
		   while (size != -1) {
		      PCMSize += size;
		      size = fis.read(buf);
		    }
		   fis.close();
 
 
		   //填入参数，比特率等等。这里用的是16位单声道 8000 hz
		   WaveHeader header = new WaveHeader(PCMSize);
		   //长度字段 = 内容的大小（PCMSize) + 头部字段的大小(不包括前面4字节的标识符RIFF以及fileLength本身的4字节)
		   header.fileLength = PCMSize + (44 - 8);
		   header.FmtHdrLeth = 16;
		   header.BitsPerSample = 16;
		   header.Channels = 1;
		   header.FormatTag = 0x0001;
		   header.SamplesPerSec = 8000;
		   header.BlockAlign = (short)(header.Channels * header.BitsPerSample / 8);
		   header.AvgBytesPerSec = header.BlockAlign * header.SamplesPerSec;
		   header.DataHdrLeth = PCMSize;
 
		   byte[] h = header.getHeader();
 
		   assert h.length == 44; //WAV标准，头部应该是44字节
		   
//		   auline.write(h, 0, h.length);
		   
		   byte[] b = new byte[10];
		   
		   FileOutputStream fs = new FileOutputStream(src[1]);
		   fs.write(h);
		   FileInputStream fiss = new FileInputStream(src[0]);
		   byte[] bb = new byte[10];
		   int len = -1;
		   while((len = fiss.read(bb))>0) {
			   fs.write(bb, 0, len);
		   }
		   
		}
}
