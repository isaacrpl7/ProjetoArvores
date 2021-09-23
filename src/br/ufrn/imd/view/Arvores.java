package br.ufrn.imd.view;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import br.ufrn.imd.controller.ArvoreController;
import br.ufrn.imd.model.ArvoreAVL;
import br.ufrn.imd.model.ArvoreBinariaBusca;
import br.ufrn.imd.model.ArvoreRubroNegra;
import br.ufrn.imd.model.Node;

@SuppressWarnings("serial")

public class Arvores extends JFrame{
	
	static private int ALTURA = 500;
	static private int LARGURA = 600;
	
	private int rootY = 10;
	private int TAMANHO_NO = 30;
	private int ALTURA_LINHA = 50;
	mxGraph graph = new mxGraph();
	Object parent = graph.getDefaultParent();
	
	public Object desenharArvore(Node raiz, int profundidade, int index) {
		if(raiz == null) return null;//se n�o houver mais raiz, ir� retornar nulo e n�o ir� inserir mais vertex
		int myX = (int) ((LARGURA * (index)) / (Math.pow(2, profundidade-1) +1));//c�lculo para definir a coordenada X de cada vertex
		Object rootVertex;
		if(raiz.getColor() == 1) {
			rootVertex = graph.insertVertex(parent, null, raiz.getConteudo(), myX, profundidade * ALTURA_LINHA + rootY, TAMANHO_NO, TAMANHO_NO,"strokeColor=#a32a40;fillColor=#c93a63;fontColor=white");
		} else if(raiz.getColor() == 0){
			rootVertex = graph.insertVertex(parent, null, raiz.getConteudo(), myX, profundidade * ALTURA_LINHA + rootY, TAMANHO_NO, TAMANHO_NO,"strokeColor=black;fillColor=#262626;fontColor=white");
		} else {
			rootVertex = graph.insertVertex(parent, null, raiz.getConteudo(), myX, profundidade * ALTURA_LINHA + rootY, TAMANHO_NO, TAMANHO_NO,"opacity=30;strokeColor=black;fillColor=#2b2b2b;fontColor=white");
		}
		
		Object rightChildVertex = desenharArvore(raiz.getNoDireita(), profundidade + 1, index * 2);//chamada recusiva que aumenta a profundidade, e desenha os pr�ximos n�s da direita
		if(rightChildVertex != null) {//linha
			graph.insertEdge(parent, null, "", rootVertex, rightChildVertex, "startArrow=none;endArrow=none;strokeWidth=1;strokeColor=black");
		}
		
		Object leftChildVertex = desenharArvore(raiz.getNoEsquerda(), profundidade + 1, index * 2 - 1);
		if(leftChildVertex != null) {
			graph.insertEdge(parent, null, "", rootVertex, leftChildVertex, "startArrow=none;endArrow=none;strokeWidth=1;strokeColor=black");
		}
		
		return rootVertex;
	}
	
	public void update(Node raiz) {
		graph.getModel().beginUpdate();
		try {
			Object[] cells = graph.getChildCells(parent, true, false);
			graph.removeCells(cells, true);
			desenharArvore(raiz, 1, 1);
		} finally {
			graph.getModel().endUpdate();
		}
	}
	
	public Arvores(Node raiz) {
		this.update(raiz);
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}

	public static void main(String[] args) {
		ArvoreController ac = new ArvoreController();
		ArvoreBinariaBusca abb = new ArvoreBinariaBusca();
		ArvoreAVL avl = new ArvoreAVL();
		ArvoreRubroNegra arn = new ArvoreRubroNegra();
		
		int opt=0;
		while(opt != 10) {
			Scanner s = new Scanner(System.in);
			
			//PRIMEIRO MENU
			System.out.println("Escolha um tipo de �rvore:");
			System.out.println("1 - �rvore bin�ria de busca");
			System.out.println("2 - �rvore AVL");
			System.out.println("3 - �rvore Rubro Negra");
			System.out.println("4 - Testar a inser��o nas �rvores");
			System.out.println("10 - Sair");
			opt = s.nextInt();
			
			int opt1 = 0;
			while(opt1 != 10) {
				if(opt == 1) {
					System.out.println("Escolha uma das op��es: ");
					System.out.println("1 - Imprimir a �rvore de busca");
					System.out.println("2 - Buscar um elemento na �rvore de busca");
					System.out.println("3 - Inserir um elemento na �rvore de busca");
					System.out.println("4 - Remover um elemento na �rvore de busca");
					System.out.println("5 - Visualizar graficamente a �rvore de busca");
					System.out.println("10 - Voltar");
					opt1 = s.nextInt();
					
					if(opt1 == 1) {
						ac.print(abb.getRaiz());
					} else if(opt1 == 2) {
						System.out.println("Digite o valor do elemento a ser buscado");
						double value = s.nextDouble();
						Node result = abb.search(value);
						if(result == null) {
							System.out.println("N� n�o foi encontrado!");
						} else {
							System.out.println("N� " + result.getConteudo() + " encontrado!");
						}
					} else if(opt1 == 3) {
						System.out.println("Digite o valor a ser inserido na �rvore");
						double value = s.nextDouble();
						Node n = new Node();
						n.setConteudo(value);
						abb.insert(n);
					} else if(opt1 == 4) {
						System.out.println("Digite o valor a ser removido da �rvore");
						double value = s.nextDouble();
						Node n = new Node();
						n.setConteudo(value);
						abb.remove(n);
					} else if(opt1 == 5) {
						Arvores arvore = new Arvores(abb.getRaiz());
						JFrame frame = arvore;
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					    frame.setSize(LARGURA, ALTURA);
					    frame.setTitle("�rvore Bin�ria de Busca");
					    frame.setVisible(true);
					}
					
				} else if(opt == 2) {
					System.out.println("Escolha uma das op��es: ");
					System.out.println("1 - Imprimir a �rvore AVL");
					System.out.println("2 - Buscar um elemento na �rvore AVL");
					System.out.println("3 - Inserir um elemento na �rvore AVL");
					System.out.println("4 - Remover um elemento na �rvore AVL");
					System.out.println("5 - Visualizar graficamente a �rvore AVL");
					System.out.println("10 - Voltar");
					opt1 = s.nextInt();
					
					if(opt1 == 1) {
						ac.print(avl.getRaiz());
					} else if(opt1 == 2) {
						System.out.println("Digite o valor do elemento a ser buscado");
						double value = s.nextDouble();
						Node result = avl.search(value);
						if(result == null) {
							System.out.println("N� n�o foi encontrado!");
						} else {
							System.out.println("N� " + result.getConteudo() + " encontrado!");
						}
					} else if(opt1 == 3) {
						System.out.println("Digite o valor a ser inserido na �rvore AVL");
						double value = s.nextDouble();
						avl.setRaiz(avl.insert(avl.getRaiz(),value));
					} else if(opt1 == 4) {
						System.out.println("Digite o valor a ser removido da �rvore AVL");
						double value = s.nextDouble();
						avl.setRaiz(avl.remove(avl.getRaiz(), value));
					} else if(opt1 == 5) {
						Arvores arvore = new Arvores(avl.getRaiz());
						JFrame frame = arvore;
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					    frame.setSize(LARGURA, ALTURA);
					    frame.setTitle("�rvore AVL");
					    frame.setVisible(true);
					}
				} else if(opt == 3) {
					System.out.println("Escolha uma das op��es: ");
					System.out.println("1 - Imprimir a �rvore Rubro Negra");
					System.out.println("2 - Buscar um elemento na �rvore Rubro Negra");
					System.out.println("3 - Inserir um elemento na �rvore Rubro Negra");
					System.out.println("4 - Remover um elemento na �rvore Rubro Negra");
					System.out.println("5 - Visualizar graficamente a �rvore Rubro Negra");
					System.out.println("10 - Voltar");
					opt1 = s.nextInt();
					
					if(opt1 == 1) {
						ac.print(arn.getRaiz());
					} else if(opt1 == 2) {
						System.out.println("Digite o valor do elemento a ser buscado");
						double value = s.nextDouble();
						Node result = arn.search(value);
						if(result == null) {
							System.out.println("N� n�o foi encontrado!");
						} else {
							System.out.println("N� " + result.getConteudo() + " encontrado!");
						}
					} else if(opt1 == 3) {
						System.out.println("Digite o valor a ser inserido na �rvore Rubro Negra");
						double value = s.nextDouble();
						Node n = new Node();
						n.setConteudo(value);
						arn.insert(n);
					} else if(opt1 == 4) {
						System.out.println("Digite o valor a ser removido da �rvore Rubro Negra");
						double value = s.nextDouble();
						arn.remove(value);
					} else if(opt1 == 5) {
						Arvores arvore = new Arvores(arn.getRaiz());
						JFrame frame = arvore;
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					    frame.setSize(LARGURA, ALTURA);
					    frame.setTitle("�rvore Rubro Negra");
					    frame.setVisible(true);
					}
				} else if(opt == 4) {
					System.out.println("Digite a quantidade de inser��es que deseja realizar");
					double value = s.nextDouble();
					Instant start = Instant.now();
					for(double i=0;i<value;i++) {
						Node n = new Node();
						n.setConteudo(i);
						arn.insert(n);
					}
					Instant finish = Instant.now();
					long timeElapsed = Duration.between(start, finish).toMillis();
					System.out.println(timeElapsed + " milisegundos para inserir "+ value +" elementos na RUBRO NEGRA");
					
					start = Instant.now();
					for(double i=0;i<value;i++) {
						Node n = new Node();
						n.setConteudo(i);
						abb.insert(n);
					}
					finish = Instant.now();
					timeElapsed = Duration.between(start, finish).toMillis();
					System.out.println(timeElapsed + " milisegundos para inserir "+ value +" elementos na BINARIA DE BUSCA");
					
					start = Instant.now();
					for(double i=0;i<value;i++) {
						avl.setRaiz(avl.insert(avl.getRaiz(),i));
					}
					finish = Instant.now();
					timeElapsed = Duration.between(start, finish).toMillis();
					System.out.println(timeElapsed + " milisegundos para inserir "+ value +" elementos na AVL");
					opt = 10;
				}
			}
			
			
		}
	}

}
