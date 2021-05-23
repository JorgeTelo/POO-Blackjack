package player;

import java.util.Iterator;
import java.util.LinkedList;

import cards.Card;
import cards.Deck;
import gameactions.*;
import blackjack.*;


/**
 * Dealer Class
 * Contains methods related to Dealer usage
 *
 */
public class Dealer {
	/* Fields */
	protected final static int INIT = 0; /*start of the game*/
	protected final static int BET = 1; /*bets are placed*/
	protected final static int PLAY = 2; /*player turn*/
	protected final static int STAND = 3; /*player stands or busts*/
	protected final static int DEAL = 4; /*dealer turn*/
	protected final static int QUIT = 5; /*players quits*/
	protected final static int SIMULATION = 6; /*only for simulation*/
	protected int state = INIT; /*first state is 0*/
	
	public int dbj;

	public LinkedList<Card> hand = new LinkedList<Card>();
	
	/**
	 * Constructor
	*to use when initializing the new Dealer*/
	public Dealer(){
		/*
		 * Empty constructor, since we add cards to the dealer when dealing
		 */
	}
	
	/*Methods*/
	/**
	 *Add DBJ
	 *increments the dbj parameter, which saves the amount of blackjacks the dealer has received 
	 */
	public void addDBJ() {
		this.dbj++;
	}
	
	/*Adds new card to current hand*/
	/**
	 * Add Card to Dealer Hand
	 * Adds a new drawn card to Dealer Hand
	 * @param GameDeck current gamedeck that is being used
	 */
	public void Add_cardtohand(Deck GameDeck) {
		Card entry = Deck.draw();
		this.hand.add(entry);
		//System.out.println("Current hand: " + hand);
	}
	
	/*Cleans current hand when dealer gets bust or round is over*/
	/**
	 * Clear Hand
	 * clears dealer's hand and fills Discard Pile with cards that were discarded
	 */
	public void clear_hand() {
		for(int i=0; i<this.hand.size();i++) {
			Deck.DiscardDeck.add(this.hand.get(i));
		}
		
		this.hand.clear();
		//System.out.println("Cards added " + Deck.DiscardDeck);
	}
	
	/**
	 * Dealer hand Score
	 * @param gameState current state of the Game
	 * @return total score of the dealer's hand
	 */
	public int dealerHandScore(int gameState) {
		int total = 0;
		int numberOfCards = 0;
		if (gameState == SIMULATION){
			numberOfCards = 1;//(this.hand.size())-1;
		}else{
			numberOfCards = this.hand.size();
		}
		for(int i=0; i<numberOfCards;i++) {
			total = total + Card.cardvalue(hand.get(i));
		}
		return total;
	}
	
	/*
	 * To Do: Show hand
	 * Need to make a method in Card.java to show cards
	 */
	/**
	 * Show Dealer
	 * @param hand LinkedList of cards present in Dealer's hand
	 * @param gameState current state of the Game
	 * @return total score of Dealer's Hand
	 * This method performs differently depending on the game state
	 * If it's called after a Deal, Dealer will have 2 cards, but only show 1 and that one's score, 
	 * and hide the 2nd card and it's score with an X, designated hole card.
	 * If called in any other moment, then it shows the Dealers complete hand, without the hole card
	 * 
	 * This method is also the method that deals with Soft and Hard hands of the Dealer
	 */
	public int showDealer(LinkedList<Card> hand, int gameState) {
		System.out.print("dealer's hand ");
		String cards = "";
		int total = 0;
		int hasace = 0;
		
		if(gameState == PLAY || gameState == SIMULATION) {
			cards = hand.getFirst() + " X"; /*X is the hole card*/
			System.out.println(cards);

		}else if(gameState == DEAL) {
			Iterator<Card> iterator = hand.iterator();
			
			while(iterator.hasNext()) {
				cards = cards +iterator.next() + " ";
			}
			
			for(int i=0; i<hand.size();i++) {
				total += Card.cardvalue(hand.get(i));
			}
			
			for(int i=0; i<hand.size();i++) {
				if(Card.cardRank(hand.get(i))==1){
					hasace++;
				}
			}
			
			if(hand.size() == 2 && hasace >=1 && total + 10 >= 17) {
				total+=10;
				System.out.println(cards + "(" + total + ")");
				return total;
			}
			else if(hasace >=1 && total + 10 <= 21) {
				total+=10;
				System.out.println(cards + "(" + total + ")");
				return total;
			}
			
			
			System.out.println(cards + "(" + total + ")");
			
			
		}
		
		return total;
	}

	/**
	 * Rank Conversion Table
	 * @param rank rank of the Card object
	 * @return
	 * 
	 * Creates the conversion table for the Hi-lo strat
	 */
	public int rankConversionTable(char rank){
		switch(rank){
		case '2' : return 1; 
		case '3' : return 1;
		case '4' : return 1;
		case '5' : return 1; 
		case '6' : return 1;
		case '7' : return 0; 
		case '8' : return 0; 
		case '9' : return 0; 
		case '1' : return -1; 
		case 'J' : return -1; 
		case 'Q' : return -1; 
		case 'K' : return -1; 
		case 'A' : return -1;
		}
		return 0;
	}
	
	/**
	 * Assigns Value to Rank
	 * @param hand LinkedList of Card objects
	 * @return Running Count for Hi-lo
	 */
	public int assignValueToRankD(LinkedList<Card> hand){
		int auxRunningCount = 0;
		int aux = 0;
		String card = "";

		Iterator<Card> iterator = hand.iterator();
		
		while(iterator.hasNext()) {
			card = "";
			card = card + iterator.next();
			aux = rankConversionTable(card.charAt(0));
			auxRunningCount = auxRunningCount + aux;
		}

		return auxRunningCount;
	}

	/**
	 * Count F&A Dealer
	 * @param hand LinkeList of Card objects
	 * @return number of aces and fives in the Dealer's hand
	 */
	public int countFivesAndAcesDealer(LinkedList<Card> hand){
		String cards = "";
		int counter = 0;
		
		Iterator<Card> iterator = hand.iterator();
		
		while(iterator.hasNext()) {
			cards = cards +iterator.next() + " ";
		}
		for (int i=0; i<cards.length(); i++){
			if (cards.charAt(i) == '5') counter++;
			if (cards.charAt(i) == 'A') counter--;
		}

		return counter;
	}
	
}
