package fr.univtln.dosso_boudfor.mini_projet_d35.emails_senders;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by yssouf on 31/12/15.
 **/


public abstract class MailSender {

    protected String host;
    protected String username;
    protected String password;
    protected String from;
    protected Session session;

    public MailSender() {
    }

    protected void init() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
//        props.put("mail.smtp.port", "465");

        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public boolean sendMessage(String to, String mssgTitle, String mssgText) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            //Message title
            message.setSubject(mssgTitle);
            // Now set the actual message
            message.setText(mssgText);
            // Send message
            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            return false;
        }
    }

}
