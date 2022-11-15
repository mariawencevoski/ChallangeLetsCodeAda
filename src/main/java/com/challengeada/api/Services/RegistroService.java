package com.challengeada.api.Services;

import java.util.Optional;

import com.challengeada.api.Entidades.Registro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RegistroService {

	// Retorna uma lista paginada de registros de um determinado funcionário

	Page<Registro> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);
	
	// Retorna um registro por ID

	Optional<Registro> buscarPorId(Long id);
	
	// Persiste um registro na base de dados

	Registro persistir(Registro registro);
	
	// Remove um registro da base de dados

	void remover(Long id);
	
}
