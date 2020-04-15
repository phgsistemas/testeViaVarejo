package com.viavarejo.teste.dto;

import lombok.Data;

@Data
public class Produto {
	private Long codigo;
	private String nome;
	private double valor;
}
