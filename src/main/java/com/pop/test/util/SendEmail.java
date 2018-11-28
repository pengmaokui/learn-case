package com.pop.test.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail
{
   private String from;

   private String host;

   private String user;

   private String password;

   public SendEmail(String from, String host, String user, String password) {
      this.from = from;
      this.host = host;
      this.user = user;
      this.password = password;
   }

   public void send(String to, String subject, String text) {
      Properties props = new Properties();

      // 设置邮件服务器
      props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
      props.setProperty("mail.smtp.host", host);   // 发件人的邮箱的 SMTP 服务器地址
      props.setProperty("mail.smtp.auth", "true");            // 需要请求认证


      // 获取默认session对象
      Session session = Session.getDefaultInstance(props);

      try{
         // 创建默认的 MimeMessage 对象
         MimeMessage message = new MimeMessage(session);

         // Set From: 头部头字段
         message.setFrom(new InternetAddress(from));

         // Set To: 头部头字段
         message.addRecipient(Message.RecipientType.TO,
                 new InternetAddress(to));

         // Set Subject: 头部头字段
         message.setSubject(subject);

         // 设置消息体
         message.setText(text);
         // 发送消息
         Transport.send(message, user, password);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }

   public static void main(String[] args) {
      SendEmail sendEmail = new SendEmail("maokui.peng@yeepay.com", "smtp.yeepay.com",
              "maokui.peng@yeepay.com", "Lanmao65714320");
      sendEmail.send("maokui.peng@yeepay.com", "test", "test");
   }
}