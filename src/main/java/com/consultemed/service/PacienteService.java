package com.consultemed.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consultemed.model.Paciente;
import com.consultemed.repository.filter.PacienteFilter;
import com.consultemed.repository.pacientes.PacienteRepository;
import com.consultemed.service.exception.ImpossivelExcluirEntidadeException;
import com.consultemed.service.exception.JaCadastradoException;
import com.consultemed.service.exception.RecursoNaoEncontradoException;

@Service
public class PacienteService {

	private static final String PACIENTE_JA_CADASTRADO = "Paciente já cadastrado";
	private static final String PACIENTE_NAO_ENCONTRADO = "Paciente não encontrado";
	private static final String IMPOSSIVEL_EXCLUIR = "Não é possível excluir o paciente";
	
	@Autowired
	private PacienteRepository pacientes;
	
	public Paciente salvar(Paciente paciente) {
		verificaSeExistePacienteComCpf(paciente);
		return pacientes.save(paciente);
	}
		
	public List<Paciente> pesquisar(PacienteFilter filter) {
		return pacientes.buscarFiltrados(filter);
	}

	public Paciente procurarPorId(Long id) {
		Paciente paciente = verificaSePacienteExiste(id);
		return paciente;
	}
	
	@Transactional
	public void excluir(Long id) {
		verificaSePacienteExiste(id);
		try {			
			this.pacientes.deleteById(id);
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException(IMPOSSIVEL_EXCLUIR);
		}
	}
	
	private void verificaSeExistePacienteComCpf(Paciente paciente) {
		Optional<Paciente> pacienteOptional = pacientes.findByCpf(paciente.getCpf());		
		pacienteOptional.ifPresent(pacienteBanco -> {
			if(!pacienteBanco.equals(paciente))
				throw new JaCadastradoException(PACIENTE_JA_CADASTRADO);
		});
	}
		
	private Paciente verificaSePacienteExiste(Long id) {
		Optional<Paciente> paciente = pacientes.findById(id);		
		if(!paciente.isPresent()) 
			throw new RecursoNaoEncontradoException(PACIENTE_NAO_ENCONTRADO);
		return paciente.get();
	}
	
}
