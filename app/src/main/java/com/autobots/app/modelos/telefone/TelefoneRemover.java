package com.autobots.app.modelos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Telefone;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.repositorios.EmpresaRepositorio;
import com.autobots.app.repositorios.TelefoneRepositorio;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Component
public class TelefoneRemover {
    @Autowired
    private TelefoneRepositorio telefoneRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Transactional
    public void removerTelefone(Long id) {
        Telefone telefone = telefoneRepositorio.findById(id).get();
        if (telefone.getUsuario() != null) {
            Usuario usuario = telefone.getUsuario();
            usuario.getTelefones().removeIf(tel -> tel.getId().equals(id));
            usuarioRepositorio.save(usuario);
        } else {
            Empresa empresa = telefone.getEmpresa();
            empresa.getTelefones().removeIf(tel -> tel.getId().equals(id));
            empresaRepositorio.save(empresa);
        }
    }
}
