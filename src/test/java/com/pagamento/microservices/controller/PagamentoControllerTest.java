package com.pagamento.microservices.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.pagamento.microservices.model.Pagamento;
import com.pagamento.microservices.model.Transacao;
import com.pagamento.microservices.service.PagamentoService;

import io.restassured.http.ContentType;

@WebMvcTest
public class PagamentoControllerTest {

	@Autowired
	private PagamentoController pagamentoController;

	@MockBean
	private PagamentoService service;

	@BeforeEach
	public void setup() {
		standaloneSetup(this.pagamentoController);
	}

	@Test
	public void sucesso_ConsultaPagamentoListaTransacao() {

		List<Pagamento>lista = new ArrayList<Pagamento>();
		
		Pagamento pagamentoMock = new Pagamento();
		Transacao transacao = new Transacao();
		transacao.setId(100023568900007l);
		transacao.setCartao("1234*******5678");
		pagamentoMock.setTransacao(transacao);
		lista.add(pagamentoMock);
		
		when(this.service.consulta())
		    .thenReturn(lista);

		given()
		     .accept(ContentType.JSON)
		 .when()
		     .get("/api-operacao/consulta")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void sucesso_ConsultaPagamentoIdTransacao() {
		
		Pagamento pagamentoMock = new Pagamento();
		Transacao transacao = new Transacao();
		transacao.setId(100023568900007l);
		transacao.setCartao("1234*******5678");
		pagamentoMock.setTransacao(transacao);
		
		when(this.service.consultaId("100023568900007"))
		    .thenReturn(pagamentoMock);

		given()
		     .accept(ContentType.JSON)
		 .when()
		     .get("/api-operacao/consulta/{id}","100023568900007")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
}
