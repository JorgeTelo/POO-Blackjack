package base;

import java.util.LinkedList;
import java.util.Random;

public class decks {
	/*Fields*/
	private LinkedList<Cards> GameDeck = new LinkedList<Cards>();
	
	/*Creating Unshuffled Decks*/
	public decks(int shuffle, int shoe) {
		Cards[][] Decks = new Cards[shoe][52];
		int total = 0;
		Random random = new Random();
		int index;
		int score;
		
		for(int i=0;i<shoe;i++) {
			index = 0;
			for(int s=1; s<=4; s++) {
				for(int rank=1; rank<14; rank++) {
					if(rank < 11) {
						score = rank;
						Cards card = new Cards(score, s, rank); /* If it's a number or Ace, automatically adds their value, Ace considered 1 for now*/
						Decks[i][index] = card;
						++total;
						++index;
					} else {
						score = 10;
						Cards card = new Cards(score,s, rank); /*If it's a figure, it uses this statement since figures are 10 points*/
						++total;
						Decks[i][index] = card;
						++index;
					}
				}
			}
		}
		for(int i=0; i<shoe;i++) {
			System.out.println(total + " cards in Deck " + (i+1));
			for(int a = 0; a<52;a++) {
				System.out.println("Card " + Decks[i][a]+ " in position " + a);
			}
		}
				
	}


	
	
}
