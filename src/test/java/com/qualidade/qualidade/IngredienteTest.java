package com.qualidade.qualidade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IngredienteTest {
	private Ingrediente subject;
	private ItemEstoque item;
	private TipoID id;
	
	@BeforeEach
	void setUp()
	{		
		id = new TipoID(1);
		item = new ItemEstoque(id, "produto A", 15, "mg", 15.5f);
		subject = new Ingrediente(id, item, 10, "mg");
		
	}
	
	@Test
	void deveModificarItemEstoque() 
	{
		int multiplica = 5;
		subject.aumentarItemAssociado(multiplica);
		assertEquals(65, subject.getItemEstoque().getQtde());
		subject.diminuirItemAssociado(multiplica-2);
		assertEquals(35, subject.getItemEstoque().getQtde());
	}
	
	@Test
	void deveManterNome() 
	{
		assertEquals("10 mg de produto A", subject.obterInformacoes());
	}
	
	@Test
	void deveManterCusto()
	{
		assertEquals(155f, subject.obterCusto());
	}
	
	
}
