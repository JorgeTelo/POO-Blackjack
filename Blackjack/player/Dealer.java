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

	public LinkedList<Card> hand = new LinkedList<Card>();
	
	/*Constructors*/
	/*to use when initializing the new player*/
	public Dealer(Deck Gamedeck){
		for(int i=0; i<2;i++) {
			Card entry = Deck.draw();
			this.hand.add(entry);
		}
	}
	
	/*Methods*/

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
		
	public int handscore(int gameState) {
		int total = 0;
		int numberOfCards = 0;
		if (gameState == SIMULATION){
			numberOfCards = (this.hand.size())-1;
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
			
			System.out.println(cards + "(" + total + ")");
		}
		
		return total;
	}


	//card counting functions

	/*public BShard(int playerScore, int dealerScore){
		if (playerScore <= 8) return 1;
		if (playerScore == 9){
			if (dealerScore>=3 && dealerScore<=6) return 4;
			return 1;
		}
		if (playerScore == 10){
			if (dealerScore<=9) return 4;
			return 1;
		}
		if (playerScore == 11){
			if (dealerScore<=10) return 4;
			return 1;
		}
		if (playerScore == 12){
			if (dealerScore>=3 && dealerScore <=6) return 2;
			return 1;
		}
		if (playerScore>=13 && playerScore <=16){
			if (dealerScore<=6) return 2;
			if (dealerScore ==10 && playerScore==15) return 6;
			if (dealerScore>=9 && playerScore==16) return 6;
			return 1;
		}
		if (playerScore >= 17) return 2;
	}

	public BSsoft(int playerScore, int dealerScore){
		
	}
	public int basicStrategy(int dealerScore){
	//This functions return an int that tells what
	//the next mode for the player should be
	//1 - hit
	//2 - stand
	//3 - split
	//4 - double if possible, else hit
	//5 - double if possible, else stand
	//6 - surrender if possible, else hit
		int playerScore = handscore();

		if (table == 1)
			int nextMove = BShard(playerScore, dealerScore);
		if (table == 2)
			int nextMove = BSsoft(playerScore, dealerScore);
		
		return nextMove;
	}*/
	
	
	
	
}
