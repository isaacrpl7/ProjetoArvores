package br.ufrn.imd.model;

public interface AVL {
	public Node rotacaoEsquerda(Node p);
	public Node rotacaoDireita(Node p);
	public int fatorBalanceamento(Node n);
	public int altura(Node n);
}
