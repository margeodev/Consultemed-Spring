package com.consultemed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultemed.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

}
