package blackjack;

import java.util.Scanner;

import cards.Deck;
import gameactions.GameActions;
<<<<<<< HEAD
import gameactions.GameActions;
=======
import player.Dealer;
import player.Player;

>>>>>>> 33ced9ceb3ce0b75c34b9d2c4cf23702da0e7d47

/**This class is the game development class
 * which has all the modes on how they run
 */

<<<<<<< HEAD


=======
/**
 * Game Class
 * Contains the important game running algorithms
 * Extends GameActions, which contains the rules and how they influence the game
 *
 */
>>>>>>> 33ced9ceb3ce0b75c34b9d2c4cf23702da0e7d47
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
		Deck GameDeck = new Deck(shoe);
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
				Deck.shuffle();
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
				justdealt = 1;
				player_score = this.dealing(dealer, p1, GameDeck);
				//System.out.println("Dealing");
				break;
			//hit
			case 'h':
				justdealt = 0;
				player_score = this.hitting(p1, GameDeck);
				//if(this.getCurrent() == 0) {
					if(player_score >= 21) {
						this.setState(DEAL);
						if(dealer_score < 22) {
						dealer_score = this.standing(dealer, p1, player_score);
						}else {
							dealer_score = dealer.showDealer(dealer.hand, DEAL);
						}
						this.setState(SHOWDOWN);
						this.showdown(player_score, dealer_score, p1, dealer, insured);
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
							dealer_score = this.standing(dealer, p1, player_score);
						}else {
							dealer_score = dealer.showDealer(dealer.hand, DEAL);
						}
						this.setState(SHOWDOWN);
						this.showdown(player_score, dealer_score, p1, dealer, insured);
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
				insured = this.insurance(justdealt, p1, dealer);
				if(insured == true) {
					justdealt = 2;
					//System.out.println("player insured, current balance is" + p1.getBalance() );
				}
				break;
			//surrender
			case 'u':
				this.surrender(justdealt, p1, dealer, insured);
				//System.out.println("Surrendering");
				break;
			//splitting
			case 'p':
				splitable = p1.splitAble(p1.hand);
				
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
				Boolean aux = this.doublingdown(p1, dealer);
				//System.out.println("Doubling Down");
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
				this.statistics(p1, dealer);
				//System.out.println("Statistics");
				break;
			}
		}
	}
	
	public void debug(int min_bet, int max_bet, int init_balance, String shoe_file, String cmd_file) {
		//Deck GameDeck = new Deck(shoe);

		Player p1 = new Player(init_balance, min_bet, GameDeck);/*starting shuffled deck*/
		
		Dealer dealer = new Dealer();
		
		Boolean insured = false;
		
		char[] debugMoves;
		
		File file = new File("C:\\Users\\pedro\\Desktop\\VASCO\\POO-Blackjack\\Blackjack\\cmd-file.txt"); 
		  
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			  
			String string; 
		 
			while ((string = br.readLine()) != null) 
				debugMoves[]
				System.out.println(string); 
		  
		} catch (IOException e) {
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
		Deck GameDeck = new Deck(shoe);

		Player p1 = new Player(init_balance, min_bet, GameDeck);/*starting shuffled deck*/
		
		Dealer dealer = new Dealer();
		
		Boolean insured = false;

		int numberOfShufflesLeft = s_number;
		while(numberOfShufflesLeft > -100){
		
			

			int playerScore = 0;
			int dealerScoreShowing = 0;
			int dealerScoreFull = 0;
			

			this.state = SIMULATION;


			for(int i=0;i<2;i++) {
				p1.Add_cardtohand(GameDeck);
				dealer.Add_cardtohand(GameDeck);
			}

			//this variable tells which action the player should make
			int nextMove = 0;

			//this variable will only be necessary for AceFive
			int aceFiveCount = 0; 
			
			while(state != QUIT) {


				if ( (strategy.equals("BS")) || (strategy.equals("BS-AF")) ){

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

					if (strategy == "BS-AF"){
						aceFiveCount = p1.countFivesAndAcesPlayer(p1.hand);
						aceFiveCount = aceFiveCount + dealer.countFivesAndAcesDealer(dealer.hand);

						if (aceFiveCount >= 2)
							this.betting(p1, p1.getPrevious()*2);
						else 
							this.betting(p1, min_bet);
					}

					// Compute next move for the player
					nextMove = p1.basicStrategy(dealerScoreShowing, table);
					System.out.println("\n" + nextMove);

					

				}else if ( (strategy.equals("HL")) || (strategy.equals("HL-AF")) ){ 
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

					float trueCount = (runningCount/numberOfShufflesLeft);

					nextMove = p1.Illustrious18ANDFab4(trueCount, playerScore, dealer.hand);

					System.out.println("nextMove " + nextMove);

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
							dealerScoreFull = this.standing(dealer, p1, playerScore);
						}else {
							dealerScoreFull = dealer.showDealer(dealer.hand, DEAL);
						}
						
						this.setState(SHOWDOWN);
						this.showdown(playerScore, dealerScoreFull, p1, dealer, insured);
						this.setState(QUIT);
						p1.clear_hand();
						dealer.clear_hand();
						
					}
					break;
				case 2 : 
					this.setState(DEAL);
					if(playerScore < 22) {
						dealerScoreFull = this.standing(dealer, p1, playerScore);
					}else {
						dealerScoreFull = dealer.showDealer(dealer.hand, DEAL);
					}
					this.setState(SHOWDOWN);
					this.showdown(playerScore, dealerScoreFull, p1, dealer, insured);
					this.setState(QUIT);
					numberOfShufflesLeft--;
					p1.clear_hand();
					dealer.clear_hand();
					break;
				case 3 : 
					System.out.println("Splitting");
					this.setState(QUIT);
					p1.clear_hand();
					dealer.clear_hand();
					break;	
				case 4 :
					Boolean doubleIsPossible1 = this.doublingdown(p1, dealer);
					if (doubleIsPossible1 == false){
					//if doubledown was not possible, hit
						playerScore = this.hitting(p1, GameDeck);
						if(playerScore > 21) {
							this.setState(DEAL);
							if(playerScore < 22) {
								dealerScoreFull = this.standing(dealer, p1, playerScore);
							}else {
								dealerScoreFull = dealer.showDealer(dealer.hand, DEAL);
							}
							
							this.setState(SHOWDOWN);
							this.showdown(playerScore, dealerScoreFull, p1, dealer, insured);
							this.setState(QUIT);
							numberOfShufflesLeft--;
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
							dealerScoreFull = this.standing(dealer, p1, playerScore);
						}else {
							dealerScoreFull = dealer.showDealer(dealer.hand, DEAL);
						}
						
						this.setState(SHOWDOWN);
						this.showdown(playerScore, dealerScoreFull, p1, dealer, insured);
						this.setState(QUIT);
						numberOfShufflesLeft--;
						p1.clear_hand();
						dealer.clear_hand();
						
					}	
					break;
				case 6 :
					System.out.println("Surrender");
					this.setState(QUIT);
					p1.clear_hand();
						dealer.clear_hand();
					break;
				case 7 : 
					System.out.println("Switching to BS");
						this.setState(QUIT);

					//strategy = "BS";
					p1.clear_hand();
						dealer.clear_hand();
					break;
				}
			}
		}
		//this.statistics(p1, dealer);

				
	}
	/**
	 * Debug mode
	 */
	
	
}

