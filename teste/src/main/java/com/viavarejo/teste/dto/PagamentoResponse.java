package com.viavarejo.teste.dto;

import java.util.List;

import lombok.Data;

@Data
public class PagamentoResponse {
	private List<Parcela> parcelas;
}
