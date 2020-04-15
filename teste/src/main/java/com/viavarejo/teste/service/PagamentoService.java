package com.viavarejo.teste.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.viavarejo.teste.dto.PagamentoRequest;
import com.viavarejo.teste.dto.Parcela;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PagamentoService {
	
	@Autowired
	Environment env;

	public List<Parcela> listaParcelamento(PagamentoRequest pagamento) {
		log.info("Iniciando serviço listaParcelamento");
		List<Parcela> parcelas = new ArrayList<Parcela>();
		Double valorParcelar = pagamento.getProduto().getValor() - pagamento.getCondicaoPagamento().getValorEntrada();
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		Double somaParcelas = 0.0;
		if(pagamento.getCondicaoPagamento().getQtdeParcelas() > Integer.valueOf(env.getProperty("qtd.max.parcela.sem.juros"))){
			Double taxaAtual = consultaTaxaSelicAtual();
			Double valorRealParcelar = calculaValorRealParcelar(valorParcelar, pagamento.getCondicaoPagamento().getQtdeParcelas(), taxaAtual);
			Double valorParcela = Double.valueOf(df.format(valorRealParcelar/pagamento.getCondicaoPagamento().getQtdeParcelas()).replace(",", "."));
			int i = 0;
			while(i < pagamento.getCondicaoPagamento().getQtdeParcelas()) {
				Parcela p = new Parcela();
				p.setNumeroParcela(++i);
				p.setValor(valorParcela);
				p.setTaxaJurosAoMes(taxaAtual*100);
				somaParcelas = somaParcelas+valorParcela;
				if(i == pagamento.getCondicaoPagamento().getQtdeParcelas() && somaParcelas < valorRealParcelar) {
					p.setValor(p.getValor()+(valorRealParcelar-somaParcelas));
					p.setValor(Double.valueOf(df.format(p.getValor()).replace(",", ".")));
				}
				parcelas.add(p);
			}
		}else {
			if(pagamento.getCondicaoPagamento().getQtdeParcelas() == 0) {
				return parcelas;
			}
			Double valorParcela = Double.valueOf(df.format(valorParcelar/pagamento.getCondicaoPagamento().getQtdeParcelas()).replace(",", "."));
			int i = 0;
			while(i < pagamento.getCondicaoPagamento().getQtdeParcelas()) {
				Parcela p = new Parcela();
				p.setNumeroParcela(++i);
				p.setValor(valorParcela);
				somaParcelas = somaParcelas+valorParcela;
				if(i == pagamento.getCondicaoPagamento().getQtdeParcelas() && somaParcelas < valorParcelar) {
					p.setValor(p.getValor()+(valorParcelar-somaParcelas));
					p.setValor(Double.valueOf(df.format(p.getValor()).replace(",", ".")));
				}
				parcelas.add(p);
			}
		}
		return parcelas;
	}
	
	public Double calculaValorRealParcelar(Double valorParcelar, int meses, Double taxa) {
		log.info("Iniciando serviço de calculo de valor real a parcelar");
		for(int i=1; i<=meses; ++i) {
			valorParcelar = (valorParcelar * (taxa))+valorParcelar;
		}
		return valorParcelar;
	}
	
	public Double consultaTaxaSelicAtual() {
		log.info("Consultando taxa selic atual");
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(env.getProperty("uri.taxa.selic"), String.class);
		JSONArray lista = new JSONArray(result);
		JSONObject objeto = new JSONObject(lista.get(0).toString());
		return objeto.getDouble("valor");
		
	}
	
}
