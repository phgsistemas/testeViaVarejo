package com.viavarejo.teste.dto;

import lombok.Data;

@Data
public class PagamentoRequest {
	private Produto produto;
	private CondicaoPagamento condicaoPagamento;	
}
