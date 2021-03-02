package com.qualidade.qualidade;

import java.util.ArrayList;

public class Refeicao {

	private int noRefeicoes;

	private float custoTotal;

	private int valorCalorico;

	private TipoID id;

	private ArrayList<Receita> receitas;

	public Refeicao(TipoID id, int noRefeicoes) {
		this.id = id;
		this.noRefeicoes = noRefeicoes;
		this.custoTotal = 0;
		this.receitas = new ArrayList<Receita>();
	}

	private int calculaValorCaloricoTotal() {
		int valor = 0;
		for (Receita r : this.receitas) {
			valor += r.getValorCalorico();
		}
		return valor;
	}

	private float calculaCustoTotal() {
		float custo = 0.0f;
		for (Receita r : this.receitas) {
			custo += r.obterCusto(this.noRefeicoes);
		}
		return custo;
	}

	public void atualizar(int noRefeicoes) {
		this.setNoRefeicoes(noRefeicoes);
		this.custoTotal = this.calculaCustoTotal();
		this.valorCalorico = this.calculaValorCaloricoTotal();
	}

	public void incluirReceita(Receita receita) {
		this.receitas.add(receita);
	}

	public void removerReceita(Receita receita) throws Exception {
		if (!this.receitas.remove(receita)) {
			throw new Exception("Erro.\n Ocorreu um erro ao remover da lista.");
		}
	}

	public void removerReceitas() {
		this.receitas = new ArrayList<Receita>();
	}

	public Pedido gerarPedido() {
		return new Pedido(this);
	}

	public int getNoRefeicoes() {
		return this.noRefeicoes;
	}

	public void setNoRefeicoes(int noRefeicoes) {
		this.noRefeicoes = noRefeicoes;
	}

	public float getCustoTotal() {
		return this.custoTotal;
	}

	public int getValorCalorico() {
		return this.valorCalorico;
	}

	public TipoID getID() {
		return this.id;
	}

	public ArrayList<Receita> getReceitas() {
		return this.receitas;
	}

	public String obterInformacoes() {
		String infoRefeicao = "Número de Refeições: " + this.noRefeicoes + "\n" + "Custo Total: "
				+ this.calculaCustoTotal() + "\n" + "Valor Calórico da Refeição: " + this.calculaValorCaloricoTotal()
				+ "\n";

		ArrayList<String> arrInfoReceitas = new ArrayList<String>();
		for (Receita r : this.receitas) {
			arrInfoReceitas.add(r.obterInformacoes(this.noRefeicoes));
		}

		String infoReceitas = String.join("\n", arrInfoReceitas);

		return infoRefeicao + "\n" + infoReceitas;
	}
}
