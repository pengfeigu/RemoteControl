package com.pibigstar.thread;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
		try {
			Robot robot = new Robot();
			while (true) {
				Object readObject = ois.readObject();
				InputEvent inputEvent = (InputEvent) readObject;
				actionEvent(robot,inputEvent);
			}
		} catch (Exception e) {
			System.out.println("�ͻ����˳�������");
			return;
		}
	}


	/**
	 * ִ�о�����¼�
	 * @param inputEvent
	 */
	private void actionEvent(Robot robot,InputEvent inputEvent) {
		int mousebuttonmask = -100; //��갴�� 
		// ����¼�
		if (inputEvent instanceof MouseEvent) {
			MouseEvent e = (MouseEvent)inputEvent;
			int type = e.getID();
			if(type==MouseEvent.MOUSE_PRESSED){  //����
				robot.mouseMove( e.getX() , e.getY() );  
				mousebuttonmask = getMouseClick(e.getButton() );
				if(mousebuttonmask != -100)
					robot.mousePress(mousebuttonmask);
			}
			if(type==MouseEvent.MOUSE_RELEASED){  //�ſ�
				robot.mouseMove( e.getX() , e.getY() );  
				mousebuttonmask = getMouseClick( e.getButton() );//ȡ����갴��  
				if(mousebuttonmask != -100)  
					robot.mouseRelease( mousebuttonmask ); 
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
	private static int getMouseClick(int button) { 
		//ȡ����갴�� 
		if (button == MouseEvent.BUTTON1) //��� ,�м��ΪBUTTON2  
			return InputEvent.BUTTON1_MASK; 
		if (button == MouseEvent.BUTTON3) //�Ҽ�  
			return InputEvent.BUTTON3_MASK; 
		return -100; 

	} 
}
