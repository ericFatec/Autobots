package com.autobots.app.types.interfaces;

import java.util.List;

public interface CrudInterfaceWithCadastro<ReturnDTO, ID, DTOCreate, DTOUpdate> {
    ReturnDTO inserir(DTOCreate dtoCreate);
    ReturnDTO selecionar(ID id);
    List<ReturnDTO> selecionarTodos();
    ReturnDTO atualizar(ID id, DTOUpdate dtoUpdate);
    void deletar(ID id);
}
