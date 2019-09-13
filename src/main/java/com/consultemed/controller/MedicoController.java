package com.consultemed.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.consultemed.model.Medico;
import com.consultemed.service.MedicoService;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private MedicoService medicoService;
	
	@GetMapping
	public String novo() {
		List<Medico> medicos = medicoService.listar();
		System.out.println("Total de médicos: " + medicos.size());
		return "medico/cadastro-medico";
	}
	
	
}
