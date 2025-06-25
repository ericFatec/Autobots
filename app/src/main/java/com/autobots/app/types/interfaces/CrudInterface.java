package com.autobots.app.types.interfaces;

import java.util.List;

public interface CrudInterface<T, ID, DTO> {
    T inserir(T entity);
    T selecionar(ID id);
    List<T> selecionarTodos();
    T atualizar(ID id, DTO entity);
    void deletar(ID id);
}
