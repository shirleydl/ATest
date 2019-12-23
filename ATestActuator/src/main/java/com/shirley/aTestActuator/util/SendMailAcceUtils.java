package com.shirley.aTestActuator.util;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.alibaba.druid.util.StringUtils;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 发送邮件工具类
 * 
 * @author tp
 *
 */
public class SendMailAcceUtils {

	/**
	 * 发送带附件的邮件
	 * 
	 * @param receive
	 *            收件人
	 * @param subject
	 *            邮件主题
	 * @param msg
	 *            邮件内容
	 * @param filename
	 *            附件地址
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static boolean sendMail(final String from, final String pass, String host, String receive, String subject,
			String msg, String filepath, String filename) {
		if (StringUtils.isEmpty(receive)) {
			return false;
		}
		// 获取系统属性
		Properties properties = System.getProperties();
		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");

		try {
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory", sf);
			// 获取默认session对象
			Session session = Session.getInstance(properties, null);
			// session = Session.getInstance(properties, new Authenticator() {
			// public PasswordAuthentication getPasswordAuthentication() { //
			// qq邮箱服务器账户、第三方登录授权码
			// return new PasswordAuthentication(from, pass); // 发件人邮件用户名、密码
			// }
			// });
			// 创建默认的 MimeMessage 对象
			MimeMessage message = new MimeMessage(session);

			// Set From: 头部头字段
			message.setFrom(new InternetAddress(from));

			// new InternetAddress();
			// Set To: 头部头字段
			Address[] internetAddressTo = InternetAddress.parse(receive);
			message.addRecipients(Message.RecipientType.TO, internetAddressTo);

			// Set Subject: 主题文字
			message.setSubject(subject);

			// 创建消息部分
			BodyPart messageBodyPart = new MimeBodyPart();

			// 消息
			messageBodyPart.setText(msg);

			// 创建多重消息
			Multipart multipart = new MimeMultipart();

			// 设置文本消息部分
			multipart.addBodyPart(messageBodyPart);

			// 附件部分
			messageBodyPart = new MimeBodyPart();
			// 设置要发送附件的文件路径
			DataSource source = new FileDataSource(filepath);
			messageBodyPart.setDataHandler(new DataHandler(source));

			// 处理附件名称中文（附带文件路径）乱码问题
			messageBodyPart.setFileName(MimeUtility.encodeText(filename));
			multipart.addBodyPart(messageBodyPart);

			// 发送完整消息
			message.setContent(multipart);

			// 发送消息
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);// 连接服务器的邮箱
			transport.sendMessage(message, message.getAllRecipients());// 把邮件发送出去
			transport.close();// 关闭连接
			// Transport.send(message);
			System.out.println("Sent message successfully....");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
