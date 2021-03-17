package com.qualidade.qualidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReceitaTest {
	
	private TipoID id;
	private ItemEstoque item;
	private Receita rec; 

	@BeforeEach
	void setUp() 
	{
		id = new TipoID();
		rec = new Receita(id, "receita_a", "modo");
		item = new ItemEstoque(id, "produto_a", 10, "mg", 15f);
	}
	
	@Test
	void deveAdicionarNovoIngrediente() 
	{
		assertTrue(rec.getIngredientes().isEmpty());
		rec.adicionarIngrediente(item, 13, "kf");		
		assertFalse(rec.getIngredientes().isEmpty());
		assertEquals(1, rec.getIngredientes().remove(0).getID().get());
	}
	
	@Test
	void deveRemoverIngrediente() 
	{
		assertTrue(rec.getIngredientes().isEmpty());
		rec.adicionarIngrediente(item, 43, "kg");
		rec.adicionarIngrediente(item, 231, "mg");
		rec.removerIngredientes();
		assertTrue(rec.getIngredientes().isEmpty());
	}
	
	@Test
	void deveManterEstoqueSemIngrediente() 
	{
		rec.atualizarEstoque("adicionar", 100);
		assertTrue(rec.ingredientesDisponiveisNoEstoque(99));
		assertTrue(rec.ingredientesDisponiveisNoEstoque(1));
		assertFalse(rec.ingredientesDisponiveisNoEstoque(125));		
	}
	
	@Test
	void deveManterEstoqueComIngrediente()
	{
		rec.adicionarIngrediente(item, 10, "mg");
		rec.atualizarEstoque("adicionar", 100);
		assertTrue(rec.ingredientesDisponiveisNoEstoque(99));
		assertTrue(rec.ingredientesDisponiveisNoEstoque(1));
		assertFalse(rec.ingredientesDisponiveisNoEstoque(105));	
	}
	
	@Test
	void deveCalcularCusto()
	{
		rec.adicionarIngrediente(item, 10, "kg");	
		assertEquals(2500 ,rec.obterCusto(25));
		rec.atualizarEstoque("adicionar", 100);
		assertEquals(27500 ,rec.obterCusto(25));
		
	}
	
	
}
