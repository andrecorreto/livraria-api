package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.repository.AutorRepository;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

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
	
	@Test
	void deveriaCadastrarUmAutor() {
		AutorFormDto formDto = criarUmAutorFormDto();

		AutorDto dto = service.cadastrar(formDto);
			
		assertEquals(formDto.getNome(), dto.getNome());
		assertEquals(formDto.getEmail(), dto.getEmail());
		assertEquals(formDto.getDataNascimento(), dto.getDataNascimento());
	}

}
