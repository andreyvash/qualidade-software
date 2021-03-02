package com.qualidade.qualidade;

public class ImpressoraTela implements InterfaceImpressao {
	public void imprimirPedido(Pedido pedido) {
		System.out.println(pedido.obterInformacoes());
	}
}
