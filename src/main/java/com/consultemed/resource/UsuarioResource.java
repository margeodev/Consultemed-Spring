package com.consultemed.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultemed.model.Usuario;
import com.consultemed.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public List<Usuario> listar() {
		return service.listarTodos();
	}
	
	@PostMapping
	public Usuario adicionar(@RequestBody Usuario usuario) {
		return service.salvar(usuario);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
		
		Optional<Usuario> usuario = service.procurarPorId(id);
		
		if(!usuario.isPresent()) 
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(usuario.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateStudent(@RequestBody Usuario usuario, @PathVariable Long id) {

		Optional<Usuario> usuarioOptional = service.procurarPorId(id);

		if (!usuarioOptional.isPresent())
			return ResponseEntity.notFound().build();

		usuario.setId(id);
		Usuario usuarioSalvo = usuarioOptional.get();
		usuarioSalvo = service.salvar(usuario);

		return ResponseEntity.ok(usuarioSalvo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Usuario> excluir(@PathVariable Long id) {
		
		Optional<Usuario> usuarioOptional = service.procurarPorId(id);

		if (!usuarioOptional.isPresent())
			return ResponseEntity.notFound().build();
		
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
