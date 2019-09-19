package com.consultemed.repository.pacientes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultemed.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>, PacienteRepositoryQuery {
	
	public Optional<Paciente> findByCpf(String cpf);

}
