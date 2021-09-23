package br.com.alura.livraria.modelo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Livro {
	private String titulo;
	private LocalDate dataDeLancamento;
	private int numeroDePaginas;
	private String nomeAutor;
	
}
