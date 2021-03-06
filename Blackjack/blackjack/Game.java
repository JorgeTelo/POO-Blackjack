
package blackjack;

import cards.*;
import blackjack.*;
import main.*;
import player.*;
import gameactions.GameActions;

import java.util.*;
import java.io.*;



public class Game extends GameActions{
	/**
	 * Interactive Mode
	 * @param min_bet minimum bet 
	 * @param max_bet maximum allowed bet
	 * @param init_balance initial player Balance
	 * @param shoe number of shoe
	 * @param shuffle shuffle % 
	 */
	public void interactive(int min_bet, int max_bet, int init_balance, int shoe, int shuffle) {
		int sim = 0;
		Deck GameDeck = new Deck(shoe, sim);
		Deck DiscardPile = new Deck();
		Player p1 = new Player(init_balance, min_bet, GameDeck);/*starting shuffled deck*/
		Dealer dealer = new Dealer();
		Scanner scan = null; /*reads from terminal*/
		int player_score = 0;
		int[] hand_scores = {0,0,0,0}; /*to save hand scores for splits*/
		int dealer_score = 0;
		Boolean insured = false;
		Boolean splitable = false;
		int justdealt = 0;
		int amount = 0;
		int splits = 0;
		int currentHand = 0;

		
		
		/*game is initiated so state goes to INIT*/
		this.state = INIT;
		
		/*while the state doesn't go into quit, which means player doesn't quit, keep going*/
		while(state != QUIT) {
			
			if(Deck.deckSize() < shoe*shuffle*52/100 && this.getState()==INIT) {
				Deck.shuffle(sim);
			}
			
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
							this.betting(p1, amount, sim);
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
					this.betting(p1, amount, sim);
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
				justdealt = 1;
				player_score = this.dealing(dealer, p1, GameDeck, sim);
				//System.out.println("Dealing");
				break;
			//hit
			case 'h':
				justdealt = 0;
				player_score = this.hitting(p1, GameDeck, sim);
				//if(this.getCurrent() == 0) {
					if(player_score >= 21 || (player_score == 21 && p1.hand.size() == 2)) {
						this.setState(DEAL);
						if(dealer_score < 22) {
						dealer_score = this.standing(dealer, p1, player_score, sim);
						}else {
							dealer_score = dealer.showDealer(dealer.hand, DEAL, sim);
						}
						this.setState(SHOWDOWN);
						this.showdown(player_score, dealer_score, p1, dealer, insured,sim);
						this.setState(INIT);
						p1.clear_hand();
						dealer.clear_hand();
					}
				
				//}else if(this.getCurrent()>0 && this.getCurrent()<splits+2){					
					//if(player_score >= 21) {
						//hand_scores[this.getCurrent()-2] = player_score;
						//player_score = 0;
						//System.out.println("scoreboard  hand 1:" + hand_scores[0]+ "  hand 2:" + hand_scores[1]);
					//}
					
					
				
				
				//System.out.println("scoreboard  hand 1:" + hand_scores[0]+ "  hand 2:" + hand_scores[1]);
				//System.out.println("Hitting");
				break;
			//stand
			case 's':
				if(cmdln.length() < 2) {
					if(this.getState() == PLAY) {
						System.out.println("player stands");
						this.setState(DEAL);
						if(player_score < 22) {
							dealer_score = this.standing(dealer, p1, player_score,sim);
						}else {
							dealer_score = dealer.showDealer(dealer.hand, DEAL, sim);
						}
						this.setState(SHOWDOWN);
						this.showdown(player_score, dealer_score, p1, dealer, insured, sim);
						this.setState(INIT);
						p1.clear_hand();
						dealer.clear_hand();
					}
					else {
						this.illegalCommand('s');
					}
				}
				break;
			//insurance
			case 'i':
				insured = this.insurance(justdealt, p1, dealer, sim);
				if(insured == true) {
					justdealt = 2;
					//System.out.println("player insured, current balance is" + p1.getBalance() );
				}
				break;
			//surrender
			case 'u':
				this.surrender(justdealt, p1, dealer, insured, sim);
				//System.out.println("Surrendering");
				break;
			//splitting
			case 'p':
				splitable = p1.splitAble(p1.hand);
				if(sim == 0)
				System.out.println("Splitting was not implemented");
				if(splitable) {
					/*splits++;
					System.out.println("player is splitting " + splits);
					this.splitting(splits, p1, p1.hand);
					p1.Add_cardtohand(GameDeck);
					p1.showHand(p1.hand);*/					
				}else {
					this.illegalCommand('p');
				}
				//System.out.println("player is splitting");
				

				break;
			//double
			case '2':
				//aux is useless here, only used for simulation
				Boolean aux = this.doublingdown(p1, dealer, sim);
				//System.out.println("Doubling Down");
				break;
			//player quits
			case 'q':
				this.toquit(p1);
				break;
			//advice
			}
			switch(cmdln) {
			case "ad":		
				int cardsLeft = Deck.deckSize();
				int numberofShoesleft = cardsLeft/52;
				
				int nextMoveBS = this.adviceBS(p1,dealer);
				int nextMoveHL = this.adviceHL(p1, dealer, numberofShoesleft, sim);
				
				String basicStrategyMove = p1.convertMoveToString(nextMoveBS);
				String hiloMove = p1.convertMoveToString(nextMoveHL);
				
				System.out.println("basic : \t" + basicStrategyMove);
				System.out.println("hi-lo : \t" + hiloMove);
				break;
			//statistics
			case "st":
				this.statistics(p1, dealer);
				//System.out.println("Statistics");
				break;
			}
		}
	}
	
	public void debug(int min_bet, int max_bet, int init_balance, String shoe_file, String cmd_file) {
		Deck GameDeck = new Deck(shoe_file);

		Player p1 = new Player(init_balance, min_bet, GameDeck);/*starting shuffled deck*/
		
		Dealer dealer = new Dealer();
		
		
		
		File cmdFile = new File(cmd_file); 
		File shoeFile = new File(shoe_file);
		
		int sim = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(cmdFile)); 
			  
			String string; 
		 
			string = br.readLine();
			
			char[] debugMoves = new char[(string.length()/2)+1];

			int j = 0;
			for (int i=0; i < string.length(); i++) {
				if (string.charAt(i) == ' ') {
				}
				else {
					debugMoves[j] = string.charAt(i);
					j++;
				}
			}
			
			//System.out.println(debugMoves);
			
			
			int player_score = 0;
			int dealer_score = 0;
			Boolean insured1 = false;
			Boolean splitable = false;
			int justdealt = 0;
			int amount = 0;
			j= 2;
			StringBuffer auxBet = new StringBuffer();


			for(int i = 0; i < debugMoves.length; i++) {

				System.out.println("-cmd " + debugMoves[i]);

				switch(debugMoves[i]) {
				//b
				case 'b':
					//System.out.println("i = " + i);
					//check if next char after 'b' is a number
					if (Character.isDigit(debugMoves[i+1])) {
						auxBet = new StringBuffer().append(debugMoves[i+1]);
							//if it is, that is the amount the player wants to bet
						while(Character.isDigit(debugMoves[i+j]) == true){
							
							auxBet = new StringBuffer().append(auxBet).append(debugMoves[i+j]);
							j++;
						}
						amount = Integer.parseInt(auxBet.toString());
						//System.out.println("next i = " + i);
						i = i+j-1;
						j=2;
							//skips the number next iteration of the for Loop
						//System.out.println("next i = " + (i+1));

					}else {
						//if next char is not a number, the bet is the minimum bet
						amount = min_bet;
					}
					this.betting(p1, amount, sim);
					auxBet.delete(0, auxBet.length());
					break;
				//current balance
				case '$':
					double balance = p1.getBalance();
					System.out.println("player current balance is " + balance);
					System.out.println("");
					break;
				//deal
				case 'd':
					justdealt = 1;
					player_score = this.dealing(dealer, p1, GameDeck, sim);
					//System.out.println("Dealing");
					break;
				//hit
				case 'h':
					justdealt = 0;
					player_score = this.hitting(p1, GameDeck, sim);
					//if(this.getCurrent() == 0) {
						if(player_score >= 21) {
							this.setState(DEAL);
							if(dealer_score < 22) {
							dealer_score = this.standing(dealer, p1, player_score, sim);
							}else {
								dealer_score = dealer.showDealer(dealer.hand, DEAL, sim);
							}
							this.setState(SHOWDOWN);
							this.showdown(player_score, dealer_score, p1, dealer, insured1, sim);
							this.setState(INIT);
							p1.clear_hand();
							dealer.clear_hand();
						}
					System.out.println("");
					break;
				//stand
				case 's':
					if(this.getState() == PLAY) {
						System.out.println("player stands");
						this.setState(DEAL);
						if(player_score < 22) {
							dealer_score = this.standing(dealer, p1, player_score, sim);
						}else {
							dealer_score = dealer.showDealer(dealer.hand, DEAL, sim);
						}
						this.setState(SHOWDOWN);
						this.showdown(player_score, dealer_score, p1, dealer, insured1, sim);
						this.setState(INIT);
						p1.clear_hand();
						dealer.clear_hand();
					}
					break;
				case 'i':
					insured1 = this.insurance(justdealt, p1, dealer, sim);
					if(insured1 == true) {
						justdealt = 2;
					}
					break;
				case 'u':
					this.surrender(justdealt, p1, dealer, insured1, sim);
					break;
				//splitting
				case 'p':
					splitable = p1.splitAble(p1.hand);
						System.out.println("Splitting was not implemented");
					if(splitable) {
					
					}else {
						this.illegalCommand('p');
					}
					//System.out.println("player is splitting");
					
	
					break;
				//double
				case '2':
					//aux is useless here, only used for simulation
					Boolean aux = this.doublingdown(p1, dealer, sim);
					//System.out.println("Doubling Down");
					break;
				//player quits
				case 'q':
					this.toquit(p1);
					break;
				}
				
				
			}

		} catch (IOException e) {
			//This is when the program doesn't read correctly the files
            e.printStackTrace();
        }
		
		
		
	}


	/**
	 * Simulation mode
	 * @param min_bet minimum bet
	 * @param max_bet maximum bet allowed
	 * @param init_balance initial balance
	 * @param shoe number of shoes
	 * @param shuffle shuffle %
	 * @param s_number number of shuffles until finish
	 * @param strategy strategy to use
	 */
	public void simulation(int min_bet, int max_bet, int init_balance, int shoe, int shuffle, int s_number, String strategy) {
		int sim = 1;
		Deck GameDeck = new Deck(shoe, sim);
		Deck DiscardPile = new Deck();
		Player p1 = new Player(init_balance, min_bet, GameDeck);/*starting shuffled deck*/
		
		Dealer dealer = new Dealer();
		
		Boolean insured = false;

		int numberOfShufflesLeft = s_number;
		int jogadas = 1;
		int roundResult = 0;
		int hasDoubled = 0;


		
		//player starts by betting min_bet
		this.betting(p1, p1.getPrevious(), sim);			
		p1.minusBalance(p1.getBalance(), p1.getPrevious());

			int playerScore = 0;
			int dealerScoreShowing = 0;
			int dealerScoreFull = 0;
			

			this.state = SIMULATION;
			//System.out.println("Jogada n?" + jogadas);

			for(int i=0;i<2;i++) {
				p1.Add_cardtohand(GameDeck);
				dealer.Add_cardtohand(GameDeck);
			}

			//this variable tells which action the player should make
			int nextMove = 0;

			//this variable will only be necessary for AceFive
			int aceFiveCount = 0; 
			
			while(state != QUIT) {
				//previous round was a win
				if(roundResult == 1 && this.getState() == INIT) {
					if(p1.getPrevious()+min_bet <=max_bet) {
						p1.updatePrevious(p1.getPrevious()+min_bet);
						roundResult = 0;
					}
					p1.minusBalance(p1.getBalance(), p1.getPrevious());
					this.betting(p1, p1.getPrevious(), sim);
				}else if(roundResult == 2 && this.getState() == INIT) { //previous round was a lose
					if(p1.getPrevious()-min_bet >= min_bet) {
						p1.updatePrevious(p1.getPrevious()-min_bet);
						roundResult = 0;
					}
					p1.minusBalance(p1.getBalance(), p1.getPrevious());
					this.betting(p1, p1.getPrevious(),sim);
				}else if(roundResult == 3&& this.getState() == INIT) { //previous round was a push
					this.betting(p1, p1.getPrevious(), sim);
					p1.minusBalance(p1.getBalance(), p1.getPrevious());
					roundResult = 0;
				}
				if(Deck.deckSize() < shoe*shuffle*52/100) {
					
					Deck.shuffle(sim);
					numberOfShufflesLeft--;
					//System.out.println("shuffles left " + numberOfShufflesLeft);
				}
				
				if(numberOfShufflesLeft <0) {
					//System.out.println("shuffles left " + numberOfShufflesLeft);
					//System.out.println("Sim has ended");
					state = QUIT;
					break;
				}
				
				if(p1.hand.size()==0) {
					for(int i=0;i<2;i++) {
						p1.Add_cardtohand(GameDeck);
						dealer.Add_cardtohand(GameDeck);
					}
					jogadas++;
					//System.out.println("Jogada n?" + jogadas);
					this.setState(SIMULATION);
				}
				if ( (strategy.equals("BS")) || (strategy.equals("BS-AF")) ){

					// Shows player hand
					p1.showHand(p1.hand, sim);

					// Shows dealer's hand
					int auxDL = dealer.showDealer(dealer.hand, this.getState(), sim);

					// get the dealer's card that is showing
					dealerScoreShowing = dealer.dealerHandScore(SIMULATION);
					dealerScoreFull = dealer.dealerHandScore(0);//since we pass zero, it will not read as simulation mode
					//System.out.println("Dealer score : " + dealerScoreShowing + " Full : " + auxDL);

					//computes which table (1,2 or 3) we will use
					int table = p1.getTable(p1.hand);

					if (strategy == "BS-AF"){
						aceFiveCount = p1.countFivesAndAcesPlayer(p1.hand);
						aceFiveCount = aceFiveCount + dealer.countFivesAndAcesDealer(dealer.hand);

						if (aceFiveCount >= 2)
							this.betting(p1, p1.getPrevious()*2, sim);
						else 
							this.betting(p1, min_bet, sim);
					}

					// Compute next move for the player
					nextMove = p1.basicStrategy(dealerScoreShowing, table);
					//System.out.println("\nNext Move " + nextMove);

					

				}else if ( (strategy.equals("HL")) || (strategy.equals("HL-AF")) ){ 
					// Shows player hand
					playerScore = p1.showHand(p1.hand, sim);

					// Shows dealer's hand
					int auxHL = dealer.showDealer(dealer.hand, DEAL, sim);

					// get the dealer's card that is showing
					dealerScoreShowing = dealer.dealerHandScore(this.getState());
					dealerScoreFull = dealer.dealerHandScore(0);//since we pass zero, it will not read as simulation mode
					//System.out.println("Dealer score : " + dealerScoreShowing + " Full : " + auxHL);

					int runningCount = 0;

					runningCount = runningCount + p1.assignValueToRank(p1.hand);
					runningCount = runningCount + dealer.assignValueToRankD(dealer.hand);
					//System.out.println("\n\nRunning count" + runningCount);

					float trueCount = (runningCount/numberOfShufflesLeft);

					nextMove = p1.Illustrious18ANDFab4(trueCount, playerScore, dealer.hand);

					//System.out.println("nextMove " + nextMove);

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
					playerScore = this.hitting(p1, GameDeck, sim);
					if(playerScore > 21|| (playerScore == 21 && p1.hand.size() == 2)) {
						this.setState(DEAL);
						if(playerScore < 22) {
							dealerScoreFull = this.standing(dealer, p1, playerScore, sim);
						}else {
							dealerScoreFull = dealer.showDealer(dealer.hand, DEAL, sim);
						}
						
						this.setState(SHOWDOWN);
						roundResult =this.showdown(playerScore, dealerScoreFull, p1, dealer, insured, sim);
						this.setState(INIT);
						p1.clear_hand();
						dealer.clear_hand();
						
					}
					break;
				case 2 : 
					this.setState(DEAL);
					if(playerScore < 22) {
						dealerScoreFull = this.standing(dealer, p1, playerScore, sim);
					}else {
						dealerScoreFull = dealer.showDealer(dealer.hand, DEAL, sim);
					}
					this.setState(SHOWDOWN);
					roundResult = this.showdown(playerScore, dealerScoreFull, p1, dealer, insured, sim);
					this.setState(INIT);
					p1.clear_hand();
					dealer.clear_hand();
					break;
				case 3 : 
					//System.out.println("Splitting");
					this.setState(QUIT);
					p1.clear_hand();
					dealer.clear_hand();
					break;	
				case 4 :
					Boolean doubleIsPossible1 = this.doublingdown(p1, dealer, sim);
					if (doubleIsPossible1 == false){
					//if doubledown was not possible, hit
						playerScore = this.hitting(p1, GameDeck, sim);
						if(playerScore > 21) {
							this.setState(DEAL);
							if(playerScore < 22) {
								dealerScoreFull = this.standing(dealer, p1, playerScore, sim);
							}else {
								dealerScoreFull = dealer.showDealer(dealer.hand, DEAL, sim);
							}
							
							this.setState(SHOWDOWN);
							roundResult = this.showdown(playerScore, dealerScoreFull, p1, dealer, insured, sim);
							this.setState(INIT);
							p1.clear_hand();
							dealer.clear_hand();
							
						}
					}
					break;
				case 5 :
					Boolean doubleIsPossible2 = this.doublingdown(p1, dealer, sim);
					if(doubleIsPossible2 == false){
						this.setState(DEAL);
						if(playerScore < 22) {
							dealerScoreFull = this.standing(dealer, p1, playerScore, sim);
						}else {
							dealerScoreFull = dealer.showDealer(dealer.hand, DEAL, sim);
						}
						
						this.setState(SHOWDOWN);
						roundResult = this.showdown(playerScore, dealerScoreFull, p1, dealer, insured, sim);
						this.setState(INIT);
						p1.clear_hand();
						dealer.clear_hand();
						
					}	
					break;
				case 6 :
					//System.out.println("Surrender");
					this.setState(INIT);
					p1.clear_hand();
					dealer.clear_hand();
					break;
				case 7 : 
					//System.out.println("Switching to BS");
						this.setState(INIT);

					//strategy = "BS";
					p1.clear_hand();
						dealer.clear_hand();
					break;
				}
				//System.out.println("Shuffles left" + numberOfShufflesLeft);
			}
		
		this.statistics(p1, dealer);

				
	}
	/**
	 * Debug mode
	 */
	
	
}

