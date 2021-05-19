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
	protected final static int PLAY = 2; /*player turn*/
	protected final static int STAND = 3; /*player stands or busts*/
	protected final static int DEAL = 4; /*dealer turn*/
	protected final static int QUIT = 5; /*players quits*/
	protected final static int SIMULATION = 6; /*only for simulation mode*/
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
		if(this.getState() == INIT || this.getState() == BET) {
			System.out.println("Player exiting the game");
			this.setState(QUIT);
		}else {
			this.illegalCommand('q');
		}
	}
	
	public void betting(Player curr_player, int amount) {
		if(this.getState() == INIT) {
			curr_player.updatePrevious(amount);
			System.out.println("player is betting " + amount);
			this.setState(BET);
		}else {
			System.out.println("player is betting " + curr_player.getPrevious());
		}
	}
	
	public void dealing(Dealer curr_dealer, Player curr_player, Deck GameDeck) {
		if(this.getState() == BET) {
			curr_player.minusBalance(curr_player.getBalance(), curr_player.getPrevious());
			for(int i=0;i<2;i++) {
				curr_dealer.Add_cardtohand(GameDeck);
			}
			for(int i=0;i<2;i++) {
				curr_player.Add_cardtohand(GameDeck);
			}
			this.setState(PLAY);
			int dummy = curr_dealer.showDealer(curr_dealer.hand, this.getState());
			curr_player.showHand(curr_player.hand);
			//System.out.println("Deck now has " + Deck.deckSize() + " cards in it");
		}else {
			this.illegalCommand('d');
		}
		return;
	}
	
	/*
	 * Method that automatically prints illegal commands depending on what char is introduced
	 */
	
	protected void illegalCommand(char cmd) {
		System.out.println(cmd + ": illegal command");
		return;
	}
	
	public void hitting(Player curr_player, Deck GameDeck) {
		int score = 0;
		if(this.getState() == PLAY) {
			
			System.out.println("player hits");
			curr_player.Add_cardtohand(GameDeck);
			score = curr_player.showHand(curr_player.hand);
			
		}else {
			this.illegalCommand('h');
		}
		if (score > 21) {
			System.out.println("player busts");
			this.setState(STAND);
		}
		return;
	}
}
