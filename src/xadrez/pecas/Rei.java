package xadrez.pecas;

import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{
	
	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover (Posicao posicao) {

		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
 	}
	
	private boolean testeTorreAptaRoque (Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getcontagemMovimento() == 0;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean [][] mat = new boolean [getTabuleiro().getLinhas()] [getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		// Em cima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Em baixo
				p.setValores(posicao.getLinha() + 1, posicao.getColuna());
				if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
		// Para esquerda
				p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
		// Para direita
				p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
		// Noroeste 
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
		// Nordeste
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
		// Sudoeste 
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
		// Sudeste 
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				// #Movimento Especial de Roque
				if(getcontagemMovimento() == 0 && !partidaXadrez.getXeque()) {
					
					// #Movimento Esqpecial Roque Pequeno
					Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
					if(testeTorreAptaRoque(posT1)) {
						Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
						Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
						if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
							mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
						}
					
					}
					
					// #Movimento Esqpecial Roque Grande
					Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
					if(testeTorreAptaRoque(posT2)) {
						Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
						Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
						Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
						if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
							mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
						}
					
					}
					
				}
				
		
		return mat;
	}

}
