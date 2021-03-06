package br.com.alura.livraria.service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.livraria.dto.AtualizacaoLivroFormDto;
import br.com.alura.livraria.dto.DetalhesLivroDto;
import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Page<LivroDto> listar(Pageable paginacao) {
		Page<Livro> livros = livroRepository.findAll(paginacao);
		return livros.map(l -> modelMapper.map(l, LivroDto.class));
	}

	@Transactional
	public LivroDto cadastrar(LivroFormDto dto) {
		Long idAutor = dto.getAutorId();

		try {
			Autor autor = autorRepository.getById(idAutor);
			Livro livro = modelMapper.map(dto, Livro.class);
			livro.setId(null);
			livro.setAutor(autor);

			livroRepository.save(livro);
			return modelMapper.map(livro, LivroDto.class);

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Usuário inexistente");
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("Usuário inexistente");
		}
	}

	@Transactional
	public LivroDto atualizar(@Valid AtualizacaoLivroFormDto dto) {
		Long autorId = dto.getAutorId();			
		Autor autor = autorRepository.findById(autorId).orElseThrow(() -> new EntityNotFoundException("Autor inexistente"));;
		System.out.println(autor.toString());
		Livro livro = livroRepository.getById(dto.getId());
		livro.atualizaInformacoes(dto.getTitulo(), dto.getDataDeLancamento(), dto.getNumeroDePaginas(), autor);
			return modelMapper.map(livro, LivroDto.class);
	}

	@Transactional
	public void remover(@NotNull Long id) {
		livroRepository.deleteById(id);

	}

	public DetalhesLivroDto detalhar(@NotNull Long id) {
		Livro livro = livroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro inexistente"));
		return modelMapper.map(livro, DetalhesLivroDto.class);
	}

}
