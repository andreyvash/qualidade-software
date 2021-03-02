package com.qualidade.qualidade;
import java.util.ArrayList;
import com.google.gson.*;


import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class EscreveLeArquivo implements InterfacePersistencia {

	private Estoque estoque;
	private ListaDeReceitas listaDeReceitas;
	private ListaDeRefeicoes listaDeRefeicoes;
	private Gson gson;
	private Gson gsonListaDeReceitas;
	private Gson gsonListaDeRefeicoes;

	public EscreveLeArquivo() {
		this.gson = new GsonBuilder().setPrettyPrinting().create();

		Gson tempGson = new Gson();

		GsonBuilder gsonBuilder = new GsonBuilder();
		// Mantém somente o ID do item estoque na lista de receitas
		gsonBuilder.registerTypeAdapter(ItemEstoque.class, new JsonSerializer<ItemEstoque>() {
			public JsonElement serialize(ItemEstoque src, Type typeOfSrc, JsonSerializationContext context) {
				JsonObject result = new JsonObject();
				result.add("id", tempGson.toJsonTree(src).getAsJsonObject().get("id"));
				return result;
			}
		});
		this.gsonListaDeReceitas = gsonBuilder.setPrettyPrinting().create();

		gsonBuilder = new GsonBuilder();
		// Mantém somente o ID da receita na lista de refeições
		gsonBuilder.registerTypeAdapter(Receita.class, new JsonSerializer<Receita>() {
			public JsonElement serialize(Receita src, Type typeOfSrc, JsonSerializationContext context) {
				JsonObject result = new JsonObject();
				result.add("id", tempGson.toJsonTree(src).getAsJsonObject().get("id"));
				return result;
			}

		});
		this.gsonListaDeRefeicoes = gsonBuilder.setPrettyPrinting().create();

		this.estoque = null;
		this.listaDeReceitas = null;
		this.listaDeRefeicoes = null;
	}

	public void persistirDadosListaDeReceita(ListaDeReceitas listaReceita) throws IOException {
		Writer writer = Files.newBufferedWriter(Paths.get("listaDeReceitas.json"), StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING);
		writer.write(this.gsonListaDeReceitas.toJson(listaReceita));
		writer.close();
		this.listaDeReceitas = listaReceita;
	}

	public void persistirDadosEstoque(Estoque estoque) throws IOException {
		Writer writer = Files.newBufferedWriter(Paths.get("estoque.json"), StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING);
		writer.write(this.gson.toJson(estoque));
		writer.close();
		this.estoque = estoque;
	}

	public void persistirDadosListaDeRefeicoes(ListaDeRefeicoes listaRefeicoes) throws IOException {
		Writer writer = Files.newBufferedWriter(Paths.get("listaDeRefeicoes.json"), StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING);
		writer.write(this.gsonListaDeRefeicoes.toJson(listaRefeicoes));
		writer.close();
		this.listaDeRefeicoes = listaRefeicoes;
	}

	private void carregarDados() {
		try {
			Reader reader = Files.newBufferedReader(Paths.get("estoque.json"));
			this.estoque = this.gson.fromJson(reader, Estoque.class);

			reader = Files.newBufferedReader(Paths.get("listaDeReceitas.json"));
			this.listaDeReceitas = this.gsonListaDeReceitas.fromJson(reader, ListaDeReceitas.class);
			for (Receita r : this.listaDeReceitas.getArrayList()) {
				ArrayList<Ingrediente> ingredientes = r.getIngredientes();
				r.removerIngredientes();

				for (Ingrediente i : ingredientes) {
					ItemEstoque item = this.estoque.obterItemEstoque(i.getItemEstoque().getID());
					if (item == null) {
						throw new Exception("Item inexistente");
					}
					r.adicionarIngrediente(item, i.getQtde(), i.getUnidade());
				}
			}

			reader = Files.newBufferedReader(Paths.get("listaDeRefeicoes.json"));
			this.listaDeRefeicoes = this.gsonListaDeRefeicoes.fromJson(reader, ListaDeRefeicoes.class);
			for (Refeicao refeicao : this.listaDeRefeicoes.getArrayList()) {
				ArrayList<Receita> receitas = refeicao.getReceitas();
				refeicao.removerReceitas();

				for (Receita receita : receitas) {
					Receita receitaCerta = this.listaDeReceitas.obterReceita(receita.getID());
					if (receitaCerta == null) {
						throw new Exception("Receita inexistente");
					}
					refeicao.incluirReceita(receitaCerta);
				}

			}

		} catch (Exception e) {
			// Caso tenha problema com algum arquivo, todos eles serão ignorados e limpos
			this.estoque = new Estoque();
			this.listaDeReceitas = new ListaDeReceitas();
			this.listaDeRefeicoes = new ListaDeRefeicoes();

			try {
				this.persistirDadosEstoque(this.estoque);
				this.persistirDadosListaDeReceita(this.listaDeReceitas);
				this.persistirDadosListaDeRefeicoes(this.listaDeRefeicoes);
			} catch (Exception ex) {
			}
		}
	}

	public ListaDeReceitas obterDadosListaDeReceitas() {
		if (this.listaDeReceitas == null) {
			this.carregarDados();
		}
		return this.listaDeReceitas;
	}

	public Estoque obterDadosEstoque() {
		if (this.estoque == null) {
			this.carregarDados();
		}
		return this.estoque;
	}

	public ListaDeRefeicoes obterDadosListaDeRefeicoes() {
		if (this.listaDeRefeicoes == null) {
			this.carregarDados();
		}
		return this.listaDeRefeicoes;
	}

}
