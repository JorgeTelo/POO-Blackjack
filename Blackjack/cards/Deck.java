package cards;

import java.util.*; /*Imports all funtions of java.util*/

/**
 * Class Deck
 * Contains Methods related to card drawing, shuffling and getting Deck parameters, like it's size
 */
public class Deck {
	/*Fields*/
	public static LinkedList<Card> GameDeck = new LinkedList<Card>();
	public static LinkedList<Card> DiscardDeck = new LinkedList<Card>();
	
	
	/*Creating Unshuffled Decks for Interactive mode*/
	/**
	 * Constructor: Creates the GameDeck that will be used for the entirety of the game and instantly performs the initial shuffle
	 * @param shoe number of decks used to create the GameDeck LinkedList
	 */
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
	/**
	 * Empty Constructor for Discard pile, to be used on new shuffles
	 * It's empty because Discard Pile starts empty, and is populated as hands are played
	 */
	public Deck() {
	}


	//Drawing cards from Deck
	/**
	 * Draw Card method: used for giving cards to both dealer and players
	 * @return card_out Card object that was drawn from the first position of GameDeck
	 */
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

	/**
	 * Deck Size
	 * Used to determine if a new shuffle is required
	 * @return current size of GameDeck
	 */
	public static int deckSize() {
		return Deck.GameDeck.size();
	}
	 

	/**
	 * Shuffle
	 * When called, empties the DiscardDeck into the GameDeck, and shuffles the refilled GameDeck
	 */
	public static void shuffle() {
		for(int i=0; i<Deck.DiscardDeck.size();i++) {
			Deck.GameDeck.add(Deck.DiscardDeck.get(i));
		}
		
		DiscardDeck.clear();
		Collections.shuffle(GameDeck);
		System.out.println("Shuffling the shoe ...");
	}
	
}
