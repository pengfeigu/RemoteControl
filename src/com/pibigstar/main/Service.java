package com.pibigstar.main;

import java.awt.AWTException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.pibigstar.thread.SendScreenThread;

/**
 * ����ˣ��ȴ����ƶ˵Ľ���
 * @author pibigstar
 *
 */
public class Service {

	private  DataOutputStream dos;
	private  ObjectOutputStream oos;
	private final int port = 9090; 
	public static void main(String[] args) throws IOException {
		new Service().startServer();

	}

	private void startServer(){
		try {
			ServerSocket serverSocket = new ServerSocket(port);
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
