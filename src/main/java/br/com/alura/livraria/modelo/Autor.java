package br.com.alura.livraria.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "autores")
public class Autor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	@Column(name = "data_de_nascimento")
	private LocalDate dataNascimento;
	private String curriculo;
	
	public Autor(String nome, String email, LocalDate dataNascimento, String curriculo) {
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.curriculo = curriculo;
	}

	public void atualizaInformacoes(
			String nome,
			String email, 
			LocalDate dataNascimento, 
			String curriculo) {
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.curriculo = curriculo;	
	}
	
	
}