package com.consultemed.repository.pacientes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.consultemed.model.Paciente;
import com.consultemed.repository.filter.PacienteFilter;

public class PacienteRepositoryImpl implements PacienteRepositoryQuery {

	@Autowired
	private EntityManager manager;

	@Override
	public List<Paciente> buscarFiltrados(PacienteFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Paciente> criteria = builder.createQuery(Paciente.class);
		Root<Paciente> root = criteria.from(Paciente.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Paciente> query = manager.createQuery(criteria);
		
		return query.getResultList();
				
	}
	
	private Predicate[] criarRestricoes(PacienteFilter filter, CriteriaBuilder builder,
			Root<Paciente> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get("nome")), "%" + filter.getNome().toLowerCase() + "%"));
		}

		if (!StringUtils.isEmpty(filter.getCpf())) {
			predicates.add(builder.equal(
					builder.lower(root.get("cpf")), filter.getCpf().toLowerCase()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	
	}
}
