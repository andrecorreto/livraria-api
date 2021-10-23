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

import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

	@Mock
	private LivroRepository livroRepository;	
	@Mock
	private AutorRepository autorRepository;
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

		LivroDto dto = service.cadastrar(formDto);
			
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
