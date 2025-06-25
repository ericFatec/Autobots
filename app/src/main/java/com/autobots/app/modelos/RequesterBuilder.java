package com.autobots.app.modelos;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.autobots.app.adapters.UserDetailsImpl;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.enums.TipoPerfil;

@Component
public class RequesterBuilder {
    public RequesterDTO build(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        TipoPerfil role = userDetails.getAuthorities().stream()
            .map(auth -> TipoPerfil.valueOf(auth.getAuthority().replace("ROLE_", "")))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Usuário sem perfil válido"));
        
        RequesterDTO requester = new RequesterDTO();
        requester.setRole(role);
        requester.setUsuario(userDetails.getUsuario());

        return requester;
    }
}
