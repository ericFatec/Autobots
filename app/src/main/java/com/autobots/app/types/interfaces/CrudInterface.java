package com.autobots.app.types.interfaces;

import java.util.List;

public interface CrudInterface<ReturnDTO, ID, DTOUpdate> {
    // ReturnDTO inserir(O object);
    ReturnDTO selecionar(ID id);
    List<ReturnDTO> selecionarTodos();
    ReturnDTO atualizar(ID id, DTOUpdate dtoUpdate);
    void deletar(ID id);
}
