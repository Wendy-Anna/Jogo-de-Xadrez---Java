package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner (System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>();
		
		
		while(true) {
			try {
				UI.limparTela();
				UI.printPartida(partidaXadrez, capturadas);
				System.out.println();
				System.out.println("Origem: ");
				XadrezPosicao origem = UI.leiaPosicaoXadrez(sc);
				
				boolean [][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
				UI.limparTela();
				UI.printTabuleiro(partidaXadrez.getPecas(), possiveisMovimentos);
				System.out.println();
				System.out.println("Destino: ");
				XadrezPosicao destino = UI.leiaPosicaoXadrez(sc);
				
				PecaXadrez pecaCapturada = partidaXadrez.performanceMovimentoXadrez(origem, destino);
				
				if(pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
				
			}
			catch(XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
				
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
				
			}
		}
		

	}

}
