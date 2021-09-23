package br.ufrn.imd.model;

public class ArvoreAVL extends ArvoreBinaria implements AVL {

	@Override
	public Node rotacaoEsquerda(Node p) {
		Node pivot = p.getNoDireita();
		p.setNoDireita(pivot.getNoEsquerda());
		pivot.setNoEsquerda(p);
		
		//atualizando alturas
		p.setAltura(max(altura(p.getNoDireita()), altura(p.getNoEsquerda())) + 1);
		pivot.setAltura(max(altura(pivot.getNoEsquerda()), altura(pivot.getNoDireita())) + 1);
		
		return pivot;
	}

	@Override
	public Node rotacaoDireita(Node p) {
		Node pivot = p.getNoEsquerda();
		p.setNoEsquerda(pivot.getNoDireita());
		pivot.setNoDireita(p);
		
		//atualizando alturas
		p.setAltura(max(altura(p.getNoDireita()), altura(p.getNoEsquerda())) + 1);
		pivot.setAltura(max(altura(pivot.getNoEsquerda()), altura(pivot.getNoDireita())) + 1);
		
		return pivot;
	}

	@Override
	public int fatorBalanceamento(Node n) {
		if(n == null) return 0;
		return altura(n.getNoEsquerda()) - altura(n.getNoDireita());
	}
	
	@Override
	public int altura(Node n) {
		if(n == null) 
			return 0;
		else
			return n.getAltura();
	}

	@Override
	public Node insert(Node raiz, double key) {
		Node novo = new Node();
		novo.setConteudo(key);
		
		if(raiz == null) {
			return novo;
		}
		
		if(key < raiz.getConteudo()) {
			raiz.setNoEsquerda(insert(raiz.getNoEsquerda(), key));
		} else if(key > raiz.getConteudo()) {
			raiz.setNoDireita(insert(raiz.getNoDireita(), key));
		} else {
			return raiz;
		}
		
		// Reescrever a altura
		raiz.setAltura(1 + max(altura(raiz.getNoEsquerda()), altura(raiz.getNoDireita())));
		
		int balanceamento = fatorBalanceamento(raiz);
		
		//caso 1 (esquerda e esquerda)
		if(balanceamento > 1 && key < raiz.getNoEsquerda().getConteudo()) {
			return rotacaoDireita(raiz);
		}
		
		//caso 2 (direita e direita)
		if(balanceamento < -1 && key > raiz.getNoDireita().getConteudo()) {
			return rotacaoEsquerda(raiz);
		}
		
		//caso 3 (esquerda e direita)
		if(balanceamento > 1 && key > raiz.getNoDireita().getConteudo()) {
			raiz.setNoEsquerda(rotacaoDireita(raiz.getNoEsquerda()));
			return rotacaoDireita(raiz);
		}
		
		//caso 4 (direita e esquerda)
		if(balanceamento < -1 && key < raiz.getNoDireita().getConteudo()) {
			raiz.setNoDireita(rotacaoDireita(raiz.getNoDireita()));
			return rotacaoEsquerda(raiz);
		}
		
		return raiz;
	}
	
	public Node remove(Node raiz, double key) {
		Node novo = new Node();
		novo.setConteudo(key);
		
		//se for nula
		if(raiz == null)
			return raiz;
		
		//ir para o nó da esquerda
		if(key < raiz.getConteudo())
			raiz.setNoEsquerda(remove(raiz.getNoEsquerda(), key));
		
		//ir para o nó da direita
		else if(key > raiz.getConteudo())
			raiz.setNoDireita(remove(raiz.getNoDireita(), key));
		
		//se for o nó que queremos deletar
		else {
			//nó tem apenas um filho, ou nenhum filho
			if(raiz.getNoEsquerda() == null || raiz.getNoDireita() == null) {
				Node aux = raiz.getNoDireita() == null ? raiz.getNoEsquerda() : raiz.getNoDireita();//se for o filho da direita, retorna ele, caso contrário, retorna o outro
				
				//nao tem filhos
				if(aux == null) {
					aux = raiz;
					raiz = null;
				} else //tem um filho
					raiz = aux;
				
			} else {//tem dois filhos
				Node aux = minimum(raiz.getNoDireita());//sucessor direito
				raiz.setConteudo(aux.getConteudo());//copia os dados do sucessor direito para o nó atual
				raiz.setNoDireita(remove(raiz.getNoDireita(),aux.getConteudo()));//deleta o sucessor
			}
		}
		
		//Se a chave tiver somente um nó
		if(raiz == null)
			return raiz;
		
		//atualizar alturas
		raiz.setAltura(1 + max(altura(raiz.getNoEsquerda()), altura(raiz.getNoDireita())));
		
		//verificar balanceamento
		int balanceamento = fatorBalanceamento(raiz);
		
		//esquerda e esquerda
		if(balanceamento > 1 && fatorBalanceamento(raiz.getNoEsquerda()) >= 0)
			return rotacaoDireita(raiz);
		
		//esquerda e direita
		if(balanceamento > 1 && fatorBalanceamento(raiz.getNoEsquerda()) < 0) {
			raiz.setNoEsquerda(rotacaoDireita(raiz.getNoEsquerda()));
			return rotacaoDireita(raiz);
		}
		
		//direita e direita
		if(balanceamento < -1 && fatorBalanceamento(raiz.getNoDireita()) <= 0)
			return rotacaoDireita(raiz);
		
		// direita e esquerda
		if(balanceamento < -1 && fatorBalanceamento(raiz.getNoDireita()) > 0) {
			raiz.setNoDireita(rotacaoDireita(raiz.getNoDireita()));
			return rotacaoEsquerda(raiz);
		}
			
		return raiz;
	};
	
}
