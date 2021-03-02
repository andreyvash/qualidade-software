package com.qualidade.qualidade;

public class Pedido {

	private Refeicao refeicao;

	public Pedido(Refeicao refeicao) {
		this.refeicao = refeicao;
	}

	public String obterInformacoes() {
		String linhaInicio = "== == == Pedido == == == ==\n";
		String linhaFim = "== == == == == == == == ==\n";
		return linhaInicio + this.refeicao.obterInformacoes() + linhaFim;
	}
}