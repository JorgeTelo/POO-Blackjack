package gameactions;

import cards.*;
import player.*;

public abstract class GameActions {
	/*This class presents all necessary methods for the game*/
	
	/*Game States
	 * Used to prevent illegal actions
	 */
	protected final static int INIT = 0; /*start of the game*/
	protected final static int BET = 1; /*bets are placed*/
	protected final static int DEAL = 2; /*dealer turn*/
	protected final static int QUIT = 3; /*players quits*/
	protected int state = INIT; /*first state is 0*/
	
	/*
	 * Deck currently being used
	 */
	protected Deck GameDeck;
	
	/*
	 * Methods to get information in case it's needed
	 */
	public Deck getDeck() {
		return GameDeck;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int new_state) {
		this.state = new_state;
	}
	
	public void shuffle(int shoe) {
		this.GameDeck = new Deck(shoe);
	}
	
	protected void toquit(Player curr_player) {
		if(state == INIT || state == BET) {
			System.out.println("Player exiting the game");
			state = QUIT;
		}else {
			this.illegalCommand('q');
		}
	}
	
	public void betting(Player curr_player, int amount) {
		if(state == INIT || state == BET) {
			curr_player.updatePrevious(amount);
			System.out.println("player is betting " + amount);
			this.setState(BET);
		}else {
			System.out.println("player is betting " + curr_player.getPrevious());
		}
	}
	
	public void dealing(Player curr_player, Deck GameDeck) {
		if(state == BET) {
			curr_player.minusBalance(curr_player.getBalance(), curr_player.getPrevious());
			curr_player.Add_cardtohand(GameDeck);
			curr_player.showHand(curr_player.hand);
			System.out.println("Deck now has " + Deck.deckSize() + " cards in it");
		}
	}
	
	/*
	 * Method that automatically prints illegal commands depending on what char is introduced
	 */
	
	protected void illegalCommand(char cmd) {
		System.out.println(cmd + ": illegal command");
		return;
	}
}
