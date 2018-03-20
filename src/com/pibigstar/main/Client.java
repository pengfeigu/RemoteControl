package com.pibigstar.main;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
	private ObjectOutputStream oos;
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
				oos = new ObjectOutputStream(socket.getOutputStream());
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

	/**
	 * ��ʾ���沢��Ӽ��
	 */
	private void showUi() {
		jFrame = new MyJFrame("java Զ�̼��");
	
		//������(ע�⣺�����JFrame�����������ã���֪��Ϊɶ���ӡ�������
		jFrame.panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				sendEvent(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				sendEvent(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sendEvent(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				sendEvent(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				sendEvent(e);
			}
		});
		// ������
		jFrame.panel.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				sendEvent(e);
			}
		});

		// ���̼���
		jFrame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				sendEvent(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				sendEvent(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				sendEvent(e);
			}
		});
	}

	/**
	 * ���¼����͵������ƶ�
	 * @param e
	 */
	private void sendEvent(InputEvent e) {
		try {
			oos.writeObject(e);
			oos.flush();
			System.out.println("�����¼���"+e);
		} catch (IOException e1) {
			System.out.println("�����¼�ʧ��");
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
				jFrame.changeImage(imageIcon);
			} catch (Exception e) {
				System.out.println("����������쳣");
				break;
			}
		}
	}
}
