package com.challengeada.api.Services;

import java.util.Optional;

import com.challengeada.api.Entidades.Empresa;

public interface EmpresaService {

	// Consulta uma empresa dado um CNPJ

	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	// Cadastra uma nova empresa na base de dados

	Empresa persistir(Empresa empresa);
	
}
