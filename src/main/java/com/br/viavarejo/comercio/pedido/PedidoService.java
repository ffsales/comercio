package com.br.viavarejo.comercio.pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.br.viavarejo.comercio.model.Pedido;
import com.br.viavarejo.comercio.model.Produto;
import com.br.viavarejo.comercio.model.SelicDto;
import com.br.viavarejo.comercio.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PedidoService {

	@Value("${url.selic}")
	private String urlSelic;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public Pedido buildPedido(Long idProduto, Integer parcelas) throws Exception {
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
	
	private List<BigDecimal> calculaParcelas(BigDecimal valor, Integer qtdeParcelas) throws Exception {
		List<BigDecimal> parcelas = new ArrayList<>();
		
		JsonNode taxasNode = restTemplate.getForObject(urlSelic, JsonNode.class);
		ObjectMapper mapper = new ObjectMapper();
		String jsonArray = mapper.writeValueAsString(taxasNode);
		SelicDto[] selicList = mapper.readValue(jsonArray, SelicDto[].class);
		
		BigDecimal taxa = BigDecimal.ZERO;
		
		for (SelicDto selic : selicList) {
			taxa = taxa.add(selic.getValor());
		}
		
		for (int i=0; i < qtdeParcelas; i++) {
			if (qtdeParcelas <= 6) {
				parcelas.add(valor.divide(new BigDecimal(qtdeParcelas), RoundingMode.HALF_UP));
			} else {
				parcelas.add(valor.divide(new BigDecimal(qtdeParcelas), RoundingMode.HALF_UP).multiply(BigDecimal.ONE.add(taxa)));
			}
		}
		return parcelas;
	}
}
