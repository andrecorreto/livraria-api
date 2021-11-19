package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

	@Mock
	private LivroRepository livroRepository;	
	@Mock
	private AutorRepository autorRepository;
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private LivroService service;
		
	public LivroFormDto criarUmLivroFormDto() {
		return new LivroFormDto(
				"Outro livro melhor ainda",
				LocalDate.now(),
				354,
				4l);
	}
	
	@Test
	void deveriaCadastrarUmLivro() {
		
		LivroFormDto formDto = criarUmLivroFormDto();
		Livro livro = new Livro(
				formDto.getTitulo(), 
				formDto.getDataDeLancamento(),
				formDto.getNumeroDePaginas(),
				new Autor());

		Mockito
		.when(modelMapper.map(formDto, Livro.class))
		.thenReturn(livro);
		
		LivroDto livroDto = new LivroDto(
				livro.getId(), 
				livro.getTitulo(), 
				livro.getDataDeLancamento(), 
				livro.getNumeroDePaginas(), 
				livro.getAutor().getId(), 
				livro.getAutor().getNome());
		Mockito
		.when(modelMapper.map(livro, LivroDto.class))
		.thenReturn(livroDto);
		
		LivroDto dto = service.cadastrar(formDto);
		
		Mockito.verify(livroRepository).save(Mockito.any());
			
		assertEquals(formDto.getTitulo(), dto.getTitulo());
		assertEquals(formDto.getDataDeLancamento(), dto.getDataDeLancamento());
		assertEquals(formDto.getNumeroDePaginas(), dto.getNumeroDePaginas());
		
	}

	@Test
	void naoDeveriaCadastrarUmLivroComAutorInexistente() {
		
		LivroFormDto formDto = criarUmLivroFormDto();
				
		Mockito
		.when(autorRepository.getById(formDto.getAutorId()))
		.thenThrow(EntityNotFoundException.class);
	
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto));		
	}
}
