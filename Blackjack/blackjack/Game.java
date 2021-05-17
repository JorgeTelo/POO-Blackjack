package blackjack;

import cards.*;
import main.*;
import player.*;

import java.util.*;
import java.io.*;

public class Game {
		/*This class is the game development class
		 * which has all the modes on how they run
		 */
	/*Game states*/
	protected final static int INIT = 0; /*start of the game*/
	protected final static int BET = 1; /*bets are placed*/
	protected final static int DEAL = 2; /*dealer turn*/
	protected final static int QUIT = 3; /*players quits*/
	protected int state = INIT; /*first state is 0*/
	
	public void interactive(int min_bet, int max_bet, int init_balance, int shoe, int shuffle) {
		Player p1 = new Player(init_balance); /*starting player*/
		Decks GameDeck = new Decks(shoe); /*starting shuffled deck*/
		Scanner scan = null; /*reads from terminal*/
		
		/*game is initiated so state goes to INIT*/
		this.state = INIT;
		
		/*while the state doesn't go into quit, which means player doesn't quit, keep going*/
		while(state != QUIT) {
			scan = new Scanner(System.in);
			String cmd = scan.nextLine();
			try {
				System.out.println("command introduzed is " + cmd);
			}catch(StringIndexOutOfBoundsException e) {
				cmd = "\0";
			}
			
			/*now that we have read the commands from the terminal, we will decide what each does*/
			switch(cmd) {
			//bet
			case "b":

			
				System.out.println("Betting");
				break;
			//current balance
			case "$":
				System.out.println("Checking Balance");
				break;
			//deal
			case "d":
				System.out.println("Dealing");
				break;
			//hit
			case "h":
				System.out.println("Hitting");
				break;
			//stand
			case "s":
				System.out.println("Standing");
				break;
			//insurance
			case "i":
				System.out.println("Insuring");
				break;
			//surrender
			case "u":
				System.out.println("Surrendering");
				break;
			//splitting
			case "p":
				System.out.println("Splitting");
				break;
			//double
			case "2":
				System.out.println("Doubling Down");
				break;
			//advice
			case "ad":
				System.out.println("Advising");
				break;
			//statistics
			case "st":
				System.out.println("Statistics");
				break;
			}
		}
	}
}
