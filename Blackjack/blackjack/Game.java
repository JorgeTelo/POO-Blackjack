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
		Deck GameDeck = new Deck(shoe);
		Player p1 = new Player(init_balance, min_bet, GameDeck);/*starting shuffled deck*/
		Dealer dealer = new Dealer(GameDeck);
		Scanner scan = null; /*reads from terminal*/
		
		/*game is initiated so state goes to INIT*/
		this.state = INIT;
		
		/*while the state doesn't go into quit, which means player doesn't quit, keep going*/
		while(state != QUIT) {
			scan = new Scanner(System.in);
			String cmdln = scan.nextLine();
			char cmd;
			try {
				cmd = cmdln.charAt(0);
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
						//amount = Integer.parseInt(bet);
						//this.betting(p1, amount);
						//System.out.println("player is betting " + amount);
						/*
						 * if cmdln string is bigger than 1, it means that the command was b 'amount'.
						 * this for loop grabs the amount to bet
						 */
					}
					//p1.showHand(p1.hand); /*works*/
					try {
						amount = Integer.parseInt(bet);
						if((amount >= min_bet) && (amount <= max_bet) && (p1.getBalance() - amount >= 0)) {
							this.betting(p1, amount);
							break;
						}else {
							System.out.println("b: Illegal amount");
							break;
						}
					}catch(NumberFormatException e) {
						System.out.println("b: Illegal amount");
					}
				}else {
					amount = p1.getPrevious();
					this.betting(p1, amount);
				}
				//System.out.println("Betting");
				break;
			//current balance
			case '$':
				int balance = p1.getBalance();
				System.out.println("player current balance is " + balance);
				break;
			//deal
			case 'd':
				this.dealing(dealer, p1, GameDeck);
				//System.out.println("Dealing");
				break;
			//hit
			case 'h':
				this.hitting(p1, GameDeck);
				//System.out.println("Hitting");
				break;
			//stand
			case 's':
				System.out.println("player stands");
				this.setState(DEAL);
				while(this.getState()==DEAL) {
					
				}
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
			//player quits
			case 'q':
				this.toquit(p1);
				break;
			}
			
			switch(cmdln) {
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
<<<<<<< HEAD
=======

	public void simulation(int min_bet, int max_bet, int init_balance, int shoe, int shuffle, int s_number, String strategy) {
		Deck GameDeck = new Deck(shoe);
		Player p1 = new Player(init_balance, min_bet, GameDeck);/*starting shuffled deck*/
		
		//For now, Dealer will act as player 2
		Player d = new Player(init_balance, min_bet, GameDeck);
		//

		/*game is initiated so state goes to INIT*/
		this.state = INIT;


		System.out.println(strategy);

		//while(state != QUIT) {

			//IMPLEMENT BASIC STRATEGY
			//table1 = hard, 2=soft, 3=pairs 
			int table = 1;
					
			p1.showHand(p1.hand);
			System.out.println("Dealer's hand: ");
			d.showHand(d.hand);

			int dealerScore = d.handscore();
			System.out.println("dealerScore" dealerScore);
					

			int a = p1.basicStrategy(dealerScore);
			System.out.println(a);
		//}
	}
>>>>>>> 5974f941dbd2e6a4f1b0979f3a43be29a96693a1
}
