package com.consultemed.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.consultemed.model.Paciente;
import com.consultemed.repository.filter.PacienteFilter;
import com.consultemed.service.PacienteService;
import com.consultemed.service.exception.JaCadastradoException;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
	
	private static final String NOVO_PACIENTE = "pages/pacientes/cadastrar-paciente";
	private static final String PESQUISAR_PACIENTE = "pages/pacientes/pesquisar-pacientes";
	private static final String SALVO_COM_SUCESSO = "Paciente salvo com sucesso!";
	private static final String REMOVIDO_COM_SUCESSO = "Paciente removido com sucesso!";

	@Autowired
	private PacienteService service;
		
	@GetMapping("/novo")
	public ModelAndView novo(Paciente paciente) {
		ModelAndView mv = new ModelAndView(NOVO_PACIENTE);
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(PacienteFilter filter) {		
		ModelAndView mv = new ModelAndView(PESQUISAR_PACIENTE);
		mv.addObject("pacientes", service.pesquisar(filter));			
		return mv;
	}

	@PostMapping(value = {"/salvar", "{\\d}"})
	public ModelAndView cadastrar(@Valid Paciente paciente, BindingResult result, Model model, RedirectAttributes attributes) {
				
		if (result.hasErrors()) {
			return novo(paciente);
		}
		
		try {
			service.salvar(paciente);
		} catch(JaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(paciente);
		}		
		attributes.addFlashAttribute("mensagem", SALVO_COM_SUCESSO);
		return new ModelAndView("redirect:/pacientes/novo");
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView excluir(@PathVariable Long id, RedirectAttributes attributes) {
		this.service.excluir(id);		
		attributes.addFlashAttribute("mensagem", REMOVIDO_COM_SUCESSO);
		return new ModelAndView("redirect:/pacientes");
	}	
		
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Paciente paciente) {
		ModelAndView mv = novo(paciente);
		mv.addObject(paciente);
		return mv;
	}
}
