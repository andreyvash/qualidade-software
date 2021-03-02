package com.qualidade.qualidade;

public class ItemEstoque {

	private String nomeProduto;

	private String unidade;

	private int qtde;

	private float preco;

	private TipoID id;

	public ItemEstoque(TipoID id, String nomeProduto, int qtde, String unidade, float preco) {
		this.id = id;
		this.nomeProduto = nomeProduto;
		this.qtde = qtde;
		this.unidade = unidade;
		this.preco = preco;
	}

	public String getNomeProduto() {
		return this.nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getUnidade() {
		return this.unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public int getQtde() {
		return this.qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public float getPreco() {
		return this.preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public TipoID getID() {
		return this.id;
	}
}
