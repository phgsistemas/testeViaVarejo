package com.viavarejo.teste.dto;

import lombok.Data;

@Data
public class Parcela {
	private int numeroParcela;
	private double valor;
	private double taxaJurosAoMes;
}
