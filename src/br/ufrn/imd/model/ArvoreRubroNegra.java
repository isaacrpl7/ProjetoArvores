package br.ufrn.imd.model;

public class ArvoreRubroNegra extends ArvoreBinaria implements RubroNegra {
	
	private Node FOLHA;
	
	public ArvoreRubroNegra() {
		FOLHA = new Node();
		FOLHA.setColor(3);
		FOLHA.setNoDireita(null);
		FOLHA.setNoEsquerda(null);
		raiz = FOLHA;
	}
	
	public Node minimum(Node raiz) {
		while(raiz.getNoDireita() != FOLHA) {
			raiz = raiz.getNoDireita();
		}
		return raiz;
	}
	
	public Node maximum(Node raiz) {
		while(raiz.getNoEsquerda() != FOLHA) {
			raiz = raiz.getNoEsquerda();
		}
		return raiz;
	}
	
	@Override
	public void rotacaoEsquerda(Node p) {
		Node pivot = p.getNoDireita();
		p.setNoDireita(pivot.getNoEsquerda());
		if(pivot.getNoEsquerda() != FOLHA) {
			pivot.getNoEsquerda().setParent(p);
		}
		pivot.setParent(p.getParent());
		
		if(p.getParent() == null) {
			this.setRaiz(pivot);
		} else if(p == p.getParent().getNoEsquerda()) {
			p.getParent().setNoEsquerda(pivot);
		} else {
			p.getParent().setNoDireita(pivot);
		}
		
		pivot.setNoEsquerda(p);
		p.setParent(pivot);
	}

	@Override
	public void rotacaoDireita(Node p) {
		Node pivot = p.getNoEsquerda();
		p.setNoEsquerda(pivot.getNoDireita());
		if(pivot.getNoDireita() != FOLHA) {
			pivot.getNoDireita().setParent(p);
		}
		pivot.setParent(p.getParent());
		
		if(p.getParent() == null) {
			this.setRaiz(pivot);
		} else if(p == p.getParent().getNoDireita()) {
			p.getParent().setNoDireita(pivot);
		} else {
			p.getParent().setNoEsquerda(pivot);
		}
		
		pivot.setNoDireita(p);
		p.setParent(pivot);
	}
	
	@Override
	public void corrigirInsercao(Node n) {
		Node tio;
		while(n.getParent().getColor() == 1) { //enquanto o pai for vermelho
			if(n.getParent() == n.getParent().getParent().getNoDireita()) { //se o pai for o filho à direita
				tio = n.getParent().getParent().getNoEsquerda(); // tio
				if(tio != null && tio.getColor() == 1) {
					// Caso que o pai é vermelho e o tio também
					tio.setColor(0);
					n.getParent().setColor(0);
					n.getParent().getParent().setColor(1);
					n = n.getParent().getParent();
				} else {
					if(n == n.getParent().getNoEsquerda()) {//se o nó atual for o filho à esquerda
						//Caso que o pai é filho à direita do avô e o nó atual é o filho à esquerda
						n = n.getParent();
						rotacaoDireita(n);
					}
					//Caso o pai seja o filho à direita e o atual o filho à direita
					n.getParent().setColor(0);
					n.getParent().getParent().setColor(1);
					rotacaoEsquerda(n.getParent().getParent());
				}
			} else { // se o pai for o filho à esquerda
				tio = n.getParent().getParent().getNoDireita(); // tio
				
				if(tio != null && tio.getColor() == 1) {
					// Contrário do primeiro caso do bloco anterior
					tio.setColor(0);
					n.getParent().setColor(0);
					n.getParent().getParent().setColor(1);
					n = n.getParent().getParent();
				} else {
					if(n == n.getParent().getNoDireita()) {//se n for filho à direita
						//contrário do segundo caso do bloco anterior
						n = n.getParent();
						rotacaoEsquerda(n);
					}
					// Contrário do ultimo caso do bloco anterior
					n.getParent().setColor(0);
					n.getParent().getParent().setColor(1);
					rotacaoDireita(n.getParent().getParent());
				}
			}
			
			if(n == this.raiz) {
				break;
			}
		}
		this.raiz.setColor(0);
	}
	
	public void insert(Node n) {
		n.setColor(1);
		n.setNoDireita(FOLHA);
		n.setNoEsquerda(FOLHA);
		Node aux = null;
		Node percorrer = this.raiz;
		
		while(percorrer != FOLHA) {
			aux = percorrer;
			if(n.getConteudo() < percorrer.getConteudo()) {
				percorrer = percorrer.getNoEsquerda();
			} else if(n.getConteudo() > percorrer.getConteudo()) {
				percorrer = percorrer.getNoDireita();
			} else {
				System.out.println("Esse nó já existe!");
				return;
			}
		}
		
		//aux é o pai de percorrer
		n.setParent(aux);
		if(aux == null) {
			this.raiz = n;
		} else if(n.getConteudo() < aux.getConteudo()) {
			aux.setNoEsquerda(n);
		} else {
			aux.setNoDireita(n);
		}
		
		if(n.getParent() == null) {
			n.setColor(0);
			return;
		}
		
		if(n.getParent().getParent() == null)
			return;
		
		corrigirInsercao(n);
	}
	
	public void corrigirExclusao(Node n) {
		Node irmao;
		while(n != raiz && n.getColor() == 0) {
			if(n == n.getParent().getNoEsquerda()) {
				irmao = n.getParent().getNoDireita();
				if(irmao != null && irmao.getColor() == 1) {
					//caso em que o irmao é vermelho
					irmao.setColor(0);
					n.getParent().setColor(1);
					rotacaoEsquerda(n.getParent());
					irmao = n.getParent().getNoDireita();
				}
				if(irmao.getNoEsquerda().getColor() == 0 && irmao.getNoDireita().getColor() == 0) {
					//caso em que o irmão é preto e ambos os filhos do irmao são pretos
					irmao.setColor(1);
					n = n.getParent();
				} else {
					if(irmao.getNoDireita().getColor() == 0) {
						//caso em que o irmao é preto, o filho esquerdo do irmao é vermelho e o direito é preto
						irmao.getNoEsquerda().setColor(0);
						irmao.setColor(1);
						rotacaoDireita(irmao);
						irmao = n.getParent().getNoDireita();
					}
					//caso em que o irmão é preto e o filho direito do irmão é vermelho
					irmao.setColor(n.getParent().getColor());
					n.getParent().setColor(0);
					irmao.getNoDireita().setColor(0);
					rotacaoEsquerda(n.getParent());
					n = raiz;
				}
			} else { // casos espelhados
				irmao = n.getParent().getNoEsquerda();
				if (irmao != null && irmao.getColor() == 1) {
					// caso 1
					irmao.setColor(0);
					n.getParent().setColor(1);
					rotacaoDireita(n.getParent());
					irmao = n.getParent().getNoEsquerda();
				}

				if (irmao.getNoDireita().getColor() == 0 && irmao.getNoEsquerda().getColor() == 0) {
					// caso 2
					irmao.setColor(1);
					n = n.getParent();
				} else {
					if (irmao.getNoEsquerda().getColor() == 0) {
						// case 3
						irmao.getNoDireita().setColor(0);
						irmao.setColor(1);
						rotacaoEsquerda(irmao);
						irmao = n.getParent().getNoEsquerda();
					} 

					// caso 4
					irmao.setColor(n.getParent().getColor());
					n.getParent().setColor(0);
					irmao.getNoEsquerda().setColor(0);
					rotacaoDireita(n.getParent());
					n = raiz;
				}
			}
		}
		n.setColor(0);
	}
	
	public void remove(double key) {
		Node n = this.raiz;
		Node aux = FOLHA;
		Node x, y;
		while(n != FOLHA) {
			if(n.getConteudo() == key) {
				aux = n;
			}
			if(n.getConteudo() <= key) {
				n = n.getNoDireita();
			} else {
				n = n.getNoEsquerda();
			}
		}
		
		if(aux == FOLHA) {
			System.out.println("Esse nó não existe");
			return;
		}
		
		y = aux; // y vai ser o nó a ser removido
		int guardarCor = y.getColor(); // guardar a cor do nó a ser removido
		if(aux.getNoEsquerda() == FOLHA) { //caso o nó a ser removido não tenha filho à esquerda
			x = aux.getNoDireita(); //x será o filho da direita do nó a ser deletado
			transplant(aux, aux.getNoDireita()); //trocar o nó a ser deletado pelo filho à direita dele
		} else if(aux.getNoDireita() == FOLHA) {//caso não tenha filho à direita
			x = aux.getNoEsquerda();
			transplant(aux, aux.getNoEsquerda());
		} else {//caso tenha dois filhos
			y = minimum(aux.getNoDireita());
			guardarCor = y.getColor();
			x = y.getNoDireita();
			if(y.getParent() == aux) {
				x.setParent(y);
			} else {
				transplant(y, y.getNoDireita());
				y.setNoDireita(aux.getNoDireita());
				y.getNoDireita().setParent(y);
			}
			transplant(aux, y);
			y.setNoEsquerda(aux.getNoEsquerda());
			y.getNoEsquerda().setParent(y);
			y.setColor(aux.getColor());
		}
		if(guardarCor == 0) {
			corrigirExclusao(x);
		}
	}
	
}