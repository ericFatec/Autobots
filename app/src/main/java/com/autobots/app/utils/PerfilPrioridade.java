package com.autobots.app.utils;

import java.util.Comparator;
import java.util.Set;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.types.enums.TipoPerfil;

public class PerfilPrioridade {
    private PerfilPrioridade() { }

    public static TipoPerfil highestPerfil(Usuario usuario) {
        return highestPerfil(usuario.getPerfis());
    }

    public static TipoPerfil highestPerfil(Set<TipoPerfil> perfis) {
        return perfis.stream()
                .max(Comparator.comparingInt(PerfilPrioridade::priority))
                .orElseThrow(() -> new IllegalStateException("Usuário não possui perfis"));
    }

    private static int priority(TipoPerfil perfil) {
        return switch (perfil) {
            case ADMIN    -> 4;
            case GERENTE  -> 3;
            case VENDEDOR -> 2;
            case CLIENTE  -> 1;
        };
    }
}
