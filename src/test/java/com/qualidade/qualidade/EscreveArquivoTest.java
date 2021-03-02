package com.qualidade.qualidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EscreveArquivoTest {

	@Test
	void obterItemEstoque_deveRetornarItemEstoque_quandoBuscaPorTipoID() {
		EscreveLeArquivo escreveLeArquivo = new EscreveLeArquivo();
		Estoque estoque = escreveLeArquivo.obterDadosEstoque();
		List<ItemEstoque> itemEstoques = estoque.getArrayList();

		assert(!itemEstoques.isEmpty());
		ItemEstoque itemEstoque = estoque.obterItemEstoque(itemEstoques.get(0).getID());
		
		
		assertEquals(itemEstoque.getID(), itemEstoques.get(0).getID());
	}

}
