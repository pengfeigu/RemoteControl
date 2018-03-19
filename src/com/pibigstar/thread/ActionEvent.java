package com.pibigstar.thread;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * ִ���¼��߳�
 * @author pibigstar
 *
 */
public class ActionEvent extends Thread{

	private ObjectInputStream ois;

	public ActionEvent(ObjectInputStream ois) {
		this.ois = ois;
	}


	@Override
	public void run() {
		while (true) {
			try {
				Object readObject = ois.readObject();
				InputEvent inputEvent = (InputEvent) readObject;
				actionEvent(inputEvent);
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("�ͻ����˳�������");
				break;
			}
		}

	}

	/**
	 * ִ�о�����¼�
	 * @param inputEvent
	 */
	private void actionEvent(InputEvent inputEvent) {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		// ����¼�
		if (inputEvent instanceof MouseEvent) {
			MouseEvent e = (MouseEvent)inputEvent;
			int type = e.getID();
			if(type==MouseEvent.MOUSE_PRESSED){  //����
				robot.mousePress(2);
			}
			if(type==MouseEvent.MOUSE_RELEASED){  //�ſ�
				robot.mouseRelease(3);
			}
			if(type==MouseEvent.MOUSE_MOVED) { //�ƶ�
				robot.mouseMove(e.getX(), e.getY());
			}
			if(type==MouseEvent.MOUSE_DRAGGED) { //�϶�
				robot.mouseMove(e.getX(), e.getY());
			}
			if(type==MouseEvent.MOUSE_WHEEL) {   //���ֹ���
				robot.mouseWheel(getMouseClick(e.getButton()));
			}

		}
		// �����¼�
		if(inputEvent instanceof KeyEvent){
			KeyEvent e=(KeyEvent) inputEvent;
			if(e.getID()==KeyEvent.KEY_PRESSED){
				robot.keyPress(e.getKeyCode());
			}
			if(e.getID()==KeyEvent.KEY_RELEASED){
				robot.keyRelease(e.getKeyCode());
			}
		}

	}

	//���ݷ����µ�Mouse�¼�����ת��Ϊͨ�õ�Mouse��������
	private int getMouseClick(int button){
		if(button==MouseEvent.BUTTON1){
			return 2;
		} 
		if(button==MouseEvent.BUTTON2){
			return 3;
		} 
		if(button==MouseEvent.BUTTON3){
			return InputEvent.BUTTON3_MASK;
		}
		return -1;
	}

}
