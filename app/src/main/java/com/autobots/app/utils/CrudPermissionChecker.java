package com.autobots.app.utils;

import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.entidades.Vendas;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.dtos.UsuarioCadastroDTO;
import com.autobots.app.types.enums.TipoPerfil;

@Component
public class CrudPermissionChecker {
    public void checkUserAccess(Usuario alvo, RequesterDTO requester) {
        if (requester.getRole() == TipoPerfil.ADMIN) return;
        if (requester.getUsuario() == alvo) return;

        switch (requester.getRole()) {
            case GERENTE -> {
                boolean adminAlvo   = PerfilPrioridade.highestPerfil(alvo) == TipoPerfil.ADMIN;
                boolean empresaDiff = !alvo.getEmpresaId().equals(requester.getUsuario().getEmpresaId());
                if (adminAlvo || empresaDiff)
                    throw new AccessDeniedException("Gerente não pode acessar esse usuário");
            }
            case VENDEDOR -> {
                boolean naoCliente = PerfilPrioridade.highestPerfil(alvo) != TipoPerfil.CLIENTE;
                if (naoCliente)
                    throw new AccessDeniedException("Vendedor só pode acessar dados de clientes");
            }
            default -> throw new AccessDeniedException("Perfil sem permissão");
        }
    }

    public void canRegisterRole(UsuarioCadastroDTO novoUsuario, RequesterDTO requester) {
        if (requester.getRole() == TipoPerfil.ADMIN) return;
        TipoPerfil perfil = PerfilPrioridade.highestPerfil(novoUsuario.getPerfis());

        switch (requester.getRole()) {
            case GERENTE -> { if (perfil == TipoPerfil.ADMIN) throw new AccessDeniedException("Gerente não pode cadastrar um admnistrador."); }
            case VENDEDOR -> { if (perfil != TipoPerfil.CLIENTE) throw new AccessDeniedException("Vendedor só pode cadastrar clientes."); }
            default -> throw new AccessDeniedException("Perfil sem permissão");
        }
        
    }

    public void canRegisterVenda(Usuario usuarioVendedor, Usuario usuarioCliente, RequesterDTO requester) {
        if (requester.getRole() == TipoPerfil.ADMIN) return;
        if (!usuarioVendedor.getPerfis().contains(TipoPerfil.VENDEDOR)) throw new AccessDeniedException("Vendedor deve possuir um perfil de vendedor para ser parte de uma venda.");
        if (!usuarioCliente.getPerfis().contains(TipoPerfil.CLIENTE)) throw new AccessDeniedException("Cliente deve possuir um perfil de cliente para ser parte de uma venda.");
        switch (requester.getRole()) {
            case GERENTE -> { checkUserAccess(usuarioVendedor, requester); }
            case VENDEDOR -> { if (requester.getUsuario() != usuarioVendedor) throw new AccessDeniedException("Vendedor só pode cadastrar vendas no próprio nome."); }
            default -> throw new AccessDeniedException("Perfil sem permissão");
        }
    }

    public void canAccessVenda(Vendas venda, RequesterDTO requester) {
        if (requester.getRole() == TipoPerfil.ADMIN) return;

        boolean allowedOnSeller  = venda.getVendedor() != null && isAllowed(venda.getVendedor(), requester);
        boolean allowedOnBuyer   = venda.getCliente()  != null && isAllowed(venda.getCliente(),  requester);

        if (!allowedOnSeller && !allowedOnBuyer) {
            throw new AccessDeniedException("Usuário não tem permissão para acessar esta venda");
        }
    }

    public void isFromEmpresa(Empresa empresaAlvo, RequesterDTO requester) {
        if (requester.getRole() == TipoPerfil.ADMIN) return;
        if (!Objects.equals(empresaAlvo.getId(), requester.getUsuario().getEmpresaId()))
            throw new AccessDeniedException("Usuário não tem acesso a esta empresa");
    }

    private boolean isAllowed(Usuario alvo, RequesterDTO requester) {
        try {
            checkUserAccess(alvo, requester);
            return true;
        } catch (AccessDeniedException ex) {
            return false;
        }
    }
}
