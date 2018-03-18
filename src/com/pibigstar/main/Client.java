package com.pibigstar.main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;

/**
 * �ͻ��ˣ����շ���˴��ݵ���Ϣ
 * @author pibigstar
 *
 */
public class Client extends Thread{
	
	private DataInputStream dis;
	private Socket socket;
	
	public static void main(String[] args) {
		
	}
	
	private boolean connectService(String host,int port) {
		try {
			socket = new Socket(host, port);
			//�õ�����˵�������
			dis = new DataInputStream(socket.getInputStream());
			
			return true;
		} catch (IOException e) {
			System.out.println("����"+host+"��������ʧ��");
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				int len = dis.readInt();
				byte[] data = new byte[len];
				dis.readFully(data);
				
				ImageIcon imageIcon = new ImageIcon(data);
				
			} catch (Exception e) {
				System.out.println("����������쳣");
				e.printStackTrace();
			}
			
		}
	}

}
