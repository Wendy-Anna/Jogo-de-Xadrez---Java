package xadrez;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleirojogo.Peca;
import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;



public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();	
    private List<Peca> pecasCapturadas = new ArrayList<>();	

    
    
	public int getTurno() {
		return turno;
	}


	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getXeque() {
		return xeque;
	}

	public boolean getXequeMate() {
		return xequeMate;
	}
	
	public PartidaXadrez() {
		
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Cor.WHITE;
		configuracaoInicial();
		
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
		
		if(testeXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturePeca);
			throw new XadrezException("Você não pode se colocar em cheque!");
		}
		
		xeque = (testeXeque(oponente(jogadorAtual))) ? true: false;
		
		if(testeXequeMate (oponente(jogadorAtual))) {
			xequeMate = true;
		}
		else {
			proximoTurno();

		}
		
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
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(origem);
		p.aumentarContagemMovemento();
		Peca capturePeca = tabuleiro.removePeca(destino);
		tabuleiro.coloquePeca(p, destino);
		
		if(capturePeca != null) {
			pecasNoTabuleiro.remove(capturePeca);
			pecasCapturadas.add(capturePeca);
		}
				
		return capturePeca;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturePeca) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(destino);
		p.diminuirContagemMovemento();
		tabuleiro.coloquePeca(p, origem);
		
		if(capturePeca != null) {
			tabuleiro.coloquePeca(capturePeca, destino);
			pecasCapturadas.remove(capturePeca);
			pecasNoTabuleiro.add(capturePeca);
		}
		
	}
	
	private void proximoTurno() {
		turno ++;
		jogadorAtual = (jogadorAtual == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.WHITE)? Cor.BLACK : Cor.WHITE;
	}
	
	private PecaXadrez Rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : lista) {
			if(p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe o Rei da cor " + cor + " no tabuleiro");
	}
	
	private boolean testeXeque(Cor cor) {
		Posicao PosicaoRei = Rei(cor).getXadrezPosicao().toPosicao();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peca p : pecasOponentes) {
			boolean [][] mat = p.possiveisMovimentos();
			if(mat [PosicaoRei.getLinha()][PosicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeXequeMate(Cor cor) {
		if(!testeXeque(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : lista) {
			boolean [][] mat = p.possiveisMovimentos();
			for(int i=0; i<tabuleiro.getLinhas(); i++) {
				for(int j=0; j<tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getXadrezPosicao().toPosicao();
						Posicao destino = new Posicao(i,j);
						Peca capturePeca = fazerMovimento(origem, destino);
						boolean testeXeque = testeXeque(cor);
						desfazerMovimento(origem, destino, capturePeca);
						if(!testeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.coloquePeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private void configuracaoInicial (){
		
		colocarNovaPeca ( 'a' , 1 , new  Torre (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'b' , 1 , new  Cavalo (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'g' , 1 , new  Cavalo (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'd' , 1 , new  Rainha (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'c' , 1 , new  Bispo (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'f' , 1 , new  Bispo (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'e' , 1 , new  Rei (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'h', 1 , new Torre (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'a' , 2 , new  Peao (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'b' , 2 , new  Peao (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'c' , 2 , new  Peao (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'd', 2 , new  Peao (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'e', 2 , new  Peao (tabuleiro, Cor.WHITE));
		colocarNovaPeca ('f', 2 , new  Peao (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'g', 2 , new  Peao (tabuleiro, Cor.WHITE));
		colocarNovaPeca ( 'h' , 2 , new  Peao (tabuleiro, Cor.WHITE));
		
		colocarNovaPeca('d', 8,new Rainha (tabuleiro, Cor.BLACK));
		colocarNovaPeca('b', 8,new Cavalo (tabuleiro, Cor.BLACK));
		colocarNovaPeca('g', 8,new Cavalo (tabuleiro, Cor.BLACK));
		colocarNovaPeca('a', 8,new Torre (tabuleiro, Cor.BLACK));
		colocarNovaPeca('e', 8,new Rei (tabuleiro, Cor.BLACK));
		colocarNovaPeca('h', 8,new Torre (tabuleiro, Cor.BLACK));
		colocarNovaPeca('a', 7,new Peao (tabuleiro, Cor.BLACK));
		colocarNovaPeca('b', 7,new Peao (tabuleiro, Cor.BLACK));
		colocarNovaPeca('c', 7,new Peao (tabuleiro, Cor.BLACK));
		colocarNovaPeca('d', 7,new Peao (tabuleiro, Cor.BLACK));
		colocarNovaPeca('e', 7,new Peao (tabuleiro, Cor.BLACK));
		colocarNovaPeca('f', 7,new Peao (tabuleiro, Cor.BLACK));
		colocarNovaPeca('g', 7,new Peao (tabuleiro, Cor.BLACK));
		colocarNovaPeca('h', 7,new Peao (tabuleiro, Cor.BLACK));
		colocarNovaPeca('c', 8,new Bispo (tabuleiro, Cor.BLACK));
		colocarNovaPeca('f', 8,new Bispo (tabuleiro, Cor.BLACK));



	}
	
	
	
}
