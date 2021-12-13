package br.com.alura.livraria.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.livraria.dto.ItemLivrariaDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class LivroRepositoryTest {
	
	@Autowired
	private LivroRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	public void popularBancoDeDados() {
		Autor autor1 = new Autor(
				"André da Silva",
				"andre@email.com",
				LocalDate.of(1969, 03, 21),
				"Bem extenso");
		em.persist(autor1);
		
		Autor autor2 = new Autor(
				"Fernanda Nogueira",
				"fernanda@email.com",
				LocalDate.of(1975, 02, 04),
				"Bem bonitinho, fofo");
		em.persist(autor2);
		
		Autor autor3 = new Autor(
				"Juliana Carvalho",
				"juliana@email.com",
				LocalDate.of(1984, 07, 25),
				"Legal e impressionante");
		em.persist(autor3);
		
		Autor autor4 = new Autor(
				"Rita de Assis",
				"rita@email.com",
				LocalDate.of(1992, 10, 14),
				"Muito pequeno e faltando");
		em.persist(autor4);
		
		Autor autor5 = new Autor(
				"Rodrigo de Souza",
				"rodrigo@email.com",
				LocalDate.of(1963, 05, 17),
				"Bem pensado mas sem graça");
		em.persist(autor5);
		
		Livro livro1 = new Livro(
				"Aprenda Java em 21 dias",
				LocalDate.of(2004, 12, 03),
				272,
				autor1);	
		em.persist(livro1);
		
		Livro livro2 = new Livro(
				"Como ser mais produtivo",
				LocalDate.of(2004, 04, 21),
				203,
				autor2);	
		em.persist(livro2);
		
		Livro livro3 = new Livro(
				"Aprenda a falar em público",
				LocalDate.of(2004, 07, 01),
				195,
				autor3);
		em.persist(livro3);
		
		Livro livro4 = new Livro(
				"Otimizando seu tempo",
				LocalDate.of(2004, 12, 10),
				159,
				autor2);
		em.persist(livro4);
		
		Livro livro5 = new Livro(
				"Como fazer bolos incríveis",
				LocalDate.of(2008, 9, 12),
				304,
				autor4);
		em.persist(livro5);
		
		Livro livro6 = new Livro(
				"Investindo em ações na bolsa de valores",
				LocalDate.of(2010, 12, 21),
				462,
				autor5);
		em.persist(livro6);

		Livro livro7 = new Livro(
				"Aprenda Python em 12 dias",
				LocalDate.of(2012, 01, 01),
				328,
				autor1);
		em.persist(livro7);
	}
	
	@BeforeEach   //para ser executado antes de cada metodo
	public void inicializar() {
		popularBancoDeDados();
	}
	
	@Test
	void deveriaRetornarRelatorioLivrariaPorAutor() {	
		List<ItemLivrariaDto> relatorio = repository.relatorioLivrariaPorAutor();
		Assertions.assertThat(relatorio)
		.hasSize(5)
		.extracting(ItemLivrariaDto::getAutor, 
				ItemLivrariaDto::getQuantidadeDeLivros,
				ItemLivrariaDto::getPercentual)
		.containsExactlyInAnyOrder(
				Assertions.tuple("André da Silva", 2l, 0.2857142857142857),
				Assertions.tuple("Fernanda Nogueira", 2l, 0.2857142857142857),
				Assertions.tuple("Juliana Carvalho", 1l, 0.14285714285714285),
				Assertions.tuple("Rita de Assis", 1l, 0.14285714285714285),
				Assertions.tuple("Rodrigo de Souza", 1l, 0.14285714285714285));
	}

}
