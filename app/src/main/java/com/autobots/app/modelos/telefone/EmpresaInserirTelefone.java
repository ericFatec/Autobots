package com.autobots.app.modelos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Telefone;
import com.autobots.app.repositorios.TelefoneRepositorio;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.utils.mappers.TelefoneMapper;

import jakarta.transaction.Transactional;

@Component
public class EmpresaInserirTelefone {
    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Transactional
    public Telefone inserirTelefone(Empresa empresa, TelefoneDTO telefoneDTO) {
        Telefone telefone = telefoneMapper.toObject(telefoneDTO);
        telefone.setEmpresa(empresa);
        return telefoneRepositorio.save(telefone);
    }
}
