package com.autobots.app.types.interfaces;

import java.util.List;

public interface LinkInterface<T> {
    public void adicionarLink(List<T> lista);
	public void adicionarLink(T objeto);
}
