package br.com.alura.livraria.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import br.com.alura.livraria.dto.AtualizacaoAutorFormDto;
import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.dto.DetalhesAutorDto;
import br.com.alura.livraria.service.AutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/autores")
@Api(tags = "Autor" )
public class AutorController {

	@Autowired
	private AutorService service;	

	@GetMapping
	@ApiOperation("Listar autores")
	public Page<AutorDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
		return service.listar(paginacao);
	}
	
	@PostMapping
	@ApiOperation("Cadastrar novo autor")
	public ResponseEntity<AutorDto> cadastrar(@RequestBody @Valid 
			AutorFormDto dto,
			UriComponentsBuilder uriBuilder) {
			
		AutorDto autorDto = service.cadastrar(dto);
		
		URI uri = uriBuilder
		.path("/autores/{id}")		
		.buildAndExpand(autorDto.getId())
		.toUri();		
		return ResponseEntity.created(uri).body(autorDto);	
	}
	
	@PutMapping
	@ApiOperation("Alterar atributos de um autor")
	public ResponseEntity<AutorDto> atualizar(@RequestBody @Valid 
			AtualizacaoAutorFormDto dto) {	
		AutorDto atualizado = service.atualizar(dto);	
		return ResponseEntity.ok(atualizado);	
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("Deletar um autor")
	public ResponseEntity<AutorDto> remover(@PathVariable @NotNull Long id) {
		try {
			service.remover(id);		
			return ResponseEntity.noContent().build();
		} catch (DataIntegrityViolationException ex) {
			throw new IllegalArgumentException("Autor com livro publicado não pode ser deletado");
		}
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Mostrar detalhes de um autor")
	public ResponseEntity<DetalhesAutorDto> detalhar(@PathVariable @NotNull Long id) {
		DetalhesAutorDto dto = service.detalhar(id);		
		return ResponseEntity.ok(dto);
	}
	
}
