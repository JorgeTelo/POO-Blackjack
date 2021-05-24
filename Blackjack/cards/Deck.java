package cards;

import java.util.*; /*Imports all funtions of java.util*/
import java.io.*;
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
	public Deck(int shoe, int sim) {
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
		if(sim == 0)
			System.out.println("Shuffling the shoe ...");
		//stem.out.println("Shuffled deck " + GameDeck.get(5));
	}
	/**
	 * Empty Constructor for Discard pile, to be used on new shuffles
	 * It's empty because Discard Pile starts empty, and is populated as hands are played
	 */
	public Deck() {
	}
	
	/**
	 * Constructor for Debug mode
	 * @param show_file file to be read
	 */
	public Deck(String shoe_file) {
		int s, rank, score;
		char cardSuit = '\0';
		char cardRank = '\0';
		int iterator = 0;

		
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader(shoe_file);
			br = new BufferedReader(fr);
			
			String fileLine;
			//fileLine saves the line from the file, since the file is considered 1 big line
			fileLine = br.readLine();
			
			while(iterator < fileLine.length()) {
				if(fileLine.charAt(iterator) == ' ') { //if finds a space, ignore it and move forward
					iterator++;
					continue;
				}
				
				//in the file the rank of the card always comes before the suit, so it's easy to predict
				rank  = Card.StringtoRank(fileLine.charAt(iterator));
				iterator++;
				if(fileLine.charAt(iterator) == '0') {
					//System.out.println("10 was found");
					rank = Card.StringtoRank('T');
					iterator++;
				}
				s = Card.StringtoSuit(fileLine.charAt(iterator));
				iterator++;
				
					if(rank < 11) {
						score = rank;
						Card readCard = new Card(score, s, rank); /* If it's a number or Ace, automatically adds their value, Ace considered 1 for now*/
						GameDeck.addLast(readCard);
					} else {
						score = 10;
						Card readCard = new Card(score,s, rank); /*If it's a figure, it uses this statement since figures are 10 points*/
						GameDeck.addLast(readCard);
					}
			}
		}catch(IOException e){//when file is fully ready, exception breaks the loop
			e.printStackTrace();
		}
		
		//System.out.println("Shuffled deck " + GameDeck);
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
	public static void shuffle(int sim) {
		for(int i=0; i<Deck.DiscardDeck.size();i++) {
			Deck.GameDeck.add(Deck.DiscardDeck.get(i));
		}
		
		DiscardDeck.clear();
		Collections.shuffle(GameDeck);
		if(sim == 0)
			System.out.println("Shuffling the shoe ...");
	}
	
}
