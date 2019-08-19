package com.br.viavarejo.comercio.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.viavarejo.comercio.model.Produto;
import com.br.viavarejo.comercio.repository.ProdutoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/produtos")
@Api(value = "Produto")
public class ProdutoResource {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@ApiOperation(value = "Lista de produtos")
	@RequestMapping(value = "/produto", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> getProdutos() {
		List<Produto> produtos = produtoRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(produtos);
	}
	
	@ApiOperation(value = "Inclusão de produto")
	@RequestMapping(value = "/produto", method = RequestMethod.POST)
	public ResponseEntity<Void> postProduto(@RequestBody Produto produto) {
		produtoRepository.save(produto);
		return ResponseEntity.noContent().build();
	}
}
