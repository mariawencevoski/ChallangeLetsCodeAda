package com.challengeada.api.Services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.challengeada.api.Entidades.Registro;
import com.challengeada.api.Repositorios.RegistroRepository;
import com.challengeada.api.Services.RegistroService;

@Service
public class RegistroServiceImpl implements RegistroService {

	private static final Logger log = LoggerFactory.getLogger(RegistroServiceImpl.class);

	@Autowired
	private RegistroRepository registroRepository;

	public Page<Registro> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		log.info("Buscando registros para o funcionário ID {}", funcionarioId);
		return this.registroRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}
	
	@Cacheable("registroPorId")
	public Optional<Registro> buscarPorId(Long id) {
		log.info("Buscando um registro pelo ID {}", id);
		return Optional.ofNullable(this.registroRepository.findOne(id));
	}
	
	@CachePut("registroPorId")
	public Registro persistir(Registro registro) {
		log.info("Persistindo o registro: {}", registro);
		return this.registroRepository.save(registro);
	}
	
	public void remover(Long id) {
		log.info("Removendo o registro ID {}", id);
		this.registroRepository.delete(id);
	}

}
