package com.autobots.app.types.interfaces;

import java.util.List;

public interface CrudInterface<ReturnDTO, ID, DTOUpdate, DTORequester> {
    // ReturnDTO inserir(O object);
    ReturnDTO selecionar(ID id, DTORequester requester);
    List<ReturnDTO> selecionarTodos();
    ReturnDTO atualizar(ID id, DTOUpdate dtoUpdate, DTORequester requester);
    void deletar(ID id, DTORequester requester);
}
