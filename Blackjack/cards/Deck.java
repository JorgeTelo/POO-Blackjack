package cards;

import java.util.*; /*Imports all funtions of java.util*/

public class Deck {
	/*Fields*/
	public static LinkedList<Card> GameDeck = new LinkedList<Card>();
	
	/*Creating Unshuffled Decks for Interactive mode*/
	public Deck(int shoe) {
		Card[][] Decks = new Card[shoe][52];
		// total = 0;
		int index;
		int score;
		
		for(int i=0;i<shoe;i++) {
			index = 0;
			for(int s=1; s<=4; s++) {
				for(int rank=1; rank<14; rank++) {
					if(rank < 11) {
						score = rank;
						Card card = new Card(score, s, rank); /* If it's a number or Ace, automatically adds their value, Ace considered 1 for now*/
						Decks[i][index] = card;
						//++total;
						++index;
					} else {
						score = 10;
						Card card = new Card(score,s, rank); /*If it's a figure, it uses this statement since figures are 10 points*/
						//++total;
						Decks[i][index] = card;
						++index;
					}
				}
			}
		}
		
		//for(int i=0; i<shoe;i++) {
			//System.out.println(total + " cards in Deck " + (i+1));
			//for(int a = 0; a<52;a++) {
				//System.out.println("Card " + Decks[i][a]+ " in position " + a + " with value " + Decks[i][a].score);
			//}
		//}
		for(int i=0;i<shoe;i++) {
			for(int a=0;a<52;a++) {
				GameDeck.add(Decks[i][a]);
			}
		}
		Collections.shuffle(GameDeck);
		System.out.println("Shuffling the shoe ...");
		//stem.out.println("Shuffled deck " + GameDeck.get(5));
	}


	//Drawing cards from Deck
	public static Card draw() {
		Card card_out = new Card(0,0,0); /*empty card, to be changed later in this method*/
		try {
			//System.out.println("Card drawn " + GameDeck.getFirst());
			card_out = GameDeck.pop();
		}catch(Exception e) {
			System.out.println("Deck is empty!");
			System.out.println("Closing game!");
			System.exit(0);
			/*if no card was popped, then game closes due to error*/
		}
		return card_out;
	}
	 
	
}
