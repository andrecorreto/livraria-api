package br.com.alura.livraria.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.livraria.infra.security.TokenService;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AutorControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private AutorRepository repository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Test
	void naoDeveriaCadastrarAutorComDadosIncompletos() throws Exception {
		String json = "{}";
		
		Usuario logado = new Usuario("Andre", "andre", "123456");
		Perfil admin = perfilRepository.findById(1l).get();
		logado.adicionarPerfil(admin);
		usuarioRepository.save(logado);	
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin()) ;		
		String token = tokenService.gerarToken(authentication);
		
		mvc
		.perform(
				post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(status().isBadRequest());
		
	}

	@Test
	void deveriaCadastrarAutorComDadosCompletos() throws Exception {
		String json = "{"
				+ " \"nome\":\"Fulano\", "
				+ " \"email\":\"fulano@email.com\", "
				+ " \"dataNascimento\":\"1969-03-21\", "
				+ " \"curriculo\":\"Fez bastante coisa\"} ";
		
		String jsonRetorno = "{"
				+ " \"nome\":\"Fulano\", "
				+ " \"email\":\"fulano@email.com\", "
				+ " \"dataNascimento\":\"1969-03-21\"} ";
		
		Usuario logado = new Usuario("Andre", "andre", "123456");
		Perfil admin = perfilRepository.findById(1l).get();
		logado.adicionarPerfil(admin);
		usuarioRepository.save(logado);	
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin()) ;		
		String token = tokenService.gerarToken(authentication);
		
		mvc
		.perform(
				post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(status().isCreated())
		.andExpect(header().exists("Location"))
		.andExpect(content().json(jsonRetorno));	
	}
	
	@Test
	void deveriaAtualizarAutorComDadosCompletos() throws Exception {
		
		Autor autorFormDto = new Autor(
				"André da Silva",
				"andre@email.com",
				LocalDate.of(1969, 03, 21),
				"Bem extenso");
		Autor a1 = repository.save(autorFormDto);
	
		String json = "{"
				+ " \"id\" :" + a1.getId() + " , "
				+ " \"nome\":\"Fulano\", "
				+ " \"email\":\"fulano@email.com\", "
				+ " \"dataNascimento\":\"1984-09-12\", "
				+ " \"curriculo\":\"Fez bastante coisa\"} ";
		
		String jsonRetorno = "{"
				+ " \"nome\":\"Fulano\", "
				+ " \"email\":\"fulano@email.com\", "
				+ " \"dataNascimento\":\"1984-09-12\"} ";
		
		Usuario logado = new Usuario("Andre", "andre", "123456");
		Perfil admin = perfilRepository.findById(1l).get();
		logado.adicionarPerfil(admin);
		usuarioRepository.save(logado);	
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin()) ;		
		String token = tokenService.gerarToken(authentication);
		
		mvc
		.perform(
				put("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(status().isOk())
		.andExpect(content().json(jsonRetorno));	
	}
}
