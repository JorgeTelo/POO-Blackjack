package player;

import java.util.*;

import cards.Card;
import cards.Deck;

public class Player {
	/* Fields */
	private static int activeplayers = 1; /* determines what is the number of the new Player object */
	public int playernum; /*number of the current player, max 8*/
	private int curr_balance;
	private final int initial_balance;
	private int previousBet;
	public LinkedList<Card> hand = new LinkedList<Card>();
	
	/*Constructors*/
	/*to use when initializing the new player*/
	public Player(int b, int min, Deck Gamedeck){
		this.playernum = activeplayers++;
		this.curr_balance  = b;
		this.initial_balance = b;
		this.previousBet = min;
		this.Add_cardtohand(Gamedeck); 
		this.Add_cardtohand(Gamedeck);
		/*
		 * Starting with 2 cards on hand
		 */
		System.out.print("Player number " + playernum);
		System.out.println(" initiated with balance of " + b);
		
		
	}
	/*to use middle of game, only changes current balance and current hand*/
	
	/*Methods*/
	/*Adds the value of a bet if players wins*/
	public void plusBalance(int initial, int change) {
		this.curr_balance = initial + change;
		
	}
	/*Subtracts the value of a bet if players wins*/
	public void minusBalance(int initial, int change) {
		this.curr_balance = initial - change;
	}
	/*Adds new card to current hand*/
	public void Add_cardtohand(Deck GameDeck) {
		Card entry = Deck.draw();
		this.hand.add(entry);
		//System.out.println("Current hand: " + hand);
	}
	
	/*Cleans current hand when players gets bust, surrenders or round is over*/
	public void clear_hand() {
		this.hand.clear();
	}
	
	public String playertoString() {
		return "Player " + this.playernum + " Current Balance : " + this.curr_balance + " With hand " + hand;
			
	}
	
	public int handscore() {
		int total = 0;
		for(int i=0; i<this.hand.size();i++) {
			total = total + Card.cardvalue(hand.get(i));
		}
		return total;
		
	}
	
	public int getBalance() {
		return this.curr_balance;
	}
	
	public int getInitialBalance() {
		return this.initial_balance;
	}
	
	public int getPrevious() {
		return this.previousBet;
	}
	
	/*
	 * To Do: Show hand
	 * Need to make a method in Card.java to show cards
	 */
	
	public void showHand(LinkedList<Card> hand) {
		System.out.print("player's hand ");
		String cards = "";
		int total = 0;
		
		Iterator<Card> iterator = hand.iterator();
		
		while(iterator.hasNext()) {
			cards = cards +iterator.next() + " ";
		}
		
		for(int i=0; i<hand.size();i++) {
			total += Card.cardvalue(hand.get(i));
		}
		
		System.out.println(cards + "(" + total + ")");
		
	}


	//card counting functions

	public int BShard(int playerScore, int dealerScore){
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
		return 2;
	}

	public int BSsoft(int playerScore, int dealerScore){
		if (playerScore<=17){
			if (dealerScore == 5 || deadlerScore==6) return 4;
			if (dealerScore==4 && playerScore>=15) return 4;
			if (dealerScore==3 && playerScore==17) return 4;
			return 1;
		}
		if (playerScore==18){
			if (dealerScore>=3 && dealerScore<=6) return 5;
			if (dealerScore>=9) return 1;
			return 2;
		}
		return 2;
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
		int table = 1;
		int nextMove = 0;

		if (table == 1){
			nextMove = BShard(playerScore, dealerScore);
		}		
		if (table == 2){
			nextMove = BSsoft(playerScore, dealerScore);
		}
		return nextMove;
	}
	
	public void updatePrevious(int amount) {
		this.previousBet = amount;
		return;
	}
	
	
}
