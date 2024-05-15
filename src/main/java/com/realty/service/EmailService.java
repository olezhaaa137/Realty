package com.realty.service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;


public class EmailService {
    private static final EmailService INSTANCE = new EmailService();

    public static EmailService getInstance() {
        return INSTANCE ;
    }

    private EmailService() {
    }

    void doSendEmail(String to, String subject, String text) {

        System.out.println("TLSEmail Start");

        final String from = "kursach06@list.ru";
        final String password = "x7qMvqQxNvYxYpXD701y";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.list.ru"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException ignore) {
        }

    }
}
