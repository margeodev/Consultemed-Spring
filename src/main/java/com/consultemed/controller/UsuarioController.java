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

import com.consultemed.model.Usuario;
import com.consultemed.repository.GrupoRepository;
import com.consultemed.repository.filter.UsuarioFilter;
import com.consultemed.service.UsuarioService;
import com.consultemed.service.exception.JaCadastradoException;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	private static final String NOVO_USUARIO = "pages/usuarios/cadastrar-usuario";
	private static final String PESQUISAR_USUARIO = "pages/usuarios/pesquisar-usuarios";

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private GrupoRepository grupos;
		
	
	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView(NOVO_USUARIO);
		mv.addObject("grupos", grupos.findAll());
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter) {		
		ModelAndView mv = new ModelAndView(PESQUISAR_USUARIO);
		mv.addObject("usuarios", service.pesquisar(usuarioFilter));			
		return mv;
	}

	@PostMapping(value = {"/salvar", "{\\d}"})
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {
				
		if (result.hasErrors()) {
			return novo(usuario);
		}
		
		try {
			service.salvar(usuario);
		} catch(JaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(usuario);
		}		
		attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso!");
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView excluir(@PathVariable Long id, RedirectAttributes attributes) {
		this.service.excluir(id);		
		attributes.addFlashAttribute("mensagem", "Usuário removido com sucesso!");
		return new ModelAndView("redirect:/usuarios");
	}	
		
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Usuario usuario) {
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		return mv;
	}
}
