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
import com.consultemed.service.exception.ImpossivelExcluirEntidadeException;
import com.consultemed.service.exception.JaCadastradoException;
import com.consultemed.service.exception.RecursoNaoEncontradoException;

@Service
public class UsuarioService {

	private static final String USUARIO_JA_CADASTRADO = "Já existe um usuário cadastrado com esse Email";
	private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
	private static final String IMPOSSIVEL_EXCLUIR = "Não é possível excluir o usuário";
	
	@Autowired
	private UsuarioRepository usuarios;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		verificaSeJaExisteUsuarioComEmail(usuario);				
		return usuarios.save(usuario);		
	}
	
	public List<Usuario> pesquisar(UsuarioFilter filter) {
		return usuarios.buscarFiltrados(filter);
	}
	
	public Usuario procurarPorId(Long id) {
		Usuario usuario = verificaSeUsuarioExiste(id);
		return usuario;
	}

	@Transactional
	public void excluir(Long id) {
		verificaSeUsuarioExiste(id);
		try {			
			this.usuarios.deleteById(id);
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException(IMPOSSIVEL_EXCLUIR);
		}
	}
	
	
	private Usuario verificaSeUsuarioExiste(Long id) {
		Optional<Usuario> usuario = usuarios.findById(id);		
		if(!usuario.isPresent()) 
			throw new RecursoNaoEncontradoException(USUARIO_NAO_ENCONTRADO);
		return usuario.get();
	}
	
	private void verificaSeJaExisteUsuarioComEmail(Usuario usuario) {
		Optional<Usuario> usuarioOptional = usuarios.findByEmailIgnoreCase(usuario.getEmail());		
		usuarioOptional.ifPresent(usuarioBanco -> {
			if(!usuarioBanco.equals(usuario))
				throw new JaCadastradoException(USUARIO_JA_CADASTRADO);
		});
	}
}

