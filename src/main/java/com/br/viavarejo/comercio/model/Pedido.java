package com.br.viavarejo.comercio.model;

import java.math.BigDecimal;
import java.util.List;

public class Pedido {

	private Produto produto;
	
	private Integer qtdeParcelas;
	
	private List<BigDecimal> parcelas;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQtdeParcelas() {
		return qtdeParcelas;
	}

	public void setQtdeParcelas(Integer qtdeParcelas) {
		this.qtdeParcelas = qtdeParcelas;
	}

	public List<BigDecimal> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<BigDecimal> parcelas) {
		this.parcelas = parcelas;
	}
}
