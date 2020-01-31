package xadrez;

import tabuleirojogo.Peca;
import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;

public abstract class PecaXadrez extends Peca{

	private Cor cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public XadrezPosicao getXadrezPosicao() {
		return XadrezPosicao.fromPosicao(posicao);
		
	}
	
	protected boolean existePecaAdversariaNaPosicao(Posicao posicao) {
		 PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		 return p != null && p.getCor() != cor;
		 
	}

}
