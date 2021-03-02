package com.qualidade.qualidade;

public class Ingrediente {

	private int qtde;

	private String unidade;

	private TipoID id;

	private ItemEstoque itemEstoque;

	public Ingrediente(TipoID id, ItemEstoque item, int qtde, String unidade) {
		this.qtde = qtde;
		this.unidade = unidade;
		this.id = id;
		this.itemEstoque = item;
	}

	public int getQtde() {
		return this.qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public String getUnidade() {
		return this.unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public TipoID getID() {
		return this.id;
	}

	public ItemEstoque getItemEstoque() {
		return this.itemEstoque;
	}

	public boolean disponivelNoEstoque(int multiplica) {
		return this.itemEstoque.getQtde() >= multiplica * this.qtde;
	}

	public void diminuirItemAssociado(int multiplica) {
		this.itemEstoque.setQtde(this.itemEstoque.getQtde() - multiplica * this.qtde);
	}

	public void aumentarItemAssociado(int multiplica) {
		this.itemEstoque.setQtde(this.itemEstoque.getQtde() + multiplica * this.qtde);
	}

	public String obterInformacoes() {
		return String.format("%d %s de %s", this.qtde, this.unidade, this.itemEstoque.getNomeProduto());
	}

	public float obterCusto() {
		return this.itemEstoque.getPreco() * this.qtde;
	}
}
