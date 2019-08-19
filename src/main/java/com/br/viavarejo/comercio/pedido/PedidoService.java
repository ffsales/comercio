package com.br.viavarejo.comercio.pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.br.viavarejo.comercio.model.Pedido;
import com.br.viavarejo.comercio.model.Produto;
import com.br.viavarejo.comercio.repository.ProdutoRepository;

@Service
public class PedidoService {

	@Value("${taxa.selic}")
	private BigDecimal selic;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Pedido buildPedido(Long idProduto, Integer parcelas) {
		Optional<Produto> optionalProduto = produtoRepository.findById(idProduto);
		Pedido pedido = new Pedido();
		if (optionalProduto.isPresent()) {
			pedido.setProduto(optionalProduto.get());
			pedido.setQtdeParcelas(parcelas);
			pedido.setParcelas(this.calculaParcelas(optionalProduto.get().getValor(), parcelas));
		} else {
			throw new IllegalArgumentException("Produto não encontrado");
		}
		return pedido;
	}
	
	private List<BigDecimal> calculaParcelas(BigDecimal valor, Integer qtdeParcelas) {
		List<BigDecimal> parcelas = new ArrayList<>();
		for (int i=0; i < qtdeParcelas; i++) {
			if (qtdeParcelas <= 6) {
				parcelas.add(valor.divide(new BigDecimal(qtdeParcelas), RoundingMode.HALF_UP));
			} else {
				parcelas.add(valor.divide(new BigDecimal(qtdeParcelas), RoundingMode.HALF_UP).multiply(selic));
			}
		}
		return parcelas;
	}
}
