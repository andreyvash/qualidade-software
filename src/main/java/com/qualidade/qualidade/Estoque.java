package com.qualidade.qualidade;

import java.util.ArrayList;

public class Estoque {
	private TipoID idItemEstoque;

	private ArrayList<ItemEstoque> itemEstoque;

	public Estoque() {
		this.itemEstoque = new ArrayList<ItemEstoque>();
		this.idItemEstoque = new TipoID();
	}

	public ItemEstoque obterItemEstoque(TipoID idItem) {
		for (ItemEstoque item : this.itemEstoque) {
			if (item.getID().igual(idItem)) {
				return item;
			}
		}
		return null;
	}

	private TipoID criarNovoIDItem() {
		this.idItemEstoque.set(this.idItemEstoque.get() + 1);
		return this.idItemEstoque.copiar();
	}

	public void cadastrarDadosItem(String nomeProduto, int qtde, String unidade, float preco) throws Exception {
		if (qtde < 0 || preco < 0.0f) {
			throw new Exception("Os dados devem ser válidos.\nqtde >= 0 e preco >= 0.0.");
		}
		TipoID id = this.criarNovoIDItem();
		ItemEstoque i = new ItemEstoque(id, nomeProduto, qtde, unidade, preco);
		this.itemEstoque.add(i);
	}

	public void cadastrarAtualizacaoItem(TipoID idItem, int qtde) throws Exception {
		ItemEstoque item = this.obterItemEstoque(idItem);
		if (item == null || qtde < 0) {
			throw new Exception("Os dados devem ser válidos.\n item deve existir e qtde >= 0");
		}
		item.setQtde(qtde);
	}

	public ArrayList<ItemEstoque> getArrayList() {
		return this.itemEstoque;
	}

}
