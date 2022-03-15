package com.pagamento.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.pagamento.microservices.model.Pagamento;

@Component
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

	@Query(value = "SELECT * FROM PAGAMENTO INNER JOIN TRANSACAO ON PAGAMENTO.TRANSACAO_ID = TRANSACAO.ID WHERE PAGAMENTO.TRANSACAO_ID = ?1", nativeQuery = true)
	Pagamento findByTransacaoId(String id);

}
