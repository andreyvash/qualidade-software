package com.qualidade.qualidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		Estoque estoque = new Estoque();
		
		try
		{
			ctrl.destruirReceita(idReceita);
			estoque.cadastrarDadosItem("nomeProduto", 1, "g", 200F);
			
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
	void testCadastrarDadosIngredienteECadastrarDadosReceita() throws Exception{
		ctrl.criarReceita("nome", "modo preparo");
		ListaDeReceitas listaDeReceitas = ctrl.obterListaReceitas();
		Receita receita = listaDeReceitas.getArrayList().get(0);
		TipoID idReceita = receita.getID();

		
		ctrl.cadastrarDadosItem("nomeProduto", 10, "g", 200F);
		Estoque estoque = ctrl.obterListaItemEstoque();
		TipoID idItem = estoque.getArrayList().get(0).getID();

		ctrl.cadastrarDadosIngrediente(idReceita, idItem, 1, "g");


		ctrl.cadastrarDadosReceita(idReceita, "test", "modo preparo", 1, 200);

		assertEquals("test", receita.getNome());
		assertEquals("modo preparo", receita.getModoPreparo());
		assertFalse(receita.getIngredientes().isEmpty());
		assertEquals(1 ,receita.getIngredientes().size());


	}
	

}
