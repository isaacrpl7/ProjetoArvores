package br.ufrn.imd.model;

public abstract class ArvoreBinaria {
	
	protected Node raiz;
	
	public Node search(double num) {
		Node raiz = this.raiz;
		while(raiz != null && num != raiz.getConteudo()) {
			if(num > raiz.getConteudo()) {
				raiz = raiz.getNoDireita();
			} else {
				raiz = raiz.getNoEsquerda();
			}
		}
		return raiz;
	}
	
	public Node minimum(Node raiz) {
		while(raiz.getNoDireita() != null) {
			raiz = raiz.getNoDireita();
		}
		return raiz;
	}
	
	public Node maximum(Node raiz) {
		while(raiz.getNoEsquerda() != null) {
			raiz = raiz.getNoEsquerda();
		}
		return raiz;
	}
	
	public void transplant(Node n, Node v) {
		if(n.getParent() == null) {
			this.raiz = v;
		} else if(n == n.getParent().getNoEsquerda()) {
			n.getParent().setNoEsquerda(v);
		} else {
			n.getParent().setNoDireita(v);
		}
		if(v != null) {
			v.setParent(n.getParent());
		}
	}
	
	public int max(int a, int b) {
		if(a>b) return a;
		else return b;
	}
	
	public Node getRaiz() {
		return this.raiz;
	}
	public void setRaiz(Node r) {
		this.raiz = r;
	}
	
	public void insert(Node n) {};
	public Node insert(Node raiz, double key) {
		return null;
	};
	public Node remove(Node raiz, double key) {
		return null;
	};
	public void remove(Node n) {};
}
