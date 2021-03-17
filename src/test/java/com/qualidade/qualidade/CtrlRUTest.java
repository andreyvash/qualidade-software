package com.qualidade.qualidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CtrlRUTest {

	private CtrlRU ctrl;
	ImpressoraTela impressoraTela = Mockito.mock(ImpressoraTela.class);
	EscreveLeArquivo escreveLeArquivo = Mockito.mock(EscreveLeArquivo.class);

	@BeforeEach
	private void setUp()
	{
		ctrl =  new CtrlRU(new ImpressoraTela(), new EscreveLeArquivo());
	}

	@AfterEach
	private void shutDown()
	{
		ListaDeReceitas listaDeReceitas = ctrl.obterListaReceitas();
		Receita receita = listaDeReceitas.getArrayList().get(0);
		TipoID idReceita = receita.getID();
		try
		{
			ctrl.destruirReceita(idReceita);
		}
		catch(Exception e)
		{

		}
	}

	@Test
	void testCriarEBuscarReceita() {
		ctrl.criarReceita("nome", "modo preparo");
		ListaDeReceitas listaDeReceitas = ctrl.obterListaReceitas();
		Receita receita = listaDeReceitas.getArrayList().get(0);
		TipoID idReceita = receita.getID();
		assertNotNull(listaDeReceitas);
		assertNotNull(receita);
		assertNotNull(idReceita);
		Receita receitaBuscada = listaDeReceitas.obterReceita(idReceita);

		assertEquals(receita, receitaBuscada);
		assertEquals("nome", receitaBuscada.getNome());
		assertEquals("modo preparo", receitaBuscada.getModoPreparo());

	}

	@Test
	void testCadastrarDadosReceita() throws Exception{
		ctrl.criarReceita("nome", "modo preparo");
		ListaDeReceitas listaDeReceitas = ctrl.obterListaReceitas();
		Receita receita = listaDeReceitas.getArrayList().get(0);
		TipoID idReceita = receita.getID();

		ctrl.cadastrarDadosReceita(idReceita, "test", "modo preparo", 1, 200);
		Receita receitaAtualizada = listaDeReceitas.obterReceita(idReceita);

		assertEquals("test", receitaAtualizada.getNome());
		assertEquals("modo preparo", receitaAtualizada.getModoPreparo());
		assertEquals(1, receitaAtualizada.getNoPorcoes());
		assertEquals(200, receitaAtualizada.getValorCalorico());

	}
	
	@Test
	void testcadastrarDadosIngrediente() {
		assert(true);
	}

	@Test
	void testincluirReceita() {
		assert(true);
	}

	@Test
	void testobterListaReceitas() {
		assert(true);
	}

}
