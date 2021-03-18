package com.qualidade.qualidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


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
	void testCriarReceita() 
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
	void testCriarIngrediente() throws Exception{
		Receita receita = subject.obterReceita(idReceita);
		ItemEstoque itemEstoque = new ItemEstoque(new TipoID(1), "nome produto", 1, "g", 10F);
		subject.cadastrarDadosIngrediente(idReceita, itemEstoque, 1, "g");

		assertEquals(1, receita.getIngredientes().size());
	}

	@Test
	void testCriarIngrediente_deveFalharSeReceitaNaoExistir() throws Exception{
		ItemEstoque itemEstoque = new ItemEstoque(new TipoID(1), "nome produto", 1, "g", 10F);
		TipoID idReceitaQueNaoExiste = new TipoID(100);

		Exception exception = assertThrows(Exception.class, () -> {
			subject.cadastrarDadosIngrediente(idReceitaQueNaoExiste, itemEstoque, 1, "g");
		});

		assertEquals("Os dados devem ser válidos.\n receita deve existir, item deve existir e qtde >= 0", exception.getMessage());

	}

	@Test
	void testCriarIngrediente_deveFalharSeItemForNulo() throws Exception{
		ItemEstoque itemEstoque = null;

		Exception exception = assertThrows(Exception.class, () -> {
			subject.cadastrarDadosIngrediente(idReceita, itemEstoque, 1, "g");
		});

		assertEquals("Os dados devem ser válidos.\n receita deve existir, item deve existir e qtde >= 0", exception.getMessage());

	}

	@Test
	void testCriarIngrediente_deveFalharSeQuantidadeMenorQueZero() throws Exception{
		ItemEstoque itemEstoque = new ItemEstoque(new TipoID(1), "nome produto", 1, "g", 10F);

		Exception exception = assertThrows(Exception.class, () -> {
			subject.cadastrarDadosIngrediente(idReceita, itemEstoque, -1, "g");
		});

		assertEquals("Os dados devem ser válidos.\n receita deve existir, item deve existir e qtde >= 0", exception.getMessage());

	}

	@Test
	void testCadastrarDadosReceita() throws Exception{
		Receita receita = subject.obterReceita(idReceita);
		ItemEstoque itemEstoque = new ItemEstoque(new TipoID(1), "nome produto", 1, "g", 10F);
		subject.cadastrarDadosIngrediente(idReceita, itemEstoque, 1, "g");

		subject.cadastrarDadosReceita(idReceita, "nome", "modo Preparo", 1, 300);
		assertEquals("nome", receita.getNome());
		assertEquals("modo Preparo", receita.getModoPreparo());
		assertEquals(idReceita, receita.getID());
	}

	@Test
	void testCadastrarDadosReceita_deveFalharSeListaDeIngredienteForVazia() throws Exception{

		Exception exception = assertThrows(Exception.class, () -> {
			subject.cadastrarDadosReceita(idReceita, "nome", "modo Preparo", 1, 300);
		});

		assertEquals("Os dados devem ser válidos.\n Receita deve existir, ter pelo menos um ingrediente, número de porções e valor calórico >= 0", exception.getMessage());
		
	}

	@Test
	void testCadastrarDadosReceita_deveFalharSeValorCaloricoForMenorQueZero() throws Exception{

		ItemEstoque itemEstoque = new ItemEstoque(new TipoID(1), "nome produto", 1, "g", 10F);
		subject.cadastrarDadosIngrediente(idReceita, itemEstoque, 1, "g");

		Exception exception = assertThrows(Exception.class, () -> {
			subject.cadastrarDadosReceita(idReceita, "nome", "modo Preparo", 1, -300);
		});

		assertEquals("Os dados devem ser válidos.\n Receita deve existir, ter pelo menos um ingrediente, número de porções e valor calórico >= 0", exception.getMessage());
		
	}

	@Test
	void testCadastrarDadosReceita_deveFalharSeNumDePorcoesMenorQueZero() throws Exception{

		ItemEstoque itemEstoque = new ItemEstoque(new TipoID(1), "nome produto", 1, "g", 10F);
		subject.cadastrarDadosIngrediente(idReceita, itemEstoque, 1, "g");
		
		Exception exception = assertThrows(Exception.class, () -> {
			subject.cadastrarDadosReceita(idReceita, "nome", "modo Preparo", -1, 300);
		});

		assertEquals("Os dados devem ser válidos.\n Receita deve existir, ter pelo menos um ingrediente, número de porções e valor calórico >= 0", exception.getMessage());
		
	}

	@Test
	void testCadastrarDadosReceita_deveFalharSeReceitaNaoExistir() throws Exception{

		ItemEstoque itemEstoque = new ItemEstoque(new TipoID(1), "nome produto", 1, "g", 10F);
		subject.cadastrarDadosIngrediente(idReceita, itemEstoque, 1, "g");
		TipoID idReceitaQueNaoExiste = new TipoID(100);
		Exception exception = assertThrows(Exception.class, () -> {
			subject.cadastrarDadosReceita(idReceitaQueNaoExiste, "nome", "modo Preparo", -1, 300);
		});

		assertEquals("Os dados devem ser válidos.\n Receita deve existir, ter pelo menos um ingrediente, número de porções e valor calórico >= 0", exception.getMessage());
		
	}

	@Test
	void testObterReceita() throws Exception{
		Receita receita = subject.obterReceita(idReceita);

		assertNotNull(receita);
	}
	


}
