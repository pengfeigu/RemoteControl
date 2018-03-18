package com.pibigstar.main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.pibigstar.ui.MyJFrame;

/**
 * �ͻ��ˣ����շ���˴��ݵ���Ϣ
 * @author pibigstar
 *
 */
public class Client extends Thread{
	
	private DataInputStream dis;
	private static Socket socket;
	private MyJFrame jFrame;
	
	public static void main(String[] args) {
		Client client = new Client();
		if (client.connectService()) {
			client.showUi();
			client.start();
		}
	}
	
	private boolean connectService(){
		do {
			//String link = "192.168.56.1:9090";
			String link = JOptionPane.showInputDialog("�������������Ͷ˿ں�");
			
			if (link.lastIndexOf(":")==-1) {
				JOptionPane.showMessageDialog(null,"�����źͶ˿�֮ǰ����:�ָ�");
				break;
			}
			String host = link.substring(0, link.lastIndexOf(":"));
			String port = link.substring(link.lastIndexOf(":")+1);
			try {
				 socket = new Socket(host,Integer.parseInt(port));
				 dis = new DataInputStream(socket.getInputStream());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,"δ֪�˿ں�");
				break;
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null,"δ֪������");
				break;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"�����쳣���Ӵ���");
				break;
			}
			return true;
		} while (false);
		return false;
	}
	
	private void showUi() {
		jFrame = new MyJFrame("java Զ�̼��");
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				int len = dis.readInt();
				byte[] data = new byte[len];
				dis.readFully(data);
				ImageIcon imageIcon = new ImageIcon(data);
				jFrame.changeImage(imageIcon);
			} catch (Exception e) {
				System.out.println("����������쳣");
				e.printStackTrace();
				break;
			}
		}
	}
}
