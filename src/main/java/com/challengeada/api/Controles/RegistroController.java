package com.challengeada.api.Controles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challengeada.api.Dtos.RegistroDto;
import com.challengeada.api.Entidades.Funcionario;
import com.challengeada.api.Entidades.Registro;
import com.challengeada.api.Enums.TipoEnum;
import com.challengeada.api.Resposta.Resposta;
import com.challengeada.api.Services.FuncionarioService;
import com.challengeada.api.Services.RegistroService;

@RestController
@RequestMapping("/api/registros")
@CrossOrigin(origins = "*")
public class RegistroController {

	private static final Logger log = LoggerFactory.getLogger(RegistroController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private RegistroService registroService;

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public RegistroController() {
	}

	// Consulta a listagem de lançamentos de um funcionário

	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Resposta<Page<RegistroDto>>> listarPorFuncionarioId(
			@PathVariable("funcionarioId") Long funcionarioId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando registros por ID do funcionário: {}, página: {}", funcionarioId, pag);
		Resposta<Page<RegistroDto>> resposta = new Resposta<Page<RegistroDto>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Registro> lancamentos = this.registroService.buscarPorFuncionarioId(funcionarioId, pageRequest);
		Page<RegistroDto> lancamentosDto = lancamentos.map(lancamento -> this.converterLancamentoDto(lancamento));

		resposta.setData(lancamentosDto);
		return ResponseEntity.ok(resposta);
	}

	// Consulta um registro

	@GetMapping(value = "/{id}")
	public ResponseEntity<Resposta<RegistroDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando registro por ID: {}", id);
		Resposta<RegistroDto> resposta = new Resposta<RegistroDto>();
		Optional<Registro> lancamento = this.registroService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Registro não encontrado para o ID: {}", id);
			resposta.getErrors().add("Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(resposta);
		}

		resposta.setData(this.converterLancamentoDto(lancamento.get()));
		return ResponseEntity.ok(resposta);
	}

	// Adiciona um novo registro

	@PostMapping
	public ResponseEntity<Resposta<RegistroDto>> adicionar(@Valid @RequestBody RegistroDto registroDto,
														   BindingResult result) throws ParseException {
		log.info("Adicionando registro: {}", registroDto.toString());
		Resposta<RegistroDto> resposta = new Resposta<RegistroDto>();
		validarFuncionario(registroDto, result);
		Registro registro = this.converterDtoParaLancamento(registroDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando registro: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> resposta.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(resposta);
		}

		registro = this.registroService.persistir(registro);
		resposta.setData(this.converterLancamentoDto(registro));
		return ResponseEntity.ok(resposta);
	}

	// Atualiza os dados de um registro

	@PutMapping(value = "/{id}")
	public ResponseEntity<Resposta<RegistroDto>> atualizar(@PathVariable("id") Long id,
														   @Valid @RequestBody RegistroDto registroDto, BindingResult result) throws ParseException {
		log.info("Atualizando registro: {}", registroDto.toString());
		Resposta<RegistroDto> resposta = new Resposta<RegistroDto>();
		validarFuncionario(registroDto, result);
		registroDto.setId(Optional.of(id));
		Registro registro = this.converterDtoParaLancamento(registroDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando registro: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> resposta.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(resposta);
		}

		registro = this.registroService.persistir(registro);
		resposta.setData(this.converterLancamentoDto(registro));
		return ResponseEntity.ok(resposta);
	}

	// Remove um registro

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Resposta<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo registro: {}", id);
		Resposta<String> resposta = new Resposta<String>();
		Optional<Registro> lancamento = this.registroService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Erro ao remover devido ao registro ID: {} ser inválido.", id);
			resposta.getErrors().add("Erro ao remover registro. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(resposta);
		}

		this.registroService.remover(id);
		return ResponseEntity.ok(new Resposta<String>());
	}

	// Valida um funcionário, verificando se ele é existente e válido no sistema

	private void validarFuncionario(RegistroDto registroDto, BindingResult result) {
		if (registroDto.getFuncionarioId() == null) {
			result.addError(new ObjectError("funcionario", "Funcionário não informado."));
			return;
		}

		log.info("Validando funcionário id {}: ", registroDto.getFuncionarioId());
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(registroDto.getFuncionarioId());
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionário não encontrado. ID inexistente."));
		}
	}

	// Converte uma entidade registro para seu respectivo DTO

	private RegistroDto converterLancamentoDto(Registro registro) {
		RegistroDto registroDto = new RegistroDto();
		registroDto.setId(Optional.of(registro.getId()));
		registroDto.setData(this.dateFormat.format(registro.getData()));
		registroDto.setTipo(registro.getTipo().toString());
		registroDto.setDescricao(registro.getDescricao());
		registroDto.setLocalizacao(registro.getLocalizacao());
		registroDto.setFuncionarioId(registro.getFuncionario().getId());

		return registroDto;
	}

	// Converte um RegistroDto para uma entidade Registro.

	private Registro converterDtoParaLancamento(RegistroDto registroDto, BindingResult result) throws ParseException {
		Registro registro = new Registro();

		if (registroDto.getId().isPresent()) {
			Optional<Registro> lanc = this.registroService.buscarPorId(registroDto.getId().get());
			if (lanc.isPresent()) {
				registro = lanc.get();
			} else {
				result.addError(new ObjectError("lancamento", "Lançamento não encontrado."));
			}
		} else {
			registro.setFuncionario(new Funcionario());
			registro.getFuncionario().setId(registroDto.getFuncionarioId());
		}

		registro.setDescricao(registroDto.getDescricao());
		registro.setLocalizacao(registroDto.getLocalizacao());
		registro.setData(this.dateFormat.parse(registroDto.getData()));

		if (EnumUtils.isValidEnum(TipoEnum.class, registroDto.getTipo())) {
			registro.setTipo(TipoEnum.valueOf(registroDto.getTipo()));
		} else {
			result.addError(new ObjectError("tipo", "Tipo inválido."));
		}

		return registro;
	}
}
