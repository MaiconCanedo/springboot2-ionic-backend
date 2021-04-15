package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.Cliente;
import com.nelioalves.cursomc.domain.repository.ClienteRepository;
import com.nelioalves.cursomc.core.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = repository.findByEmail(email);
        if (cliente == null) throw new UsernameNotFoundException(email);
        return new UserSS(cliente);
    }
}