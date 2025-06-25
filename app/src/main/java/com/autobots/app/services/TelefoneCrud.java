package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Telefone;
import com.autobots.app.modelos.telefone.TelefoneAtualizar;
import com.autobots.app.repositorios.telefone.TelefoneRepositorio;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.types.interfaces.CrudInterface;

@Service
public class TelefoneCrud implements CrudInterface<Telefone, Long, TelefoneDTO>{
    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    @Autowired
    private TelefoneAtualizar telefoneAtualizar;

    public Telefone inserir(Telefone telefone) {
        return telefoneRepositorio.save(telefone);
    }

    public Telefone selecionar(Long id) {
        return telefoneRepositorio.findById(id).get();
    }

    public List<Telefone> selecionarTodos() {
        return telefoneRepositorio.findAll();
    }

    public Telefone atualizar(Long id, TelefoneDTO novosDados) {
        Telefone telefoneAlvo = telefoneRepositorio.findById(id).get();
        return telefoneAtualizar.atualizar(telefoneAlvo, novosDados);
    }

    public void deletar(Long id) {
        telefoneRepositorio.deleteById(id);
    }
}
