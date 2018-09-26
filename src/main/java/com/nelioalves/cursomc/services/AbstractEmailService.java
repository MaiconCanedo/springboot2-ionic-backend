package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Pedido pedido) {
        SimpleMailMessage mailMessage = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(mailMessage);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(pedido.getCliente().getEmail());
        mailMessage.setFrom(sender);
        mailMessage.setSubject("Pedido Confirmado! Código: " + pedido.getId());
        mailMessage.setSentDate(new Date(System.currentTimeMillis()));
        mailMessage.setText(pedido.toString());
        return mailMessage;
    }
}
