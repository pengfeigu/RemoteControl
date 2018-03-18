package com.pibigstar.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendEmail {
	private String accout ;//�����˵��˺�
	private String password;//��Ȩ��
	private static String  stmp = "";//Э��
	private static int  port = 0;//�˿�

	/**
	 * @param accout �������˺�
	 * @param password ��Ȩ��
	 */
	public SendEmail(String accout, String password) {
		this.accout = accout;
		this.password = password;
		//���ݷ����˵��˺�ѡ����Ӧ��Э��
		if (accout.contains("@163.com")) {
			stmp = "smtp.163.com";
			port = 994;
		}else if (accout.contains("@qq.com")) {
			stmp = "smtp.qq.com";
			port = 465;
		}
	}
	/**
	 * @param receiver �ռ���
	 * @param title �ʼ�����
	 * @param content �ʼ����� ����ʹ��HTML��ǩ��
	 * @throws Exception 
	 */
	public void sendEmail(String receiver,String title,String content){
		for(int i=0;i<5;i++) {//����5�Σ���ֹ���ٲ��÷�����ȥ
			try {
				// ����Properties �����ڼ�¼�����һЩ����
				Properties props = new Properties();
				// ��ʾSMTP�����ʼ���������������֤
				props.put("mail.smtp.auth", "true");
				//�˴���дSMTP������
				props.put("mail.smtp.host", stmp);
				//�˿ںţ�QQ��������������˿� 587 �� 465
				props.put("mail.smtp.port", port);
				props.setProperty("mail.transport.protocol", "smtp");
				// �˴���д����˺�
				props.put("mail.user", accout);
				// �˴����������ǰ��˵��16λSTMP����
				props.put("mail.password", password);
				//���� SSL ���� ��163���������䲻��Ҫ SSL ���ܣ�
				MailSSLSocketFactory sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true);
				props.put("mail.smtp.ssl.enable", "true");
				props.put("mail.smtp.ssl.socketFactory", sf);

				// ������Ȩ��Ϣ�����ڽ���SMTP���������֤
				Authenticator authenticator = new Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {
						// �û���������
						String userName = props.getProperty("mail.user");
						String password = props.getProperty("mail.password");
						return new PasswordAuthentication(userName, password);
					}
				};
				// ʹ�û������Ժ���Ȩ��Ϣ�������ʼ��Ự
				Session mailSession = Session.getInstance(props, authenticator);
				// �����ʼ���Ϣ
				MimeMessage message = new MimeMessage(mailSession);
				// ���÷�����
				InternetAddress form = new InternetAddress(
						props.getProperty("mail.user"));

				message.setFrom(form);
				// �����ռ��˵�����
				InternetAddress to = new InternetAddress(receiver);
				message.setRecipient(RecipientType.TO, to);
				// �����ʼ�����
				message.setSubject(title);
				// �����ʼ���������
				message.setContent(content, "text/html;charset=UTF-8");
				// ����ʱ��
				message.setSentDate(new Date());
				// �����ʼ�
				Transport.send(message);
				System.out.println("���ͳɹ�");
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
