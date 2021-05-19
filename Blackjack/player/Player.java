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
	public Player(int b, int min){
		this.playernum = activeplayers++;
		this.curr_balance  = b;
		this.initial_balance = b;
		this.previousBet = min;
		
		System.out.print("Player number " + playernum);
		System.out.println(" initiated with balance of " + b);
		
		
	}
	/*to use middle of game, only changes current balance and current hand*/
	
	/*Methods*/
	/*Adds the value of a bet if players wins*/
	public void Add_balance(int change) {
		this.curr_balance += change;
		
	}
	/*Subtracts the value of a bet if players wins*/
	public void Subtract_balance(int change) {
		this.curr_balance += change;
	}
	/*Adds new card to current hand*/
	public void Add_cardtohand(Deck GameDeck) {
		Card entry = Deck.draw();
		hand.add(entry);
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
	
	
}
