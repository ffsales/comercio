package com.br.viavarejo.comercio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.viavarejo.comercio.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
