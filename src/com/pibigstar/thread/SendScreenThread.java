package com.pibigstar.thread;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ����ͼͼƬ���͵��ͻ��ˣ����ƶˣ��߳�
 * @author pibigstar
 *
 */
public class SendScreenThread extends Thread{
	
	private DataOutputStream dos;
	private Toolkit toolkit;
	private Dimension dimension;
	private Rectangle rectangle;
	private Robot robot;
	
	public SendScreenThread(DataOutputStream dos) throws AWTException {
		this.dos = dos;
		toolkit = Toolkit.getDefaultToolkit();
		dimension = toolkit.getScreenSize();
		rectangle = new Rectangle(0, 0, (int)dimension.getWidth(), (int)dimension.getHeight());
		robot  = new Robot();
	}

	@Override
	public void run() {
		while (true) {
			byte[] data = getCapture();
			try {
				dos.writeInt(data.length);
				dos.write(data);
				dos.flush();
			} catch (IOException e) {
				break;
			}
		}
	}

	/**
	 * �õ���Ļ��ͼ����
	 * @return
	 */
	private byte[] getCapture() {
		
		BufferedImage bufferedImage =  robot.createScreenCapture(rectangle);

		//���һ���ڴ������
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		//��ͼƬ����д���ڴ�����
		try {
			ImageIO.write(bufferedImage, "jpg", baos);
		} catch (IOException e) {
			System.out.println("����ͼƬд���ڴ����г����쳣");
			e.printStackTrace();
		}
		
		return baos.toByteArray();
	}

}
