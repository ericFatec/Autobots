package com.autobots.app.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.autobots.app.modelos.credencial.UsuarioCredencialLogin;
import com.autobots.app.types.dtos.CredencialDTO;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsuarioCredencialLogin loginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CredencialDTO dto = (CredencialDTO) authentication.getPrincipal();
        UserDetailsImpl userDetails = loginService.login(dto);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
