package com.pagamento.microservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagamento.microservices.model.Pagamento;
import com.pagamento.microservices.repository.PagamentoRepository;

@Service
public class PagamentoServiceImpl implements PagamentoService {

	@Autowired
	PagamentoRepository repository;

	@Override

	public Pagamento inclui(Pagamento pagamento) {

		pagamento.getTransacao().getDescricao().setNsu("1234567890");
		pagamento.getTransacao().getDescricao().setCodigoAutorizacao("147258369");
		pagamento.getTransacao().getDescricao().setStatus("AUTORIZADO");

		return repository.save(pagamento);
	}

	@Override
	public List<Pagamento> consulta() {
		return repository.findAll();
	}

	@Override
	public Pagamento consultaId(String id) {

		Pagamento pagamento = repository.findByTransacaoId(id);

		return pagamento;
	}

	@Override
	public Pagamento estorna(String id) {

		Pagamento pagamento = repository.findByTransacaoId(id);

		pagamento.getTransacao().getDescricao().setStatus("CANCELADO");

		return repository.save(pagamento);

	}

}
