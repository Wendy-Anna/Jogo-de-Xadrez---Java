package xadrez.pecas;

import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{
	
	private PartidaXadrez partidaXadrez;
	
	
	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean [][] mat = new boolean [getTabuleiro().getLinhas()] [getTabuleiro().getColunas()];
		
		Posicao p = new Posicao (0,0);
		
		if(getCor() == Cor.WHITE) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao (posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().haUmaPeca(p2) && getcontagemMovimento()==0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExiste(p) && existePecaAdversariaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExiste(p) && existePecaAdversariaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// #Movimento especial en passant peças brancas
			if(posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(esquerda) && existePecaAdversariaNaPosicao(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getvulneravelEnPassant()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
					
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(direita) && existePecaAdversariaNaPosicao(direita) && getTabuleiro().peca(direita) == partidaXadrez.getvulneravelEnPassant()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
					
				}
			}
			
			
		}
		else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao (posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().haUmaPeca(p2) && getcontagemMovimento()==0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExiste(p) && existePecaAdversariaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExiste(p) && existePecaAdversariaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// #Movimento especial en passant peças pretas
						if(posicao.getLinha() == 4) {
							Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
							if(getTabuleiro().posicaoExiste(esquerda) && existePecaAdversariaNaPosicao(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getvulneravelEnPassant()) {
								mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
								
							}
							Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
							if(getTabuleiro().posicaoExiste(direita) && existePecaAdversariaNaPosicao(direita) && getTabuleiro().peca(direita) == partidaXadrez.getvulneravelEnPassant()) {
								mat[direita.getLinha() + 1][direita.getColuna()] = true;
								
							}
						}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
