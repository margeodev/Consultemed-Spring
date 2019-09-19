package com.consultemed.repository.pacientes;

import java.util.List;

import com.consultemed.model.Paciente;
import com.consultemed.repository.filter.PacienteFilter;

public interface PacienteRepositoryQuery {

	public List<Paciente> buscarFiltrados(PacienteFilter filtro);
	
}
