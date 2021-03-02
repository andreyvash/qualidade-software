package com.qualidade.qualidade;

public class CtrlRU {

	private ListaDeReceitas listaDeReceitas;

	private Estoque estoque;

	private ListaDeRefeicoes listaDeRefeicoes;

	private InterfaceImpressao impressao;

	private InterfacePersistencia persistencia;

	public CtrlRU(InterfaceImpressao interfaceImpressao, InterfacePersistencia interfacePersistencia) {
		this.impressao = interfaceImpressao;
		this.persistencia = interfacePersistencia;

		this.listaDeReceitas = this.persistencia.obterDadosListaDeReceitas();
		this.estoque = this.persistencia.obterDadosEstoque();
		this.listaDeRefeicoes = this.persistencia.obterDadosListaDeRefeicoes();
	}

	public TipoID criarReceita(String nome, String modoPreparo) {
		return this.listaDeReceitas.criarReceita(nome, modoPreparo);
	}

	// Usado no cancelamento da criação da receita
	public void destruirReceita(TipoID idReceita) throws Exception {
		this.listaDeReceitas.destruirReceita(idReceita);
	}

	public void cadastrarDadosReceita(TipoID idReceita, String nome, String modoPreparo, int noPorcoes,
			int valorCalorico) throws Exception {
		this.listaDeReceitas.cadastrarDadosReceita(idReceita, nome, modoPreparo, noPorcoes, valorCalorico);
		this.persistencia.persistirDadosListaDeReceita(this.listaDeReceitas);
	}

	public Estoque obterListaItemEstoque() {
		return this.estoque;
	}

	public void cadastrarDadosIngrediente(TipoID idReceita, TipoID idItem, int qtde, String unidade) throws Exception {
		ItemEstoque item = this.estoque.obterItemEstoque(idItem);
		this.listaDeReceitas.cadastrarDadosIngrediente(idReceita, item, qtde, unidade);
	}

	public void cadastrarAtualizacaoItem(TipoID idItem, int qtde) throws Exception {
		this.estoque.cadastrarAtualizacaoItem(idItem, qtde);
		this.persistencia.persistirDadosEstoque(this.estoque);
	}

	public void cadastrarDadosItem(String nomeProduto, int qtde, String unidade, float preco) throws Exception {
		this.estoque.cadastrarDadosItem(nomeProduto, qtde, unidade, preco);
		this.persistencia.persistirDadosEstoque(this.estoque);
	}

	public TipoID criarRefeicao(int noRefeicoes) {
		return this.listaDeRefeicoes.criarRefeicao(noRefeicoes);
	}

	// Usado no cancelamento da criação da refeição
	public void destruirRefeicao(TipoID idRefeicao) throws Exception {
		this.listaDeRefeicoes.destruirRefeicao(idRefeicao);
	}

	public ListaDeReceitas obterListaReceitas() {
		return this.listaDeReceitas;
	}

	public void incluirReceita(TipoID idRefeicao, TipoID idReceita) throws Exception {
		Receita receita = this.listaDeReceitas.obterReceita(idReceita);
		this.listaDeRefeicoes.incluirReceita(idRefeicao, receita);
	}

	public void removerReceita(TipoID idRefeicao, TipoID idReceita) throws Exception {
		Receita receita = this.listaDeReceitas.obterReceita(idReceita);
		this.listaDeRefeicoes.removerReceita(idRefeicao, receita);
	}

	public void cadastrarDadosRefeicao(TipoID idRefeicao, int noRefeicoes) throws Exception {
		this.listaDeRefeicoes.cadastrarDadosRefeicao(idRefeicao, noRefeicoes);
		Pedido pedido = listaDeRefeicoes.gerarPedido(idRefeicao);
		this.impressao.imprimirPedido(pedido);

		this.persistencia.persistirDadosListaDeRefeicoes(this.listaDeRefeicoes);
		this.persistencia.persistirDadosEstoque(this.estoque);
	}

	public ListaDeRefeicoes obterListaRefeicoes() {
		return this.listaDeRefeicoes;
	}

}
