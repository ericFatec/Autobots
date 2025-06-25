package com.autobots.app.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autobots.app.modelos.credencial.UsuarioCredencialLogin;
import com.autobots.app.types.dtos.CredencialDTO;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioCredencialLogin usuarioCredencialLogin;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioCredencialLogin.loadUser(username);
    }

    @Transactional
    public UserDetails loadByCredencial(CredencialDTO credencialDTO) {
        return usuarioCredencialLogin.login(credencialDTO);
    }
}
