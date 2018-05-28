package net.liubaicai.android.informationlauncher.tools;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by mac on 2017/8/2.
 */
public class EmailEx {

    public void Send(String title, String body) {
        new SendEmailThread(new EmailContent(title, body)).start();
    }

    public class EmailContent {
        public String title;

        public EmailContent(String title, String body) {
            super();
            this.title = title;
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String body;
    }

    public class SendEmailThread extends Thread {
        private EmailContent content;

        public SendEmailThread(EmailContent content) {
            this.content = content;
        }

        @Override
        public void run() {
            MailSenderInfo mailInfo = new MailSenderInfo();
            mailInfo.setMailServerHost("smtp.mxhichina.com");
            mailInfo.setMailServerPort("25");
            mailInfo.setValidate(true);
            mailInfo.setUserName("forward@liubaicai.net");  //你的邮箱地址
            mailInfo.setPassword("######");//您的邮箱密码
            mailInfo.setFromAddress("forward@liubaicai.net");
            mailInfo.setToAddress("liushuai.baicai@foxmail.com");
            mailInfo.setSubject(content.title);
            mailInfo.setContent(content.body);

            //这个类主要来发送邮件
            SimpleMailSender sms = new SimpleMailSender();
            sms.sendTextMail(mailInfo);//发送文体格式
        }

    }

    public class MailSenderInfo {
        // 发送邮件的服务器的IP和端口
        private String mailServerHost;
        private String mailServerPort = "25";

        // 邮件发送者的地址
        private String fromAddress;
        // 邮件接收者的地址
        private String toAddress;
        // 登陆邮件发送服务器的用户名和密码
        private String userName;
        private String password;
        // 是否需要身份验证
        private boolean validate = true;
        // 邮件主题
        private String subject;
        // 邮件的文本内容
        private String content;
        // 邮件附件的文件名
        private String[] attachFileNames;

        /**
         * 获得邮件会话属性
         */
        public Properties getProperties() {
            Properties p = new Properties();
            p.put("mail.smtp.host", this.mailServerHost);
            p.put("mail.smtp.port", this.mailServerPort);
            p.put("mail.smtp.auth", validate ? "true" : "false");
            return p;
        }

        public String getMailServerHost() {
            return mailServerHost;
        }

        public void setMailServerHost(String mailServerHost) {
            this.mailServerHost = mailServerHost;
        }

        public String getMailServerPort() {
            return mailServerPort;
        }

        public void setMailServerPort(String mailServerPort) {
            this.mailServerPort = mailServerPort;
        }

        public boolean isValidate() {
            return validate;
        }

        public void setValidate(boolean validate) {
            this.validate = validate;
        }

        public String[] getAttachFileNames() {
            return attachFileNames;
        }

        public void setAttachFileNames(String[] fileNames) {
            this.attachFileNames = fileNames;
        }

        public String getFromAddress() {
            return fromAddress;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String textContent) {
            this.content = textContent;
        }

    }

    /**
     * 简单邮件（不带附件的邮件）发送器
     */
    public class SimpleMailSender {
        /**
         * 以文本格式发送邮件
         *
         * @param mailInfo 待发送的邮件的信息
         */
        public boolean sendTextMail(MailSenderInfo mailInfo) {
            // 判断是否需要身份认证
            MyAuthenticator authenticator = null;
            Properties pro = mailInfo.getProperties();
            if (mailInfo.isValidate()) {
                // 如果需要身份认证，则创建一个密码验证器
                authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
            }
            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
            try {
                // 根据session创建一个邮件消息
                Message mailMessage = new MimeMessage(sendMailSession);
                // 创建邮件发送者地址
                Address from = new InternetAddress(mailInfo.getFromAddress());
                // 设置邮件消息的发送者
                mailMessage.setFrom(from);
                // 创建邮件的接收者地址，并设置到邮件消息中
                Address to = new InternetAddress(mailInfo.getToAddress());
                mailMessage.setRecipient(Message.RecipientType.TO, to);
                // 设置邮件消息的主题
                mailMessage.setSubject(mailInfo.getSubject());
                // 设置邮件消息发送的时间
                mailMessage.setSentDate(new Date());
                // 设置邮件消息的主要内容
                String mailContent = mailInfo.getContent();
                mailMessage.setText(mailContent);
                // 发送邮件
                Transport.send(mailMessage);
                return true;
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
            return false;
        }

    }

    public class MyAuthenticator extends Authenticator {
        String userName = null;
        String password = null;

        public MyAuthenticator() {
        }

        public MyAuthenticator(String username, String password) {
            this.userName = username;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }
}
