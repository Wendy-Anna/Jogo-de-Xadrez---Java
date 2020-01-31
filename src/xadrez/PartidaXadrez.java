package xadrez;


import tabuleirojogo.Peca;
import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	
	
	public int getTurno() {
		return turno;
	}


	public Cor getJogadorAtual() {
		return jogadorAtual;
	}


	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Cor.WHITE;
		configuracaoInicial ();
		
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez [][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
				
			}
		}
		return mat;
	}
	
	public boolean[][] possiveisMovimentos(XadrezPosicao origemPosicao){
		Posicao posicao = origemPosicao.toPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}
	
	public PecaXadrez performanceMovimentoXadrez(XadrezPosicao origemPosicao, XadrezPosicao destinoPosicao) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		validarPosicaoDestino(origem, destino);
		Peca capturePeca = fazerMovimento (origem, destino);
		proximoTurno();
		return (PecaXadrez) capturePeca;
	}
	
	private void validarOrigemPosicao(Posicao posicao) {
		if(!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezException ("Não existe peça na posição de origem");
		}
		if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()){
			throw new XadrezException ("A peça escolhida não é sua!");

		}
		if(!tabuleiro.peca(posicao).temPeloMenosUmMovimentoPossivel()) {
			throw new XadrezException ("Não existe movimentos possiveis para a peça escolhida!");

		}
	}
	
	private void validarPosicaoDestino (Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new XadrezException ("A peça escolhida, não pode se mover para a posição de destino!");
		}
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturePeca = tabuleiro.removePeca(destino);
		tabuleiro.coloquePeca(p, destino);
		return capturePeca;
	}
	
	private void proximoTurno() {
		turno ++;
		jogadorAtual = (jogadorAtual == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.coloquePeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
	}
	
	private void configuracaoInicial (){
		colocarNovaPeca('c', 1,new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('c', 2,new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('d', 2,new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('e', 2,new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('e', 1,new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('d', 1, new Rei (tabuleiro, Cor.WHITE));
		
		colocarNovaPeca('c', 7,new Torre (tabuleiro, Cor.BLACK));
		colocarNovaPeca('c', 8,new Torre (tabuleiro, Cor.BLACK));
		colocarNovaPeca('d', 7,new Torre (tabuleiro, Cor.BLACK));
		colocarNovaPeca('e', 7,new Torre (tabuleiro, Cor.BLACK));
		colocarNovaPeca('e', 8,new Torre (tabuleiro, Cor.BLACK));
		colocarNovaPeca('d', 8,new Rei (tabuleiro, Cor.BLACK));


	}
	
	
	
}
