package application;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner (System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while(true) {
			UI.printTabuleiro(partidaXadrez.getPecas());
			System.out.println();
			System.out.println("Origem: ");
			XadrezPosicao origem = UI.leiaPosicaoXadrez(sc);
			
			System.out.println();
			System.out.println("Destino: ");
			XadrezPosicao destino = UI.leiaPosicaoXadrez(sc);

			PecaXadrez capturePeca = partidaXadrez.performanceMovimentoXadrez(origem, destino);
			
			
		}
		

	}

}
