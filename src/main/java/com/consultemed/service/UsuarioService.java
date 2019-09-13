package com.consultemed.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultemed.model.Usuario;
import com.consultemed.repository.filter.UsuarioFilter;
import com.consultemed.repository.usuarios.UsuarioRepository;
import com.consultemed.service.exception.EmailJaCadastradoException;
import com.consultemed.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class UsuarioService {

	private static final String EMAIL_JA_CADASTRADO = "Email já cadastrado";
	
	@Autowired
	private UsuarioRepository usuarios;
	
	@Transactional
	public void salvar(Usuario usuario) {
		Optional<Usuario> usuarioOptional = usuarios.findByEmailIgnoreCase(usuario.getEmail());
		
		usuarioOptional.ifPresent(usuarioBanco -> {
			if(usuarioBanco != null && !usuarioBanco.equals(usuario))
				throw new EmailJaCadastradoException(EMAIL_JA_CADASTRADO);		    
		});			
		usuarios.save(usuario);		
	}
	
	public List<Usuario> pesquisar(UsuarioFilter filter) {
		return usuarios.buscarFiltrados(filter);
	}
	
	public Usuario procurarPorId(Long id) {
		return usuarios.getOne(id);
	}
			
	@Transactional
	public void excluir(Long id) {
		try {			
			this.usuarios.deleteById(id);
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir usuário.");
		}
	}
	
}

