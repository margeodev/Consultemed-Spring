package com.consultemed.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consultemed.model.Medico;
import com.consultemed.repository.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicos;
	
	public List<Medico> listar() {
		return medicos.findAll();
	}
	
	public Medico salvar(Medico medico) {
		return medicos.save(medico);
	}
}
