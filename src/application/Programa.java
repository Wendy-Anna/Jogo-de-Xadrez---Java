package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner (System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while(true) {
			try {
				UI.limparTela();
				UI.printTabuleiro(partidaXadrez.getPecas());
				System.out.println();
				System.out.println("Origem: ");
				XadrezPosicao origem = UI.leiaPosicaoXadrez(sc);
				
				boolean [][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
				UI.limparTela();
				UI.printTabuleiro(partidaXadrez.getPecas(), possiveisMovimentos);
				System.out.println();
				System.out.println("Destino: ");
				XadrezPosicao destino = UI.leiaPosicaoXadrez(sc);
				
				PecaXadrez capturePeca = partidaXadrez.performanceMovimentoXadrez(origem, destino);
				
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
