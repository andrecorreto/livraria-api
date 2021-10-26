package br.com.alura.livraria.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.livraria.dto.AtualizacaoLivroFormDto;
import br.com.alura.livraria.dto.DetalhesLivroDto;
import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.service.LivroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/livros")
@Api(tags = "Livro" )
public class LivroController {
	@Autowired
	private LivroService service;	

	@GetMapping
	@ApiOperation("Listar livros")
	public Page<LivroDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
		return service.listar(paginacao);
	}
	
	@PostMapping
	@ApiOperation("Cadastrar novo livro")
	public ResponseEntity<LivroDto> cadastrar(@RequestBody @Valid 
			LivroFormDto dto, UriComponentsBuilder uriBuilder) {
		
		LivroDto livroDto = service.cadastrar(dto);
		
		URI uri = uriBuilder
				.path("/livros/{id}")
				.buildAndExpand(livroDto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(livroDto);
		
	}
	
	@PutMapping
	@ApiOperation("Alterar atributos de um livro")
	public ResponseEntity<LivroDto> atualizar(@RequestBody @Valid 
			AtualizacaoLivroFormDto dto) {	
		LivroDto atualizado = service.atualizar(dto);	
		return ResponseEntity.ok(atualizado);	
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("Deletar um livro")
	public ResponseEntity<LivroDto> remover(@PathVariable @NotNull Long id) {
		service.remover(id);		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Mostrar detalhes de um livro")
	public ResponseEntity<DetalhesLivroDto> detalhar(@PathVariable @NotNull Long id) {
		DetalhesLivroDto dto = service.detalhar(id);		
		return ResponseEntity.ok(dto);
	}
	

}
