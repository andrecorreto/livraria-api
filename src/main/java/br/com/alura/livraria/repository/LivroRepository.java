package br.com.alura.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.livraria.dto.ItemLivrariaDto;
import br.com.alura.livraria.modelo.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
	
	
	@Query("select new br.com.alura.livraria.dto.ItemLivrariaDto( " 
			+"a.autor.nome, "
			+"count(*), "
			+"(count(*) * 1.0 / (select count(*) from Livro) * 1.0  ) ) " 
			+ "from Livro a group by a.autor.nome")
	List<ItemLivrariaDto> relatorioLivrariaPorAutor();
}
