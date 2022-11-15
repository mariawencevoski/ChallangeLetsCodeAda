package com.challengeada.api.Repositorios;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.challengeada.api.Entidades.Registro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@NamedQueries({
		@NamedQuery(name = "RegistroRepository.findByFuncionarioId",
				query = "SELECT lanc FROM Registro lanc WHERE lanc.funcionario.id = :funcionarioId") })
public interface RegistroRepository extends JpaRepository<Registro, Long> {

	List<Registro> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

	Page<Registro> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
