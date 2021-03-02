package com.qualidade.qualidade;

import java.io.IOException;

public interface InterfacePersistencia {

	public void persistirDadosListaDeReceita(ListaDeReceitas listaReceita) throws IOException;

	public void persistirDadosEstoque(Estoque estoque) throws IOException;

	public void persistirDadosListaDeRefeicoes(ListaDeRefeicoes listaRefeicoes) throws IOException;

	public ListaDeReceitas obterDadosListaDeReceitas();

	public Estoque obterDadosEstoque();

	public ListaDeRefeicoes obterDadosListaDeRefeicoes();
}
