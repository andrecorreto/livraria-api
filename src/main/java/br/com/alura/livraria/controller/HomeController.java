package br.com.alura.livraria.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/home")
@Api(tags = "Teste de funcionamento" )
public class HomeController {
	
	@GetMapping
	public String home() {
		return "Teste funcionou!";
	}

}
