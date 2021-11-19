package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.alura.livraria.dto.AtualizacaoAutorFormDto;
import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private AutorRepository autorRepository;	

	@InjectMocks
	private AutorService service;
	
	public AutorFormDto criarUmAutorFormDto() {
		return new AutorFormDto(
				"Belo Autor", 
				"autor@email.com", 
				LocalDate.now(),
				"Curriculo bem grande");
	}
	
	public AtualizacaoAutorFormDto criarUmAtualizacaoAutorFormDto() {
		return new AtualizacaoAutorFormDto(
				1l,
				"Belo Autor", 
				"autor@email.com", 
				LocalDate.now(),
				"Curriculo bem grande");
	}
	
	@Test
	void deveriaCadastrarUmAutor() {
		AutorFormDto formDto = criarUmAutorFormDto();
		Autor autor = new Autor(
				formDto.getNome(), 
				formDto.getEmail(), 
				formDto.getDataNascimento(), 
				formDto.getCurriculo());
		
		Mockito
		.when(modelMapper.map(formDto, Autor.class))
		.thenReturn(autor);
		
		AutorDto autorDto = new AutorDto();
		autorDto.setNome(formDto.getNome());
		autorDto.setEmail(formDto.getEmail());
		autorDto.setDataNascimento(formDto.getDataNascimento());
		Mockito
		.when(modelMapper.map(autor, AutorDto.class))
		.thenReturn(autorDto);
		
		
		AutorDto dto = service.cadastrar(formDto);
			
		assertEquals(formDto.getNome(), dto.getNome());
		assertEquals(formDto.getEmail(), dto.getEmail());
		assertEquals(formDto.getDataNascimento(), dto.getDataNascimento());
	}
	
	@Test
	void deveriaAlterarUmAutor() {
		AtualizacaoAutorFormDto formDto = criarUmAtualizacaoAutorFormDto();
		
		Autor autor = new Autor(
				formDto.getNome(), 
				formDto.getEmail(), 
				formDto.getDataNascimento(), 
				formDto.getCurriculo());
		
		Mockito
		.when(modelMapper.map(formDto, Autor.class))
		.thenReturn(autor);
		
		AutorDto autorDto = new AutorDto();
		autorDto.setNome(formDto.getNome());
		autorDto.setEmail(formDto.getEmail());
		autorDto.setDataNascimento(formDto.getDataNascimento());
		Mockito
		.when(modelMapper.map(autor, AutorDto.class))
		.thenReturn(autorDto);

		AutorDto dto = service.cadastrar(formDto);
			
		assertEquals(formDto.getNome(), dto.getNome());
		assertEquals(formDto.getEmail(), dto.getEmail());
		assertEquals(formDto.getDataNascimento(), dto.getDataNascimento());
	}

}
