package br.com.alura.livraria.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDto {
	private Long id;
	private String titulo;
	private LocalDate dataDeLancamento;
	private int numeroDePaginas;
	private Long idAutor;
	private String nomeAutor;
}
