package com.autobots.app.modelos.empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.repositorios.EmpresaRepositorio;
import com.autobots.app.types.dtos.EmpresaDTO;
import com.autobots.app.utils.mappers.EmpresaMapper;

import jakarta.transaction.Transactional;

@Component
public class EmpresaAtualizar {
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Transactional
    public Empresa atualizar(Empresa empresa, EmpresaDTO empresaDTO) {
        empresaMapper.updateEmpresa(empresaDTO, empresa);

        return empresaRepositorio.save(empresa);
    }
}
