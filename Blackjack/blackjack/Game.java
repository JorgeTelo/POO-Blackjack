package blackjack;

import cards.*;
import blackjack.*;
import main.*;
import player.*;
import gameactions.GameActions;

import java.util.*;
import java.io.*;

public class Game extends GameActions{
		/*This class is the game development class
		 * which has all the modes on how they run
		 */
	
	public void interactive(int min_bet, int max_bet, int init_balance, int shoe, int shuffle) {
		Player p1 = new Player(init_balance, min_bet); /*starting player*/
		Deck GameDeck = new Deck(shoe); /*starting shuffled deck*/
		Scanner scan = null; /*reads from terminal*/
		
		/*game is initiated so state goes to INIT*/
		this.state = INIT;
		p1.Add_cardtohand(GameDeck);
		p1.Add_cardtohand(GameDeck);
		p1.Add_cardtohand(GameDeck);
		
		/*while the state doesn't go into quit, which means player doesn't quit, keep going*/
		while(state != QUIT) {
			scan = new Scanner(System.in);
			String cmdln = scan.nextLine();
			char cmd = cmdln.charAt(0);
			try {
				System.out.println("command introduzed is " + cmdln + "with size " + cmdln.length());
			}catch(StringIndexOutOfBoundsException e) {
				cmd = '\0';
			}
			
			/*now that we have read the commands from the terminal, we will decide what each does*/
			switch(cmd) {
			//b
			case 'b':
				int amount = 0;
				if(cmdln.length() > 1) {
					
					String bet = "";
					for(int i = 2; i<cmdln.length();i++) {
						bet += cmdln.charAt(i);
						amount = Integer.parseInt(bet);
						//System.out.println("player is betting " + amount);
						/*
						 * if cmdln string is bigger than 1, it means that the command was b 'amount'.
						 * this for loop grabs the amount to bet
						 */
					}
					p1.showHand(p1.hand);
					System.out.println("player is betting " + amount);
					/*try {
						amount = Integer.parseInt(bet);
						if((amount >= min_bet) && (amount <= max_bet) && (p1.getBalance() - amount >= 0)) {
							
						}else {
							System.out.println("b: Illegal amount");
						}
					}catch(NumberFormatException e) {
						System.out.println("b: Illegal amount");
					}*/
				}
				System.out.println("Betting");
				break;
			//current balance
			case '$':
				System.out.println("Checking Balance");
				break;
			//deal
			case 'd':
				System.out.println("Dealing");
				break;
			//hit
			case 'h':
				System.out.println("Hitting");
				break;
			//stand
			case 's':
				System.out.println("Standing");
				break;
			//insurance
			case 'i':
				System.out.println("Insuring");
				break;
			//surrender
			case 'u':
				System.out.println("Surrendering");
				break;
			//splitting
			case 'p':
				System.out.println("Splitting");
				break;
			//double
			case '2':
				System.out.println("Doubling Down");
				break;
			//advice
			}
			
			switch(cmdln) {
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
