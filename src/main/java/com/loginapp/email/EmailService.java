package com.loginapp.email;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    @Override
    @Async
    public void send(String to, String email) {
        String from = "loginregisration@example.com";
        final String username = "";//put your mailtrap username here
        final String password = "";//put your mailtrap password here

        String host = "smtp.mailtrap.io";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "2525");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject("Daniel Enterprise");//set email subject field
            String htmlContent = email;
            message.setContent(htmlContent, "text/html");//set the content of the email message

            Transport.send(message);

            System.out.println("Email Message Sent Successfully");

        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email");
        }
    }
}
