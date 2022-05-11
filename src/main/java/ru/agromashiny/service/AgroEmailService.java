package ru.agromashiny.service;



import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import ru.agromashiny.models.Message;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;


@Service
public class AgroEmailService {
    @Autowired
    private JavaMailSenderImpl sender;
    public boolean send (Message message) throws MessagingException, IOException, GeneralSecurityException {

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        Properties properties = sender.getJavaMailProperties();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Обращение от: " + message.getName());
        simpleMailMessage.setText(message.getContent() + "\n" + message.getEmail());
        simpleMailMessage.setTo("rostislav.telitsin@gmail.com");
        simpleMailMessage.setFrom("robot@agromashiny.ru");

        try {
            sender.send(simpleMailMessage);
            return true;
        }
        catch (Exception e) {
            return false;
        }







    }

}