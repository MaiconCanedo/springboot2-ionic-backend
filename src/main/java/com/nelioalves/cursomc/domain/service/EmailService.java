package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.Cliente;
import com.nelioalves.cursomc.domain.entity.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage mailMessage);

    void sendOrderConfirmationHtmlEmail(Pedido pedido);

    void sendHtmlEmail(MimeMessage message);

    void sendNewPasswordEmail(Cliente cliente, String newPass);
}