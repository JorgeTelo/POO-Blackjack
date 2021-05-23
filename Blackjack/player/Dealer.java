package player;

import java.util.Iterator;
import java.util.LinkedList;

import cards.Card;
import cards.Deck;
import gameactions.*;
import blackjack.*;

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
	
	/*Constructors*/
	/*to use when initializing the new player*/
	public Dealer(){
		/*
		 * Empty constructor, since we add cards to the dealer when dealing
		 */
	}
	
	/*Methods*/

	public void addDBJ() {
		this.dbj++;
	}
	/*Adds new card to current hand*/
	public void Add_cardtohand(Deck GameDeck) {
		Card entry = Deck.draw();
		this.hand.add(entry);
		//System.out.println("Current hand: " + hand);
	}
	
	/*Cleans current hand when dealer gets bust or round is over*/
	public void clear_hand() {
		this.hand.clear();
	}
		
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
