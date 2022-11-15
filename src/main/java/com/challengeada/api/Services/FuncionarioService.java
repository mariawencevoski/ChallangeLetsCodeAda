package com.challengeada.api.Services;

import java.util.Optional;

import com.challengeada.api.Entidades.Funcionario;

public interface FuncionarioService {
	
	// Persiste um funcionário na base de dados.

	Funcionario persistir(Funcionario funcionario);
	
	// Busca e retorna um funcionário por CPF

	Optional<Funcionario> buscarPorCpf(String cpf);
	
	// Busca e retorna um funcionário por e-mail

	Optional<Funcionario> buscarPorEmail(String email);
	
	// Busca e retorna um funcionário por ID

	Optional<Funcionario> buscarPorId(Long id);

}
