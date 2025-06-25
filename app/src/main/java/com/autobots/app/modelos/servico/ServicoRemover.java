package com.autobots.app.modelos.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Servico;
import com.autobots.app.repositorios.EmpresaRepositorio;
import com.autobots.app.repositorios.ServicoRepositorio;

import jakarta.transaction.Transactional;

@Component
public class ServicoRemover {
    @Autowired
    private ServicoRepositorio servicoRepositorio;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Transactional
    public void removerServico(Long id) {
        Servico servico = servicoRepositorio.findById(id).get();
        Empresa empresa = servico.getEmpresa();
        empresa.getServicos().removeIf(ser -> ser.getId().equals(id));
        empresaRepositorio.save(empresa);
    }
}
