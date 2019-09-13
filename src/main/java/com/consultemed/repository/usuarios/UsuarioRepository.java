package com.consultemed.repository.usuarios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultemed.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {
	
	public Optional<Usuario> findByEmailIgnoreCase(String email);
	
}