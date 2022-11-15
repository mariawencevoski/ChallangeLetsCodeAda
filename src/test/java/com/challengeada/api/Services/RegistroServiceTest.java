package com.challengeada.api.Services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import com.challengeada.api.Entidades.Registro;
import com.challengeada.api.Repositorios.RegistroRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RegistroServiceTest {

	@MockBean
	private RegistroRepository registroRepository;

	@Autowired
	private RegistroService registroService;

	@Before
	public void setUp() throws Exception {
		BDDMockito
				.given(this.registroRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
				.willReturn(new PageImpl<Registro>(new ArrayList<Registro>()));
		BDDMockito.given(this.registroRepository.findOne(Mockito.anyLong())).willReturn(new Registro());
		BDDMockito.given(this.registroRepository.save(Mockito.any(Registro.class))).willReturn(new Registro());
	}

	@Test
	public void testBuscarLancamentoPorFuncionarioId() {
		Page<Registro> lancamento = this.registroService.buscarPorFuncionarioId(1L, new PageRequest(0, 10));

		assertNotNull(lancamento);
	}

	@Test
	public void testBuscarLancamentoPorId() {
		Optional<Registro> lancamento = this.registroService.buscarPorId(1L);

		assertTrue(lancamento.isPresent());
	}

	@Test
	public void testPersistirLancamento() {
		Registro registro = this.registroService.persistir(new Registro());

		assertNotNull(registro);
	}

}
