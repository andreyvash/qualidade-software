package com.qualidade.qualidade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QualidadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(QualidadeApplication.class, args);
		CtrlRU ctrlRU = new CtrlRU(new ImpressoraTela(), new EscreveLeArquivo());
        TelaUsuario tela = new TelaUsuario(ctrlRU);
        tela.main();
	}

}
