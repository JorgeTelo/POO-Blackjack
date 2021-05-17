package player;

import java.util.LinkedList;
import java.util.List;

import cards.Cards;
import cards.Decks;

public class Player {
	/* Fields */
	private static int activeplayers = 1; /* determines what is the number of the new Player object */
	public int playernum; /*number of the current player, max 8*/
	private float curr_balance;
	public LinkedList<Cards> hand = new LinkedList<Cards>();
	
	/*Constructors*/
	/*to use when initializing the new player*/
	public Player(int b){
		this.playernum = activeplayers++;
		this.curr_balance  = b;
		
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
	public void Add_cardtohand(Decks GameDeck) {
		Cards entry = Decks.draw();
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
			total = total + Cards.cardvalue(hand.get(i));
		}
		return total;
		
	}
	
	
	
}
