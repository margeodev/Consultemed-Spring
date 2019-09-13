package com.consultemed.repository.usuarios;

import java.util.List;

import com.consultemed.model.Usuario;
import com.consultemed.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {

	public List<Usuario> buscarFiltrados(UsuarioFilter filtro);
	
}
