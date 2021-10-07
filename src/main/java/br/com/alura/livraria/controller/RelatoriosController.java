package br.com.alura.livraria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.livraria.dto.ItemLivrariaDto;
import br.com.alura.livraria.service.RelatoriosService;

@RestController
@RequestMapping("/relatorios")
public class RelatoriosController {
	
	@Autowired
	private RelatoriosService service;
	
	@GetMapping("/livraria")
	public List<ItemLivrariaDto> relatorioLivrariaPorAutor() {
		return service.relatorioLivrariaPorAutor();
	}
	
}
