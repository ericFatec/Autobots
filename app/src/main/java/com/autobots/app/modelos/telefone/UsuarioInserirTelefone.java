package com.autobots.app.modelos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.repositorios.TelefoneRepositorio;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.utils.mappers.TelefoneMapper;
import com.autobots.app.entidades.Telefone;

import jakarta.transaction.Transactional;

@Component
public class UsuarioInserirTelefone {

    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Transactional
    public Telefone inserirTelefone(Usuario usuario, TelefoneDTO telefoneDTO) {
        Telefone telefone = telefoneMapper.toObject(telefoneDTO);
        telefone.setUsuario(usuario);
        return telefoneRepositorio.save(telefone);
    }
}
