package com.nelioalves.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage mailMessage) {
        LOGGER.info("Enviando email...");
        mailSender.send(mailMessage);
        LOGGER.info("Email enviado!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage message) {
        LOGGER.info("Enviando email...");
        javaMailSender.send(message);
        LOGGER.info("Email enviado!");
    }
}