package com.autobots.app.types.interfaces;

import java.util.List;

public interface CrudInterfaceWithCadastro<ReturnDTO, ID, DTOCreate, DTOUpdate, DTORequester> {
    ReturnDTO inserir(DTOCreate dtoCreate, DTORequester requester);
    ReturnDTO selecionar(ID id, DTORequester requester);
    List<ReturnDTO> selecionarTodos();
    ReturnDTO atualizar(ID id, DTOUpdate dtoUpdate, DTORequester requester);
    void deletar(ID id, DTORequester requester);
}
