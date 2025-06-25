package com.autobots.app.modelos.empresa;

import java.time.Instant;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.modelos.telefone.EmpresaInserirTelefone;
import com.autobots.app.repositorios.EmpresaRepositorio;
import com.autobots.app.types.dtos.EmpresaCadastroDTO;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.utils.mappers.EmpresaMapper;

import jakarta.transaction.Transactional;

@Component
public class EmpresaInsercao {
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private EmpresaInserirTelefone empresaInserirTelefone;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Transactional
    public Empresa inserir(EmpresaCadastroDTO empresaCadastroDTO) {
        Set<TelefoneDTO> telefones = empresaCadastroDTO.getTelefones();
        
        Empresa empresa = empresaMapper.toObject(empresaCadastroDTO);
        empresa.setDataCadastro(Instant.now());

        Empresa empresaNova = empresaRepositorio.save(empresa);

        if (telefones != null && !telefones.isEmpty()) {
            for (TelefoneDTO tel : telefones) {
                empresaInserirTelefone.inserirTelefone(empresaNova, tel);
            }

            Empresa empresaCompleta = empresaRepositorio.findById(empresaNova.getId()).get();
            return empresaCompleta;
        }

        return empresaNova;
    }
}
