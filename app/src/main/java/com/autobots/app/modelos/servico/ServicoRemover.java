package com.autobots.app.modelos.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Servico;
import com.autobots.app.repositorios.EmpresaRepositorio;

import jakarta.transaction.Transactional;

@Component
public class ServicoRemover {
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Transactional
    public void removerServico(Servico servico) {
        Empresa empresa = servico.getEmpresa();
        empresa.getServicos().removeIf(ser -> ser.getId().equals(servico.getId()));
        empresaRepositorio.save(empresa);
    }
}
