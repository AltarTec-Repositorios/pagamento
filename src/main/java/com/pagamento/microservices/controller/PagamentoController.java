package com.pagamento.microservices.controller;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pagamento.microservices.model.Pagamento;
import com.pagamento.microservices.service.PagamentoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api-operacao")
@Api(value="API REST Produtos")
@CrossOrigin(origins="*")
public class PagamentoController {

	@Autowired
	PagamentoService service;

	private static final Logger logger = Logger.getLogger(PagamentoController.class);
	
	{
		BasicConfigurator.configure();
	}

	@PostMapping("/pagamento")
	@ApiOperation(value = "Envia o pagamento")
	public ResponseEntity<Pagamento> pagamento(@RequestBody Pagamento pagamento) {

		logger.info("Iniciando registro de pagamento");

		Pagamento retornoPgto = service.inclui(pagamento);

		if (null != retornoPgto.getTransacao().getId()) {
			logger.info("Pagamento registrado com sucesso!");
			logger.info("Código da transacao: " + retornoPgto.getTransacao().getId());
			return new ResponseEntity<Pagamento>(retornoPgto, HttpStatus.CREATED);
		}
		logger.info("Atenção! Ocorreu algum problema no registro do pagamento");
		logger.info(pagamento);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pagamento);
	}

	@GetMapping("/consulta")
	@Cacheable("consulta")
	@ApiOperation(value = "Consulta todos os pagamentos")
	public ResponseEntity<List<Pagamento>> consulta() {

		logger.info("Iniciando consulta de pagamento");

		List<Pagamento> retornaListaPagamento = service.consulta();

		if (retornaListaPagamento.size() < 1) {
			logger.info("Consulta retornou sem valores encontrados");
			return new ResponseEntity<List<Pagamento>>(retornaListaPagamento, HttpStatus.NO_CONTENT);
		}
		logger.info("Consulta efetuada com sucesso!");
		return new ResponseEntity<List<Pagamento>>(retornaListaPagamento, HttpStatus.OK);
	}

	@GetMapping("/consulta/{id}")
	@ApiOperation(value = "Consulta o pagamento pelo codigo da transação")
	public ResponseEntity<Pagamento> consultaId(@PathVariable("id") String id) {

		logger.info("Iniciando consulta de pagamento");
		logger.info("Código da transacao para consulta: " + id);

		Pagamento retornaConsulta = service.consultaId(id);

		if (null != retornaConsulta) {
			logger.info("Consulta efetuada com sucesso!");
			return new ResponseEntity<Pagamento>(retornaConsulta, HttpStatus.OK);
		}
		logger.info("Consulta retornou sem valores encontrados");
		return new ResponseEntity<Pagamento>(retornaConsulta, HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("/estorno/{id}")
	@ApiOperation(value = "Envia o estorno do pagamento")
	public ResponseEntity<Pagamento> estorno(@PathVariable("id") String id) {

		logger.info("Iniciando estorno de pagamento");
		logger.info("Código da transacao para estorno: " + id);

		Pagamento retornaEstorno = service.estorna(id);

		if ("CANCELADO".contentEquals(retornaEstorno.getTransacao().getDescricao().getStatus())) {
			logger.info("Estorno efetuado com sucesso!");
			return new ResponseEntity<Pagamento>(retornaEstorno, HttpStatus.OK);
		}
		logger.info("Atenção! Pagamento para estorno não encontrado");
		return new ResponseEntity<Pagamento>(retornaEstorno, HttpStatus.NOT_FOUND);

	}

}
