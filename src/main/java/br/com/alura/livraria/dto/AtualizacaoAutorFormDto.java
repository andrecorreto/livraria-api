package br.com.alura.livraria.dto;

import java.time.LocalDate;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoAutorFormDto extends AutorFormDto {	
	
	@NotNull
	private Long id;
	
	public AtualizacaoAutorFormDto(long id, String nome, String email, LocalDate dataNascimento, String curriculo) {
		this.id = id;
		super.setNome(nome);
		super.setEmail(email);
		super.setDataNascimento(dataNascimento);
		super.setCurriculo(curriculo);
	}
	
}
