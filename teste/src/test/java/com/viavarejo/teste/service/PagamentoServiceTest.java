package com.viavarejo.teste.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

	@InjectMocks
	PagamentoService pagamentoService;
	
	@Mock
	Environment env;
	
	@Test
	void testCalculaValorRealParcelar() {
		
		Double valorFinal = null;
		
		valorFinal = pagamentoService.calculaValorRealParcelar(7000.0, 10, 1.5);
		
		assertThat(valorFinal != null);
	}
}
