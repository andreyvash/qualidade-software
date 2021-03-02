package com.qualidade.qualidade;

import java.util.ArrayList;
import java.util.Scanner;

public class TelaUsuario {

	private CtrlRU ctrlRU;

	public TelaUsuario(CtrlRU ctrlRU) {
		this.ctrlRU = ctrlRU;
	}

	private String obterString(Scanner scanner, String mensagem) {
		System.out.println(mensagem);
		String valor = scanner.next();
		while ("".equals(valor)) {
			System.out.println(mensagem + " (não pode ser vazio)");
			valor = scanner.next();
		}
		return valor;
	}

	private int obterInt(Scanner scanner, String mensagem) {
		System.out.println(mensagem);
		int valor;

		while (true) {
			while (!scanner.hasNextInt()) {
				System.out.println(mensagem + "\n(Digite um inteiro não negativo)");
				scanner.next();
			}
			valor = scanner.nextInt();
			if (valor < 0) {
				System.out.println(mensagem + "\n(Digite um inteiro não negativo)");
			} else {
				break;
			}
		}

		return valor;
	}

	private float obterFloat(Scanner scanner, String mensagem) {
		System.out.println(mensagem);
		float valor;

		while (true) {
			while (!scanner.hasNextFloat()) {
				System.out.println(mensagem + "\n(Digite um float não negativo)");
				scanner.next();
			}
			valor = scanner.nextFloat();
			if (valor < 0) {
				System.out.println(mensagem + "\n(Digite um float não negativo)");
			} else {
				break;
			}
		}

		return valor;
	}

	// ===================== ITEM =====================
	private int informarDadosItem(Scanner scanner, String nomeProduto, int qtde, String unidade, float preco) {
		System.out.println(
				String.format("Nome: %s\nQuantidade: %d\nUnidade: %s\nPreço: %f\n", nomeProduto, qtde, unidade, preco));

		return this.obterInt(scanner, "Escolha uma opção:\n1. Confirmar\n2. Cancelar");
	}

	private void cadastrarDadosItem(String nomeProduto, int qtde, String unidade, float preco) {
		try {
			this.ctrlRU.cadastrarDadosItem(nomeProduto, qtde, unidade, preco);
			System.out.println("------");
			System.out.println("Estoque atualizado com sucesso.");
		} catch (Exception e) {
			System.out.println("------");
			System.out.println("Erro encontrado: " + e.getMessage());
			System.out.println("Refaça a operação com parâmetros válidos.");
		}
	}

	private void criarItem(Scanner scanner) {
		String nomeProduto = this.obterString(scanner, "Digite o nome do item:");
		int qtde = this.obterInt(scanner, "Digite a quantidade do item:");

		String unidade = this.obterString(scanner, "Digite a unidade do item:");
		float preco = this.obterFloat(scanner, "Digite o preço do item:");
		System.out.println("------");

		int opcaoItem;

		do {
			opcaoItem = this.informarDadosItem(scanner, nomeProduto, qtde, unidade, preco);
			if (opcaoItem == 1) {
				this.cadastrarDadosItem(nomeProduto, qtde, unidade, preco);
				break;
			} else if (opcaoItem == 2) {
				System.out.println("------");
				System.out.println("Criação de Item cancelada com sucesso.");
				break;
			}
			System.out.println("···· Informe uma opção válida ····");
		} while (true);
	}
	// ================================================

	// ==================== ESTOQUE ====================
	private void mostrarEstoque(ArrayList<ItemEstoque> listaItensEstoque) {
		System.out.println(".... Lista de Itens ....");
		for (ItemEstoque item : listaItensEstoque) {
			String p1 = String.format("[%d] %s", item.getID().get(), item.getNomeProduto());
			String p2 = String.format(" - %d %s", item.getQtde(), item.getUnidade());
			String p3 = String.format(" = %f", item.getPreco());
			System.out.println(p1 + p2 + p3);
		}
		System.out.println("........................");
	}

	private ItemEstoque selecionarItem(Estoque estoque, int opcao) {
		ItemEstoque item = estoque.obterItemEstoque(new TipoID(opcao));
		if (item == null) {
			System.out.println("------");
			System.out.println("Item não encontrado, valor digitado incorretamente.");
		}
		return item;
	}

	private int informarQuantidade(Scanner scanner, int qtde, ItemEstoque item) {
		System.out.println("------");
		System.out
				.println(String.format("Nome: %s\nUnidade: %s\nPreço: %f\nQuantidade Antiga: %d\nQuantidade Nova: %d\n",
						item.getNomeProduto(), item.getUnidade(), item.getPreco(), item.getQtde(), qtde));

		return this.obterInt(scanner, "Escolha uma opção:\n1. Confirmar\n2. Cancelar");
	}

	private void cadastrarAtualizacaoItem(TipoID idItem, int qtde) {
		try {
			this.ctrlRU.cadastrarAtualizacaoItem(idItem, qtde);
			System.out.println("------");
			System.out.println("Estoque atualizado com sucesso.");
		} catch (Exception e) {
			System.out.println("------");
			System.out.println("Erro encontrado: " + e.getMessage());
			System.out.println("Refaça a operação com parâmetros válidos.");
		}
	}

	private void atualizarEstoque(Scanner scanner) {
		Estoque estoque = this.ctrlRU.obterListaItemEstoque();
		ArrayList<ItemEstoque> listaItensEstoque = estoque.getArrayList();

		if (listaItensEstoque.size() == 0) {
			System.out.println("Estoque vazio, favor adicionar novo item ao estoque antes de atualizá-lo.");
		} else {
			this.mostrarEstoque(listaItensEstoque);
			int opcaoItem = this.obterInt(scanner, "Digite o número do item desejado:");
			ItemEstoque item = this.selecionarItem(estoque, opcaoItem);
			if (item != null) {
				int qtde = this.obterInt(scanner, "Digite a nova quantidade:");
				do {
					opcaoItem = this.informarQuantidade(scanner, qtde, item);
					if (opcaoItem == 1) {
						this.cadastrarAtualizacaoItem(item.getID(), qtde);
						break;
					} else if (opcaoItem == 2) {
						System.out.println("------");
						System.out.println("Atualização de item cancelada com sucesso.");
						break;
					}
					System.out.println("···· Informe uma opção válida ····");
				} while (true);
			} else {
				System.out.println("Operação cancelada.");
			}
		}
	}
	// =================================================

	// ==================== RECEITA ====================
	private Receita selecionarReceita(int opcaoReceita) {
		Receita receita = this.ctrlRU.obterListaReceitas().obterReceita(new TipoID(opcaoReceita));
		if (receita == null) {
			System.out.println("------");
			System.out.println("Receita não encontrada, valor digitado incorretamente.");
		}
		return receita;
	}

	private Receita selecionarReceita(ArrayList<Receita> receitas, int opcaoReceita) {
		for (Receita receita : receitas) {
			if (receita.getID().get() == opcaoReceita) {
				return receita;
			}
		}
		System.out.println("------");
		System.out.println("Receita não encontrada, valor digitado incorretamente.");
		return null;
	}

	private void informarDadosReceita(String nome, String modoPreparo, int noPorcoes, int valorCalorico) {
		System.out.println(String.format("Nome: %s\nModo Preparo: %s\nNúmero de Porçẽs: %d\nValor Calórico: %d\n", nome,
				modoPreparo, noPorcoes, valorCalorico));
	}

	private void cadastrarDadosReceita(TipoID idReceita, String nome, String modoPreparo, int noPorcoes,
			int valorCalorico) {
		try {
			this.ctrlRU.cadastrarDadosReceita(idReceita, nome, modoPreparo, noPorcoes, valorCalorico);
			System.out.println("------");
			System.out.println("Receita criada com sucesso.");
		} catch (Exception e) {
			System.out.println("------");
			System.out.println("Erro encontrado: " + e.getMessage());
			System.out.println("Refaça a operação com parâmetros válidos.");

			this.destruirReceita(idReceita);
		}
	}

	private void destruirReceita(TipoID idReceita) {
		try {
			this.ctrlRU.destruirReceita(idReceita);
		} catch (Exception e) {
		}
	}

	private int informarDadosIngrediente(Scanner scanner, ItemEstoque item, int qtde, String unidade) {
		System.out.println(
				String.format("Nome: %s\nQuantidade: %d\nUnidade: %s\n", item.getNomeProduto(), qtde, unidade));
		return this.obterInt(scanner, "Escolha uma opção:\n1. Confirmar\n2. Cancelar");
	}

	private void cadastrarDadosIngrediente(TipoID idReceita, TipoID idItem, int qtde, String unidade) {
		try {
			this.ctrlRU.cadastrarDadosIngrediente(idReceita, idItem, qtde, unidade);
			System.out.println("------");
			System.out.println("Ingrediente adicionado com sucesso.");
		} catch (Exception e) {
			System.out.println("------");
			System.out.println("Erro encontrado: " + e.getMessage());
			System.out.println("Refaça a operação com parâmetros válidos.");
		}
	}

	private void criarIngrediente(TipoID idReceita, Scanner scanner) {
		Estoque estoque = this.ctrlRU.obterListaItemEstoque();
		ArrayList<ItemEstoque> listaItensEstoque = estoque.getArrayList();

		if (listaItensEstoque.size() == 0) {
			System.out.println("Estoque vazio, favor adicionar novo item ao estoque.");
		} else {
			this.mostrarEstoque(listaItensEstoque);
			int opcaoIngrediente = this.obterInt(scanner, "Digite o número do item desejado:");
			ItemEstoque item = this.selecionarItem(estoque, opcaoIngrediente);
			if (item != null) {
				TipoID idItem = item.getID();
				int qtde = this.obterInt(scanner, "Digite a quantidade do item:");
				String unidade = this.obterString(scanner, "Digite a unidade do item:");
				System.out.println("------");
				do {
					opcaoIngrediente = this.informarDadosIngrediente(scanner, item, qtde, unidade);
					if (opcaoIngrediente == 1) {
						this.cadastrarDadosIngrediente(idReceita, idItem, qtde, unidade);
						break;
					} else if (opcaoIngrediente == 2) {
						System.out.println("------");
						System.out.println("Criação de item cancelada com sucesso.");
						break;
					}
					System.out.println("···· Informe uma opção válida ····");
				} while (true);
			}
		}
	}

	private void criarReceita(Scanner scanner) {
		if (this.ctrlRU.obterListaItemEstoque().getArrayList().size() == 0) {
			System.out.println("Não há itens cadastrados no estoque, favor cadastrar.");
			return;
		}
		String nome = this.obterString(scanner, "Digite o nome da receita:");
		String modoPreparo = this.obterString(scanner, "Digite o modo preparo:");

		TipoID idReceita = this.ctrlRU.criarReceita(nome, modoPreparo);
		Receita receita = this.selecionarReceita(idReceita.get());
		int op = 0;

		while (true) {
			System.out.println("------");
			op = this.obterInt(scanner, "Escolha uma opção:\n 1. Criar Ingrediente \n 2. Finalizar \n 3. Cancelar");
			if (op == 1) {
				System.out.println("------");
				this.criarIngrediente(idReceita, scanner);
			} else if (op == 2) {
				if (receita.getIngredientes().size() == 0) {
					System.out.println("------");
					System.out.println("Receita deve conter pelo menos um ingrediente.");
				} else {
					System.out.println("------");
					int noPorcoes = this.obterInt(scanner, "Digite o numero de Porcoes:");
					int valorCalorico = this.obterInt(scanner, "Digite valor calórico:");
					System.out.println("------");
					this.informarDadosReceita(nome, modoPreparo, noPorcoes, valorCalorico);
					do {
						op = this.obterInt(scanner, "Escolha uma opção:\n1. Confirmar\n2. Cancelar");
						if (op == 1) {
							this.cadastrarDadosReceita(idReceita, nome, modoPreparo, noPorcoes, valorCalorico);
							break;
						} else if (op == 2) {
							this.destruirReceita(idReceita);
							System.out.println("------");
							System.out.println("Criação de receita cancelada com sucesso.");
							break;
						}
						System.out.println("···· Informe uma opção válida ····");
					} while (true);
					break;
				}
			} else if (op == 3) {
				this.destruirReceita(idReceita);
				System.out.println("------");
				System.out.println("Criação de receita cancelada com sucesso.");
				break;
			} else {
				System.out.println("···· Informe uma opção válida ····");
			}
		}

	}
	// =================================================

	// ==================== REFEIÇÃO ====================
	private void incluirReceita(TipoID idRefeicao, int opcaoReceita) {
		Receita receita = this.selecionarReceita(opcaoReceita);
		if (receita != null) {
			try {
				this.ctrlRU.incluirReceita(idRefeicao, receita.getID());
				System.out.println("------");
				System.out.println("Receita adicionada com sucesso.");
				System.out.println("------");
			} catch (Exception e) {
				System.out.println("------");
				System.out.println("Erro encontrado: " + e.getMessage());
				System.out.println("Refaça a operação com parâmetros válidos.");
				System.out.println("------");
			}
		}
	}

	private void removerReceita(Refeicao refeicao, int opcaoReceita) {
		Receita receita = this.selecionarReceita(refeicao.getReceitas(), opcaoReceita);
		if (receita != null) {
			try {
				this.ctrlRU.removerReceita(refeicao.getID(), receita.getID());
				System.out.println("------");
				System.out.println("Receita removida com sucesso.");
			} catch (Exception e) {
				System.out.println("------");
				System.out.println("Erro encontrado: " + e.getMessage());
				System.out.println("Refaça a operação com parâmetros válidos.");
			}
		}
	}

	private int informarDadosRefeicao(Scanner scanner, int noRefeicoes, Refeicao refeicao) {
		System.out.println("------");
		System.out.println(refeicao.obterInformacoes());
		return this.obterInt(scanner, "Escolha uma opção:\n1. Confirmar\n2. Cancelar");
	}

	private void cadastrarDadosRefeicao(TipoID idRefeicao, int noRefeicoes) {
		try {
			this.ctrlRU.cadastrarDadosRefeicao(idRefeicao, noRefeicoes);
			System.out.println("------");
			System.out.println("Refeição criada com sucesso e pedido gerado.");
		} catch (Exception e) {
			System.out.println("------");
			System.out.println("Erro encontrado: " + e.getMessage());
			System.out.println("Refaça a operação com parâmetros válidos.");

			this.destruirRefeicao(idRefeicao);
		}
	}

	private void destruirRefeicao(TipoID idRefeicao) {
		try {
			this.ctrlRU.destruirRefeicao(idRefeicao);
		} catch (Exception e) {
		}
	}

	private void mostrarReceitas(ArrayList<Receita> listaReceitas) {
		for (Receita r : listaReceitas) {
			System.out.println(String.format("[%d] %s - %d porções - %d calorias", r.getID().get(), r.getNome(),
					r.getNoPorcoes(), r.getValorCalorico()));
			System.out.println("    Ingredientes da receita:");
			for (Ingrediente i : r.getIngredientes()) {
				System.out.println("    - " + i.obterInformacoes());
			}
		}
	}

	private void mostrarReceitasComEstoque(ArrayList<Receita> listaReceitas) {
		for (Receita r : listaReceitas) {
			System.out.println(String.format("[%d] %s - %d porções - %d calorias", r.getID().get(), r.getNome(),
					r.getNoPorcoes(), r.getValorCalorico()));
			System.out.println("    Ingredientes da receita:");
			for (Ingrediente i : r.getIngredientes()) {
				ItemEstoque item = i.getItemEstoque();
				System.out.println(String.format("    - %s (Disponível: %d)", i.obterInformacoes(), item.getQtde()));
			}
		}
	}

	private void montarRefeicao(Scanner scanner) {
		int noRefeicoes = this.obterInt(scanner, "Digite o número de refeições desejada:");
		System.out.println("------");

		TipoID idRefeicao = this.ctrlRU.criarRefeicao(noRefeicoes);
		ListaDeReceitas receitas = this.ctrlRU.obterListaReceitas();
		ArrayList<Receita> listaReceitas = receitas.getArrayList();
		Refeicao refeicao = this.ctrlRU.obterListaRefeicoes().obterRefeicao(idRefeicao);

		while (true) {
			int opcaoRefeicao = this.obterInt(scanner,
					"Escolha uma opção:\n 1. Incluir receita \n 2. Remover receita \n 3. Finalizar \n 4. Cancelar");
			if (opcaoRefeicao == 1) {
				if (listaReceitas.size() == 0) {
					System.out.println("------");
					System.out.println("Não há receita criada, favor criar receita.");
					break;
				} else {
					System.out.println(".... Lista de Todas as Receitas ...");
					this.mostrarReceitasComEstoque(listaReceitas);
					System.out.println("..................................");

					int opcaoReceita = this.obterInt(scanner, "Escolha o numero da receita desejada:");
					this.incluirReceita(idRefeicao, opcaoReceita);
					System.out.println(".... Lista de Receitas Selecionadas ....");
					this.mostrarReceitas(refeicao.getReceitas());
					System.out.println("........................................");
				}
			} else if (opcaoRefeicao == 2) {
				if (refeicao.getReceitas().size() == 0) {
					System.out.println("------");
					System.out.println("Refeição não possui receitas, favor incluir receita.");

				} else {
					System.out.println(".... Lista de Receitas Selecionadas ....");
					this.mostrarReceitas(refeicao.getReceitas());
					System.out.println("........................................");
					int opcaoReceita = this.obterInt(scanner, "Escolha o numero da receita que deseja remover:");
					this.removerReceita(refeicao, opcaoReceita);
				}
			} else if (opcaoRefeicao == 3) {
				if (refeicao.getReceitas().size() == 0) {
					System.out.println("------");
					System.out.println("Refeição não possui receitas, favor incluir receita.");
				} else {
					do {
						int opcao = this.informarDadosRefeicao(scanner, noRefeicoes, refeicao);
						if (opcao == 1) {
							this.cadastrarDadosRefeicao(idRefeicao, noRefeicoes);
							break;
						} else if (opcao == 2) {
							this.destruirRefeicao(idRefeicao);
							System.out.println("Refeição removida com sucesso.");
							break;
						}
						System.out.println("···· Informe uma opção válida ····");
					} while (true);
					break;
				}
			} else if (opcaoRefeicao == 4) {
				this.destruirRefeicao(idRefeicao);
				System.out.println("------");
				System.out.println("Refeição cancelada com sucesso.");
				break;
			} else {
				System.out.println("···· Informe uma opção válida ····");
			}
			System.out.println("------");
		}
	}
	// ==================================================

	public void main() {
		Scanner scanner = new Scanner(System.in).useDelimiter("\n");

		while (true) {
			System.out.println("------");
			int opcao = this.obterInt(scanner,
					"Escolha uma opção:\n" + "1. Criar item estoque \n" + "2. Cadastrar receita \n"
							+ "3. Atualizar estoque \n" + "4. Montar refeição \n" + "5. Mostrar estoque \n"
							+ "6. Mostrar lista de receitas \n" + "7. Sair");
			System.out.println("------");
			if (opcao == 1) {
				this.criarItem(scanner);
			} else if (opcao == 2) {
				this.criarReceita(scanner);
			} else if (opcao == 3) {
				this.atualizarEstoque(scanner);
			} else if (opcao == 4) {
				this.montarRefeicao(scanner);
			} else if (opcao == 5) {
				Estoque estoque = this.ctrlRU.obterListaItemEstoque();
				this.mostrarEstoque(estoque.getArrayList());
			} else if (opcao == 6) {
				ListaDeReceitas listaReceitas = this.ctrlRU.obterListaReceitas();
				System.out.println(".... Lista de Todas as Receitas ....");
				this.mostrarReceitasComEstoque(listaReceitas.getArrayList());
				System.out.println("....................................");
			} else if (opcao == 7) {
				break;
			} else {
				System.out.println("···· Informe uma opção válida ····");
			}
		}
		scanner.close();
	}
}
