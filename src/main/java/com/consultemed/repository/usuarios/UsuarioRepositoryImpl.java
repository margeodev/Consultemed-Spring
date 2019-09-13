package com.consultemed.repository.usuarios;

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

import com.consultemed.model.Usuario;
import com.consultemed.repository.filter.UsuarioFilter;



public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@Autowired
	private EntityManager manager;

	@Override
	public List<Usuario> buscarFiltrados(UsuarioFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Usuario> query = manager.createQuery(criteria);
		
		return query.getResultList();
				
	}
	
	private Predicate[] criarRestricoes(UsuarioFilter filter, CriteriaBuilder builder,
			Root<Usuario> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get("nome")), "%" + filter.getNome().toLowerCase() + "%"));
		}

		if (!StringUtils.isEmpty(filter.getEmail())) {
			predicates.add(builder.equal(
					builder.lower(root.get("email")), filter.getEmail().toLowerCase()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	
	}
}
