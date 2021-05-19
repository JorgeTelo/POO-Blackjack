package gameactions;

import cards.Deck;

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
}
