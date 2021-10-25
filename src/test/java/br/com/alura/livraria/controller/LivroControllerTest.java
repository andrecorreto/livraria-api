package br.com.alura.livraria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class LivroControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private AutorRepository repository;
	
	
	@Test
	void naoDeveriaCadastrarLivroComDadosIncompletos() throws Exception {
		String json = "{}";	

		mvc
		.perform(
				post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}

	@Test
	void naoDeveriaCadastrarLivroComAutorInexistente() throws Exception {
		String json = 	
		"{	  \"titulo\" : \"Um livro muito bom\" , "
		 + "  \"dataDeLancamento\" : \"2012-01-01\", "
		 + "  \"numeroDePaginas\" : \"328\", "
		 + "  \"autorId\" : 17 } ";
		
		mvc
		.perform(
				post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().is5xxServerError());
	}
	
	@Test
	void deveriaCadastrarLivro() throws Exception {

		Autor autorFormDto = new Autor(
				"André da Silva",
				"andre@email.com",
				LocalDate.of(1969, 03, 21),
				"Bem extenso");
		Autor a1 = repository.save(autorFormDto);
		
		String json =
		"{	  \"titulo\" : \"Um livro muito bom\" , "
		 + "  \"dataDeLancamento\" : \"2012-01-01\", "
		 + "  \"numeroDePaginas\" : \"328\", "
		 + "  \"autorId\" : " + a1.getId() + "  } ";

		String jsonRetorno =
		"{	  \"titulo\" : \"Um livro muito bom\" , "
		 + "  \"dataDeLancamento\" : \"2012-01-01\", "
		 + "  \"numeroDePaginas\" : " + 328 + ", "
		 + "  \"idAutor\" : " + a1.getId() + ", "
		 + "  \"nomeAutor\" : \"André da Silva\"} ";
		
		mvc
		.perform(
				post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isCreated())
		.andExpect(header().exists("Location"))
		.andExpect(content().json(jsonRetorno));	
	}
	
}
