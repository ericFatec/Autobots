package com.autobots.app.adapters;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.autobots.app.entidades.Credencial;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.types.enums.TipoPerfil;
import com.autobots.app.utils.PerfilPrioridade;

public class UserDetailsImpl implements UserDetails{
    private final Usuario usuario;
    private final Credencial credencial;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Usuario usuario, Credencial credencial) {
        this.usuario   = usuario;
        this.credencial = credencial;

        // garante que os perfis já estão carregados
        usuario.getPerfis().size();

        TipoPerfil top = PerfilPrioridade.highestPerfil(usuario);
        this.authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + top));
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getUsername() { return credencial.getLoginHandle(); }
    @Override public String getPassword() { return credencial.getPassword(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return !credencial.isInativo(); }

    public Usuario getUsuario() {
        return usuario;
    }

    public Credencial getCredencial() {
        return credencial;
    }
}
