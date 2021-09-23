package br.ufrn.imd.model;

public class ArvoreBinariaBusca extends ArvoreBinaria {

	@Override
	public void insert(Node n) {
		Node raiz = this.raiz;
		Node cima = null;
		while(raiz != null) {
			cima = raiz;
			if(n.getConteudo() < raiz.getConteudo()) {
				raiz = raiz.getNoEsquerda();
			} else {
				raiz = raiz.getNoDireita();
			}
		}
		if(cima == null) {
			this.raiz = n;
		} else if(n.getConteudo() > cima.getConteudo()) {
			cima.setNoDireita(n);
			n.setParent(cima);
		} else {
			cima.setNoEsquerda(n);
			n.setParent(cima);
		}
	}
	
	@Override
	public void remove(Node n) {
		Node target = this.raiz;
		while(target != null && n.getConteudo() != target.getConteudo()) {
			if(n.getConteudo() < target.getConteudo()) {
				target = target.getNoEsquerda();
			} else if(n.getConteudo() > target.getConteudo()) {
				target = target.getNoDireita();
			}
		}
		if(target == null) {
			System.out.println("Nó não existe");
		} else if(target.getNoEsquerda() == null){
			this.transplant(target, target.getNoDireita());
		} else if(target.getNoDireita() == null) {
			this.transplant(target, target.getNoEsquerda());
		} else {
			Node y = this.minimum(target.getNoDireita());
			if(y.getParent() != target) {
				this.transplant(y, y.getNoDireita());
				y.setNoDireita(target.getNoDireita());
				y.getNoDireita().setParent(y);
			}
			this.transplant(target, y);
			y.setNoEsquerda(target.getNoEsquerda());
			y.getNoEsquerda().setParent(y);
		}
	}
}