package com.viavarejo.teste.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.viavarejo.teste.dto.CondicaoPagamento;
import com.viavarejo.teste.dto.PagamentoRequest;
import com.viavarejo.teste.dto.PagamentoResponse;
import com.viavarejo.teste.dto.Produto;
import com.viavarejo.teste.service.PagamentoService;

@ExtendWith(MockitoExtension.class)
class PagamentoRestTest {
	
	@InjectMocks
	PagamentoRest pagamentoRest;
	
	@Mock
	PagamentoService pagamentoService;
	
	@Test
	final void testPagamento() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        when(pagamentoService.listaParcelamento(any(PagamentoRequest.class))).thenReturn(any());
        
		Produto p = new Produto();
		p.setCodigo(1L);
		p.setValor(5000);
		p.setNome("PRODUTO TESTE");
		CondicaoPagamento cp = new CondicaoPagamento();
		cp.setQtdeParcelas(7);
		cp.setValorEntrada(350);
		PagamentoRequest pr;
		pr = new PagamentoRequest();
		pr.setProduto(p);
		pr.setCondicaoPagamento(cp);
		
		ResponseEntity<PagamentoResponse> responseEntity = pagamentoRest.pagamento(pr);
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

}
