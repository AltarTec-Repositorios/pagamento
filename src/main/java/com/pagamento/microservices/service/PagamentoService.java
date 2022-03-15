package com.pagamento.microservices.service;

import java.util.List;

import com.pagamento.microservices.model.Pagamento;

public interface PagamentoService {

	Pagamento inclui(Pagamento pagamento);

	List<Pagamento> consulta();
		
	Pagamento consultaId(String id);

	Pagamento estorna(String id);

}
