package iag1.com.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Email class.
 * <p/>
 * Created by dqromney on 9/26/15.
 */
public class Email {

    public static void sendEmail(String pTo, String pSubject, String pText) {
        final String username = "dqromney@gmail.com";
        final String password = "bogus";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
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
            message.setText(pText);

            System.out.println("Start email send ...");
            Transport.send(message);
            System.out.println("Completed email send.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
