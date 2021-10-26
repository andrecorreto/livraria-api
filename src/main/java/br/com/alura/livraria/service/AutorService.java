package br.com.alura.livraria.service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.livraria.dto.AtualizacaoAutorFormDto;
import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.dto.DetalhesAutorDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository autorRepository;

	private ModelMapper modelMapper = new ModelMapper();

	public Page<AutorDto> listar(Pageable paginacao) {		
		Page<Autor> autores = autorRepository.findAll(paginacao);	
		return autores.map(a -> modelMapper.map(a, AutorDto.class));
	}
	
	@Transactional
	public AutorDto cadastrar(AutorFormDto dto) {
		Autor autor = modelMapper.map(dto, Autor.class);
		autorRepository.save(autor);
		
		return modelMapper.map(autor, AutorDto.class);
		
	}

	@Transactional
	public AutorDto atualizar(@Valid AtualizacaoAutorFormDto dto) {
		Autor autor = autorRepository.getById(dto.getId());
		autor.atualizaInformacoes(
				dto.getNome(),
				dto.getEmail(),
				dto.getDataNascimento(),
				dto.getCurriculo());
		return modelMapper.map(autor, AutorDto.class);
	}

	@Transactional
	public void remover(@NotNull Long id) {
		autorRepository.deleteById(id);
	}

	public DetalhesAutorDto detalhar(@NotNull Long id) {
		Autor autor = autorRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Autor inexistente"));
		return modelMapper.map(autor, DetalhesAutorDto.class);
	}

}
