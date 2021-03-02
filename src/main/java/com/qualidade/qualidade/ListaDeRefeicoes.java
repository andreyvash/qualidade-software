package com.qualidade.qualidade;

import java.util.ArrayList;

public class ListaDeRefeicoes {

	private ArrayList<Refeicao> refeicoes;
	private TipoID idRefeicao;

	public ListaDeRefeicoes() {
		this.refeicoes = new ArrayList<Refeicao>();
		this.idRefeicao = new TipoID();
	}

	private TipoID criarNovoIDRefeicao() {
		this.idRefeicao.set(this.idRefeicao.get() + 1);
		return this.idRefeicao.copiar();
	}

	public TipoID criarRefeicao(int noRefeicoes) {
		TipoID id = this.criarNovoIDRefeicao();
		Refeicao r = new Refeicao(id, noRefeicoes);
		this.refeicoes.add(r);
		return id;
	}

	public void destruirRefeicao(TipoID idRefeicao) throws Exception {
		Refeicao r = this.obterRefeicao(idRefeicao);
		if (r == null) {
			throw new Exception("Erro.\n Refeição não existe");
		}

		for (Receita receita : r.getReceitas()) {
			receita.atualizarEstoque("adicionar", r.getNoRefeicoes());
		}
		r.removerReceitas();

		if (!this.refeicoes.remove(r)) {
			throw new Exception("Erro.\n Ocorreu um erro ao remover da lista.");
		}
	}

	public Refeicao obterRefeicao(TipoID idRefeicao) {
		for (Refeicao r : this.refeicoes) {
			if (r.getID().igual(idRefeicao)) {
				return r;
			}
		}
		return null;
	}

	public void cadastrarDadosRefeicao(TipoID idRefeicao, int noRefeicoes) throws Exception {
		Refeicao refeicao = obterRefeicao(idRefeicao);
		if (refeicao == null || (refeicao.getReceitas().size() <= 0) || noRefeicoes < 0) {
			throw new Exception(
					"Os dados devem ser válidos.\n refeição deve existir e ter pelo menos uma receita e noRefeicoes deve ser <= 0");
		}
		refeicao.atualizar(noRefeicoes);
	}

	public Pedido gerarPedido(TipoID idRefeicao) throws Exception {
		Refeicao refeicao = this.obterRefeicao(idRefeicao);
		if (refeicao == null) {
			throw new Exception("Os dados devem ser válidos.\n refeição deve existir");
		}
		return refeicao.gerarPedido();
	}

	public void incluirReceita(TipoID idRefeicao, Receita receita) throws Exception {
		Refeicao refeicao = this.obterRefeicao(idRefeicao);
		if (refeicao == null || receita == null) {
			throw new Exception("Os dados devem ser válidos.\n refeição deve existir e receita deve existir");
		}
		if (refeicao.getReceitas().contains(receita)) {
			throw new Exception(String.format("A receita '%s' já está na refeição", receita.getNome()));
		}
		if (!receita.ingredientesDisponiveisNoEstoque(refeicao.getNoRefeicoes())) {
			throw new Exception(String.format(
					"Não existem itens no estoque para fazer a receita '%s' com a quantidade de refeições especificada.",
					receita.getNome()));
		}
		receita.atualizarEstoque("remover", refeicao.getNoRefeicoes());

		refeicao.incluirReceita(receita);
	}

	public void removerReceita(TipoID idRefeicao, Receita receita) throws Exception {
		Refeicao refeicao = this.obterRefeicao(idRefeicao);
		if (refeicao == null || receita == null) {
			throw new Exception("Os dados devem ser válidos.\n refeição deve existir e receita deve existir");
		}

		receita.atualizarEstoque("adicionar", refeicao.getNoRefeicoes());

		refeicao.removerReceita(receita);
	}

	public ArrayList<Refeicao> getArrayList() {
		return this.refeicoes;
	}
}
