package demo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MouseListenerDemo2 extends JFrame {
private static final long serialVersionUID = 1L; 
	
	public MouseListenerDemo2() {
		setTitle("Hern");
		setBounds(400, 400, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JLabel label = new JLabel();
		label.setText("请用鼠标点击");
		label.addMouseListener(new MouseListener() {
			long end = 0;
			long start = 0;
			@Override
			public void mouseReleased(MouseEvent e) {//鼠标按键被释放时被触发
				// TODO Auto-generated method stub
				end = System.currentTimeMillis();
				System.out.println("鼠标按键被释放，耗时 "+(end-start));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {//鼠标按键被按下时被触发
				// TODO Auto-generated method stub
				start = System.currentTimeMillis();
				System.out.println("鼠标按键被按下，时间 ");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {//光标移出组件时被触发
				// TODO Auto-generated method stub
				System.out.println("光标移除组件");
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {//光标移入组件时被触发
				// TODO Auto-generated method stub
				System.out.println("光标移入组件");
 
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {//发生单击事件时被触发
				// TODO Auto-generated method stub
				System.out.print("单击了鼠标按键，");
				int i = e.getButton(); // 通过该值可以判断单击的是哪个键
				if (i == MouseEvent.BUTTON1) {
					System.out.print("单击的是鼠标左键，");
				}else if (i == MouseEvent.BUTTON2) {
					System.out.print("单击的是鼠标滚轮，");
				}else if (i == MouseEvent.BUTTON3) {
					System.out.print("单击的是鼠标右键，");
				}
				int clickCount = e.getClickCount();//获取单击按键的次数
				System.out.println("单击次数为" + clickCount + "下");
			}
		});
		
		
		add(label);
		setVisible(true);
	}
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MouseListenerDemo2 test = new MouseListenerDemo2();
 
	}

}
