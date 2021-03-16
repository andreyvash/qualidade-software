package com.qualidade.qualidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

@SpringBootTest
class ListaDeReceitaTest {

	private ListaDeReceitas subject;
	private TipoID idReceita; 

	@BeforeEach
	void setUp()
	{
		subject = new ListaDeReceitas();
		idReceita = subject.criarReceita("nome receita", "modo preparo");
	}

	@Test
	void deveCriarReceita() 
	{

		Receita receita = subject.obterReceita(idReceita);
		List<Receita> receitas = subject.getArrayList();

		assertEquals("nome receita", receita.getNome());
		assertEquals("modo preparo", receita.getModoPreparo());
		assertEquals(idReceita, receita.getID());
		assertTrue(receitas.contains(receita));
		assertEquals(0, receita.getIngredientes().size());
	}

	@Test
	void deveCriarIngrediente() throws Exception{
		Receita receita = subject.obterReceita(idReceita);
		ItemEstoque itemEstoque = new ItemEstoque(new TipoID(1), "nome produto", 1, "g", 10F);
		subject.cadastrarDadosIngrediente(idReceita, itemEstoque, 1, "g");

		assertEquals(1, receita.getIngredientes().size());
	}

	@Test
	void deveCadastrarDadosReceita() throws Exception{
		Receita receita = subject.obterReceita(idReceita);
		ItemEstoque itemEstoque = new ItemEstoque(new TipoID(1), "nome produto", 1, "g", 10F);
		subject.cadastrarDadosIngrediente(idReceita, itemEstoque, 1, "g");

		subject.cadastrarDadosReceita(idReceita, "nome", "modo Preparo", 1, 300);
		assertEquals("nome", receita.getNome());
		assertEquals("modo Preparo", receita.getModoPreparo());
		assertEquals(idReceita, receita.getID());
	}

	@Test
	void deveObterReceita() throws Exception{
		Receita receita = subject.obterReceita(idReceita);

		assertNotNull(receita);
	}
	


}
