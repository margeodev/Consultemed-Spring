package com.consultemed.resource;

import java.util.List;

import javax.validation.Valid;

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

import com.consultemed.model.Paciente;
import com.consultemed.repository.filter.PacienteFilter;
import com.consultemed.service.PacienteService;

@RequestMapping("/api/pacientes")
@RestController
public class PacienteResource {

	@Autowired
	private PacienteService service;
	
//	########## GET ##########
	@GetMapping
	public List<Paciente> pesquisar(PacienteFilter filter) {
		return service.pesquisar(filter);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
		Paciente paciente = service.procurarPorId(id);		
		return ResponseEntity.ok(paciente);
	}
	
//	########## POST ##########
	@PostMapping
	public Paciente adicionar(@RequestBody Paciente paciente) {
		return service.salvar(paciente);
	}	
	
//	########## PUT ##########
	@PutMapping("/{id}")
	public ResponseEntity<Paciente> atualizarPaciente(@RequestBody @Valid Paciente paciente, @PathVariable Long id) {
		Paciente pacienteBanco = service.procurarPorId(id);	
		paciente.setId(id);
		pacienteBanco = service.salvar(paciente);
		return ResponseEntity.ok(pacienteBanco);
	}
	
//	########## DELETE ##########
	@DeleteMapping("/{id}")
	public ResponseEntity<Paciente> excluir(@PathVariable Long id) {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
}
