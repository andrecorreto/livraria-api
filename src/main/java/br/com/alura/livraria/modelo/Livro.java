package br.com.alura.livraria.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

// Jpa
@Entity
@Table(name = "livros")
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private LocalDate dataDeLancamento;
	private Integer numeroDePaginas;
	@ManyToOne
	private Autor autor;
	
	public Livro(String titulo, LocalDate dataDeLancamento, Integer numeroDePaginas, Autor autor) {
		this.titulo = titulo;
		this.dataDeLancamento = dataDeLancamento;
		this.numeroDePaginas = numeroDePaginas;
		this.autor = autor;
	}

	public void atualizaInformacoes(
			String titulo, 
			LocalDate dataDeLancamento, 
			int numeroDePaginas, 
			Autor autor) { 
		this.titulo = titulo;
		this.dataDeLancamento = dataDeLancamento;
		this.numeroDePaginas = numeroDePaginas;
		this.autor = autor;
	}
	
	
}
