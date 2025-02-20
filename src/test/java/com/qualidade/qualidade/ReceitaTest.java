package com.qualidade.qualidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReceitaTest {
	
	private TipoID id;
	private ItemEstoque item;
	private Receita rec; 

	
	@Test
	void testAdicionarNovoIngrediente() 
	{
		id = new TipoID();
		rec = new Receita(id, "receita_a", "modo");
		item = new ItemEstoque(id, "produto_a", 10, "mg", 15f);

		assertTrue(rec.getIngredientes().isEmpty());
		rec.adicionarIngrediente(item, 13, "kf");		
		assertFalse(rec.getIngredientes().isEmpty());
		assertEquals(1, rec.getIngredientes().remove(0).getID().get());
	}
	
	
}
