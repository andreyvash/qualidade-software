package com.qualidade.qualidade;

import java.util.ArrayList;

public class Receita {

	private String nome;

	private String modoPreparo;

	private int noPorcoes;

	private int valorCalorico;

	private TipoID id;

	private ArrayList<Ingrediente> ingredientes;

	private TipoID idIngrediente;

	public Receita(TipoID id, String nome, String modoPreparo) {
		this.id = id;
		this.nome = nome;
		this.modoPreparo = modoPreparo;
		this.noPorcoes = 0;
		this.valorCalorico = 0;

		this.ingredientes = new ArrayList<Ingrediente>();
		this.idIngrediente = new TipoID();
	}

	public void atualizar(String nome, String modoPreparo, int noPorcoes, int valorCalorico) {
		this.nome = nome;
		this.modoPreparo = modoPreparo;
		this.noPorcoes = noPorcoes;
		this.valorCalorico = valorCalorico;
	}

	private TipoID criarNovoIDIngrediente() {
		this.idIngrediente.set(this.idIngrediente.get() + 1);
		return this.idIngrediente.copiar();
	}

	public void adicionarIngrediente(ItemEstoque item, int qtde, String unidade) {
		TipoID id = this.criarNovoIDIngrediente();
		Ingrediente i = new Ingrediente(id, item, qtde, unidade);
		this.ingredientes.add(i);
	}

	public void removerIngredientes() {
		this.idIngrediente = new TipoID();
		this.ingredientes = new ArrayList<Ingrediente>();
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getModoPreparo() {
		return this.modoPreparo;
	}

	public void setModoPreparo(String modoPreparo) {
		this.modoPreparo = modoPreparo;
	}

	public int getNoPorcoes() {
		return this.noPorcoes;
	}

	public void setNoPorcoes(int noPorcoes) {
		this.noPorcoes = noPorcoes;
	}

	public int getValorCalorico() {
		return this.valorCalorico;
	}

	public void setValorCalorico(int valorCalorico) {
		this.valorCalorico = valorCalorico;
	}

	public TipoID getID() {
		return this.id;
	}

	public ArrayList<Ingrediente> getIngredientes() {
		return this.ingredientes;
	}

	public boolean ingredientesDisponiveisNoEstoque(int noRefeicoes) {
		int multiplica = (int) Math.ceil(1.0f * noRefeicoes / this.noPorcoes);
		for (Ingrediente i : this.ingredientes) {
			if (!i.disponivelNoEstoque(multiplica)) {
				return false;
			}
		}

		return true;
	}

	public void atualizarEstoque(String operacao, int noRefeicoes) {
		int multiplica = (int) Math.ceil(1.0f * noRefeicoes / this.noPorcoes);
		for (Ingrediente i : this.ingredientes) {
			if (operacao == "remover") {
				i.diminuirItemAssociado(multiplica);
			} else if (operacao == "adicionar") {
				i.aumentarItemAssociado(multiplica);
			}
		}
	}

	public String obterInformacoes(int noRefeicoes) {
		String titulo = String.format(" ** Receita: %s ** \n", this.nome);
		String porcoesECalorias = "Número de Porções: " + this.noPorcoes + "\n" + "Valor Calórico: "
				+ this.valorCalorico + "\n";

		ArrayList<String> arrInfoIngredientes = new ArrayList<String>();
		for (Ingrediente i : this.ingredientes) {
			arrInfoIngredientes.add(i.obterInformacoes());
		}

		int multiplica = (int) Math.ceil(1.0f * noRefeicoes / this.noPorcoes);
		String strMultiplica = "" + multiplica + " * ";
		String infoIngredientes = "Ingredientes: \n - " + strMultiplica
				+ String.join("\n - " + strMultiplica, arrInfoIngredientes) + "\n";

		String infoModoPreparo = "Modo de Preparo: \n" + this.modoPreparo + "\n";

		return titulo + porcoesECalorias + infoIngredientes + infoModoPreparo;
	}

	public float obterCusto(int noRefeicoes) {
		int multiplica = (int) Math.ceil(1.0f * noRefeicoes / this.noPorcoes);
		float custo = 0.0f;
		for (Ingrediente i : this.ingredientes) {
			custo += i.obterCusto();
		}
		return custo * multiplica;
	}
}
