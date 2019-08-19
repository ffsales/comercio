package com.br.viavarejo.comercio.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.viavarejo.comercio.model.Pedido;
import com.br.viavarejo.comercio.pedido.PedidoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/pedidos")
@Api(value = "Pedido")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;
	
	@ApiOperation(value = "Como fazer pedidos")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Pedido feito com sucesso"),
		    @ApiResponse(code = 404, message = "Produto não encontrado") }
	)
	@RequestMapping(value = "/produto", method = RequestMethod.GET)
	public ResponseEntity<Pedido> getPedido(
			@RequestParam(value = "id-produto", required = true) Long idProduto, 
			@RequestParam(value = "qtde-parcelas", required = true) Integer qtdeParcelas) throws Exception {
		
		Pedido pedido = this.pedidoService.buildPedido(idProduto, qtdeParcelas);
		
		return ResponseEntity.status(HttpStatus.OK).body(pedido);
	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
}	
