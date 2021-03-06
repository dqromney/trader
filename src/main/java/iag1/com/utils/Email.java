package iag1.com.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * Email class.
 * <p/>
 * Created by dqromney on 9/26/15.
 */
public class Email {

    private ReadConfig readConfig;

    public Email() {
        readConfig = new ReadConfig("config.properties");
    }

    public void sendEmail(String pTo, String pSubject, String pText) throws IOException {
        Properties configProps = readConfig.getPropValues();
        final String username = configProps.getProperty("gmail.login");
        final String password = configProps.getProperty("gmail.password");

        Properties props = new Properties();
        props.put("mail.smtp.auth", configProps.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", configProps.getProperty("mail.smtp.starttls.enable"));
        props.put("mail.smtp.host", configProps.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", configProps.getProperty("mail.smtp.port"));
        // SSL port
        // props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dqromney@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pTo));
            message.setSubject(pSubject);
            message.setContent(pText, "text/html; charset=utf-8");

            System.out.println("Start email send ...");
            Transport.send(message);
            System.out.println("Completed email send.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
