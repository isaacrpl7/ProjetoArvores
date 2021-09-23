package br.ufrn.imd.controller;

import br.ufrn.imd.model.Node;

public class ArvoreController {
	public void print(Node n) {
		if(n != null) {
			print(n.getNoEsquerda());
			System.out.println(n.getConteudo());
			print(n.getNoDireita());
		}
	}
}
