package br.ufrn.imd.model;

public class Node {
	private double conteudo;
	private Node noEsquerda;
	private Node noDireita;
	private Node parent;
	
	//para árvores avl:
	private int altura;
	
	//para árvores rubo-negra
	private int color; // 1 para vermelho e 0 para preto
	
	public double getConteudo() {
		return conteudo;
	}
	public void setConteudo(double conteudo) {
		this.conteudo = conteudo;
	}
	public Node getNoEsquerda() {
		return noEsquerda;
	}
	public void setNoEsquerda(Node noEsquerda) {
		this.noEsquerda = noEsquerda;
	}
	public Node getNoDireita() {
		return noDireita;
	}
	public void setNoDireita(Node noDireita) {
		this.noDireita = noDireita;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
}
