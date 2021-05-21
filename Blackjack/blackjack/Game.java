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
		Dealer dealer = new Dealer();
		Scanner scan = null; /*reads from terminal*/
		int player_score = 0;
		int dealer_score = 0;
		
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
				double balance = p1.getBalance();
				System.out.println("player current balance is " + balance);
				break;
			//deal
			case 'd':
				player_score = this.dealing(dealer, p1, GameDeck);
				//System.out.println("Dealing");
				break;
			//hit
			case 'h':
				player_score = this.hitting(p1, GameDeck);
				if(player_score > 21) {
					this.setState(DEAL);
					if(player_score < 22) {
					dealer_score = this.standing(dealer);
					}else {
						dealer_score = dealer.showDealer(dealer.hand, DEAL);
					}
					this.setState(SHOWDOWN);
					this.showdown(player_score, dealer_score, p1, dealer);
					this.setState(INIT);
					p1.clear_hand();
					dealer.clear_hand();
				}
				//System.out.println("Hitting");
				break;
			//stand
			case 's':
				if(cmdln.length() < 2) {
					System.out.println("player stands");
					this.setState(DEAL);
					if(player_score < 22) {
						dealer_score = this.standing(dealer);
					}else {
						dealer_score = dealer.showDealer(dealer.hand, DEAL);
					}
					this.setState(SHOWDOWN);
					this.showdown(player_score, dealer_score, p1, dealer);
					this.setState(INIT);
					p1.clear_hand();
					dealer.clear_hand();
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
				//aux is useless here, only used for simulation
				Boolean aux = this.doublingdown(p1, dealer);
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
				this.statisics(p1, dealer);
				//System.out.println("Statistics");
				break;
			}
		}
	}

	public void simulation(int min_bet, int max_bet, int init_balance, int shoe, int shuffle, int s_number, String strategy) {
		Deck GameDeck = new Deck(shoe);

		Player p1 = new Player(init_balance, min_bet, GameDeck);/*starting shuffled deck*/
		
		Dealer dealer = new Dealer();

		int playerScore = 0;
		int dealerScoreShowing = 0;
		int dealerScoreFull = 0;
		

		this.state = SIMULATION;

		for(int i=0;i<2;i++) {
			p1.Add_cardtohand(GameDeck);
			dealer.Add_cardtohand(GameDeck);
		}


		int nextMove = 0;
		
		while(state != QUIT) {

			switch(strategy) {
			case "BS" :
				// Shows player hand
				p1.showHand(p1.hand);

				// Shows dealer's hand
				int auxDL = dealer.showDealer(dealer.hand, this.getState());

				// get the dealer's card that is showing
				dealerScoreShowing = dealer.dealerHandScore(this.getState());
				dealerScoreFull = dealer.dealerHandScore(0);//since we pass zero, it will not read as simulation mode
				System.out.println("Dealer score : " + dealerScoreShowing + " Full : " + dealerScoreFull);

				//computes which table (1,2 or 3) we will use
				int table = p1.getTable(p1.hand);

				// Compute next move for the player
				nextMove = p1.basicStrategy(dealerScoreShowing, table);
				System.out.println("\n" + nextMove);

				
				break;
			case "BS-AF" : 
				break;
			case "HL" : 
				// Shows player hand
				playerScore = p1.showHand(p1.hand);

				// Shows dealer's hand
				int auxHL = dealer.showDealer(dealer.hand, DEAL);

				// get the dealer's card that is showing
				dealerScoreShowing = dealer.dealerHandScore(this.getState());
				dealerScoreFull = dealer.dealerHandScore(0);//since we pass zero, it will not read as simulation mode
				System.out.println("Dealer score : " + dealerScoreShowing + " Full : " + dealerScoreFull);

				int runningCount = 0;

				runningCount = runningCount + p1.assignValueToRank(p1.hand);
				runningCount = runningCount + dealer.assignValueToRankD(dealer.hand);
				System.out.println("\n\nRunning count" + runningCount);

				//HOW TO GET NUMBER OF DECKS REMAINING???
				int numberOfDecksRemaining = s_number-1;

				float trueCount = (runningCount/numberOfDecksRemaining);

				nextMove = p1.Illustrious18ANDFab4(trueCount, playerScore, dealer.hand);

				System.out.println("nextMove " + nextMove);

				break;
			case "HL-AF" :
				break;

			}
			this.setState(PLAY);
			switch(nextMove){
			//1 - hit
			//2 - stand
			//3 - split
			//4 - double if possible, else hit
			//5 - double if possible, else stand
			//6 - surrender if possible, else hit
			//7 - basic strategy

			case 1 :
				playerScore = this.hitting(p1, GameDeck);
				if(playerScore > 21) {
					this.setState(DEAL);
					if(playerScore < 22) {
						dealerScoreFull = this.standing(dealer);
					}else {
						dealerScoreFull = dealer.showDealer(dealer.hand, DEAL);
					}
					this.setState(SHOWDOWN);
					this.showdown(playerScore, dealerScoreFull, p1, dealer);
					this.setState(QUIT);
					p1.clear_hand();
					dealer.clear_hand();
				}
				break;
			case 2 : 
				this.setState(DEAL);
				if(playerScore < 22) {
					dealerScoreFull = this.standing(dealer);
				}else {
					dealerScoreFull = dealer.showDealer(dealer.hand, DEAL);
				}
				this.setState(SHOWDOWN);
				this.showdown(playerScore, dealerScoreFull, p1, dealer);
				this.setState(QUIT);
				p1.clear_hand();
				dealer.clear_hand();
				break;
			case 3 : 
				System.out.println("Splitting");
				this.setState(QUIT);
				break;	
			case 4 :
				Boolean doubleIsPossible1 = this.doublingdown(p1, dealer);
				if (doubleIsPossible1 == false){
				//if doubledown was not possible, hit
					playerScore = this.hitting(p1, GameDeck);
					if(playerScore > 21) {
						this.setState(DEAL);
						if(playerScore < 22) {
							dealerScoreFull = this.standing(dealer);
						}else {
							dealerScoreFull = dealer.showDealer(dealer.hand, DEAL);
						}
						this.setState(SHOWDOWN);
						this.showdown(playerScore, dealerScoreFull, p1, dealer);
						this.setState(QUIT);
						p1.clear_hand();
						dealer.clear_hand();
					}
				}
				break;
			case 5 :
				Boolean doubleIsPossible2 = this.doublingdown(p1, dealer);
				if(doubleIsPossible2 == false){
					this.setState(DEAL);
					if(playerScore < 22) {
						dealerScoreFull = this.standing(dealer);
					}else {
						dealerScoreFull = dealer.showDealer(dealer.hand, DEAL);
					}
					this.setState(SHOWDOWN);
					this.showdown(playerScore, dealerScoreFull, p1, dealer);
					this.setState(QUIT);
					p1.clear_hand();
					dealer.clear_hand();
				}	
				break;
			case 6 :
				System.out.println("Nonoo");
				this.setState(QUIT);
				break;
			case 7 : 
				System.out.println("Switching to BS");
				strategy = "BS";
				break;
			}
		}
				
	}
}

