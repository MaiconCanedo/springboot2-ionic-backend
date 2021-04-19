package com.nelioalves.cursomc.core;

import com.nelioalves.cursomc.domain.service.DBService;
import com.nelioalves.cursomc.domain.service.EmailService;
import com.nelioalves.cursomc.domain.service.impl.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {


    @Autowired
    private DBService dbService;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public void instantiateDataBase() throws ParseException {
        if (strategy.equals("create"))
            dbService.instantiateTestDataBase();
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}