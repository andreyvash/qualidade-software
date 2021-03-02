package com.qualidade.qualidade;

import java.util.ArrayList;

public class ListaDeReceitas {

	private ArrayList<Receita> receitas;

	private TipoID idReceita;

	public ListaDeReceitas() {
		this.receitas = new ArrayList<Receita>();
		this.idReceita = new TipoID();
	}

	public Receita obterReceita(TipoID idReceita) {
		for (Receita r : this.receitas) {
			if (r.getID().igual(idReceita)) {
				return r;
			}
		}
		return null;
	}

	public ArrayList<Receita> getArrayList() {
		return this.receitas;
	}

	private TipoID criarNovoIDReceita() {
		this.idReceita.set(this.idReceita.get() + 1);
		return this.idReceita.copiar();
	}

	public TipoID criarReceita(String nome, String modoPreparo) {
		TipoID id = this.criarNovoIDReceita();
		Receita r = new Receita(id, nome, modoPreparo);
		this.receitas.add(r);
		return id;
	}

	public void destruirReceita(TipoID idReceita) throws Exception {
		Receita r = this.obterReceita(idReceita);
		if (r == null) {
			throw new Exception("Erro.\n Receita não existe");
		}

		r.removerIngredientes();

		if (!this.receitas.remove(r)) {
			throw new Exception("Erro.\n Ocorreu um erro ao remover da lista.");
		}
	}

	public void cadastrarDadosIngrediente(TipoID idReceita, ItemEstoque item, int qtde, String unidade)
			throws Exception {

		Receita r = this.obterReceita(idReceita);
		if (r == null || item == null || qtde < 0) {
			throw new Exception("Os dados devem ser válidos.\n receita deve existir, item deve existir e qtde >= 0");
		}
		r.adicionarIngrediente(item, qtde, unidade);
	}

	public void cadastrarDadosReceita(TipoID idReceita, String nome, String modoPreparo, int noPorcoes,
			int valorCalorico) throws Exception {
		Receita receita = obterReceita(idReceita);
		if (receita == null || (receita.getIngredientes().size()) <= 0 || noPorcoes < 0 || valorCalorico < 0) {
			throw new Exception(
					"Os dados devem ser válidos.\n Receita deve existir, ter pelo menos um ingrediente, número de porções e valor calórico >= 0");
		}
		receita.atualizar(nome, modoPreparo, noPorcoes, valorCalorico);
	}
}
