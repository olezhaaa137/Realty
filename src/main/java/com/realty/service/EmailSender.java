package com.realty.service;

public class EmailSender {
    private static final EmailSender INSTANCE = new EmailSender();

    public static EmailSender getInstance() {
        return INSTANCE;
    }

    private EmailSender() {
    }

    public void sendEmail(String to, String subject, String text) {
        EmailService.getInstance().doSendEmail(to, subject, text);
    }
}