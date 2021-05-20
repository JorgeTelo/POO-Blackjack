package player;

import java.util.*;

import cards.Card;
import cards.Deck;

public class Player {
	/* Fields */
	private static int activeplayers = 1; /* determines what is the number of the new Player object */
	public int playernum; /*number of the current player, max 8*/
	private double curr_balance;
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
		/*
		 * Starting with 2 cards on hand
		 */
		System.out.print("Player number " + playernum);
		System.out.println(" initiated with balance of " + b);
		
		
	}
	/*to use middle of game, only changes current balance and current hand*/
	
	/*Methods*/
	/*Adds the value of a bet if players wins*/
	public void plusBalance(double e, double d) {
		this.curr_balance =  e + d;
		
	}
	/*Subtracts the value of a bet if players wins*/
	public void minusBalance(double d, int change) {
		this.curr_balance = d - change;
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
	
	public double getBalance() {
		return this.curr_balance;
	}
	
	public int getInitialBalance() {
		return this.initial_balance;
	}
	
	public int getPrevious() {
		return this.previousBet;
	}
	
	public void updatePrevious(int amount) {
		this.previousBet = amount;
		return;
	}
	/*
	 * To Do: Show hand
	 * Need to make a method in Card.java to show cards
	 */
	
	public int showHand(LinkedList<Card> hand) {
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
		return total;
	}


	/*Table is 1 for hard, 2 for soft and 3 for pairs*/
	public int getTable(LinkedList<Card> hand){
		int numberOfAces = 0;
		int numberOfPairs = 0;
		String card = "";

		Iterator<Card> iterator = hand.iterator();
		
		while(iterator.hasNext()) {
			card = card + iterator.next();
		}

		char checkPair = card.charAt(0);
		
		for(int i=0; i < card.length(); i++){
			if(card.charAt(i) == 'A')
				numberOfAces++;
			if(card.charAt(i) == checkPair)
				numberOfPairs++;
		} 
		if (numberOfPairs == 2) return 3;
		if (numberOfAces == 0) return 1;
		if (numberOfAces == 1) return 2;
		if (numberOfAces == 2) return 3;
		return 0;
	}

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
			if (dealerScore == 5 || dealerScore==6) return 4;
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
	public int basicStrategy(int dealerScore, int table){
		int playerScore = handscore();
		int nextMove = 0;

		if (table == 1){
			nextMove = BShard(playerScore, dealerScore);
		}		
		if (table == 2){
			nextMove = BSsoft(playerScore, dealerScore);
		}
		if (table == 3){
			//TODO
			nextMove = 6;
		}
		return nextMove;
	}
	

	
	
}
