package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email) {
        Cliente cliente = repository.findByEmail(email);
        if (cliente == null) throw new ObjectNotFoundException("E-mail não encontrado!");
        String newPass = newPassword();
        cliente.setSenha(passwordEncoder.encode(newPass));
        repository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) vet[i] = randomChar();
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        switch (opt) {
            case 0: //gerar um digito
                return (char) (random.nextInt(10) + 48);
            case 1: //gerar um letra maiúscula
                return (char) (random.nextInt(26) + 65);
            default: //gerar uma letra minúscula
                return (char) (random.nextInt(26) + 97);
        }
    }
}