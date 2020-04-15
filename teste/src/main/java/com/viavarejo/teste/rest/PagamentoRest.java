package com.viavarejo.teste.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viavarejo.teste.dto.PagamentoRequest;
import com.viavarejo.teste.dto.PagamentoResponse;
import com.viavarejo.teste.service.PagamentoService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("pagamento/")
public class PagamentoRest {

	@Autowired
	PagamentoService pagamentoService;
	
	@ApiOperation(value="Endpoint para simulação de compra de produto, onde retorla a lista de parcelas")
	@PostMapping("v1/condicoes")
	public ResponseEntity<PagamentoResponse> pagamento(@RequestBody(required=true) PagamentoRequest pagamentoRequest) {
		log.info("Consultando condições - "+pagamentoRequest.toString());
		PagamentoResponse response = new PagamentoResponse();
		try {
			response.setParcelas(pagamentoService.listaParcelamento(pagamentoRequest));
			
			log.info("RETORNO - "+response.toString());
			
			return new ResponseEntity<PagamentoResponse>(response,HttpStatus.OK); 
		}catch (Exception e) {
			return new ResponseEntity<PagamentoResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
