package xadrez.pecas;

import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez{

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] possiveisMovimentos() {
		boolean [][] mat = new boolean [getTabuleiro().getLinhas()] [getTabuleiro().getColunas()];
		
		Posicao p = new Posicao (0,0);
		
		//Noroeste da peça
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if(getTabuleiro().posicaoExiste(p) && existePecaAdversariaNaPosicao(p)){
			mat[p.getLinha()][p.getColuna()]=true;

		}
		
		//nordeste da peça
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
				while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
					p.setValores(p.getLinha() - 1, p.getColuna() + 1);				}
				if(getTabuleiro().posicaoExiste(p) && existePecaAdversariaNaPosicao(p)){
					mat[p.getLinha()][p.getColuna()]=true;

				}
				
		//Sudeste da peça
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
				while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
					p.setValores(p.getLinha() + 1, p.getColuna() + 1);				}
				if(getTabuleiro().posicaoExiste(p) && existePecaAdversariaNaPosicao(p)){
					mat[p.getLinha()][p.getColuna()]=true;

				}
		
				
		//Sudoeste da peça
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
				while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
					p.setValores(p.getLinha() + 1, p.getColuna() - 1);				}
				if(getTabuleiro().posicaoExiste(p) && existePecaAdversariaNaPosicao(p)){
					mat[p.getLinha()][p.getColuna()]=true;

				}
				
				
		return mat;
	}
	
}
