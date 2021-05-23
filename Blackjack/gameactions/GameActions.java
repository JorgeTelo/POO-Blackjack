package gameactions;

import cards.*;

import player.*;
import java.text.*;
import java.util.LinkedList;


public abstract class GameActions {
	/*This class presents all necessary methods for the game*/
	
	/*Game States
	 * Used to prevent illegal actions
	 */
	
	protected final static int INIT = 0; /**tart of the game*/
	protected final static int BET = 1; /**bets are placed*/
	protected final static int PLAY = 2; /**player turn*/
	protected final static int STAND = 3; /**player stands or busts*/
	protected final static int DEAL = 4; /**dealer turn*/
	protected final static int SHOWDOWN = 5; /**game goes into showdown*/
	protected final static int SIMULATION = 6; /**only for simulation mode*/
	protected final static int QUIT = 7; /**player quits*/
	protected int state = INIT; /**first state is 0*/
	
	private int handsPlayer; /*numbers of hands obtained by the player*/
	private int handsDealer; /*numbers of hands obtained by the dealer*/
	/** player's current hand, in case of split*/
	protected int currentHand;
	/*
	 * Deck currently being used
	 */
	
	/**Game Deck*/
	protected Deck GameDeck;
	LinkedList<Card> splitHand1 = new LinkedList<Card>();
	LinkedList<Card> splitHand2 = new LinkedList<Card>();
	LinkedList<Card> splitHand3 = new LinkedList<Card>();
	LinkedList<Card> splitHand4 = new LinkedList<Card>();
	
	/*
	 * Methods to get information in case it's needed
	 */
	/**
	 * Get Deck
	 * @return The current state of the Deck
	 */
	public Deck getDeck() {
		return GameDeck;
	}
	
	/**
	 * Get Game State
	 * @return current state
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * Set Game State
	 * @param new_state state to go to
	 */
	public void setState(int new_state) {
		this.state = new_state;
	}
	
	/**
	 * Deck Shuffle
	 * @param shoe number of shoes
	 * 
	 * Method to re-shuffle GameDeck when shuffle % is reached
	 */
	public void shuffle(int shoe) {
		this.GameDeck = new Deck(shoe);
	}
	
	/**
	 * Increase amount of hands played by the player, to use for statistics
	 */
	public void addPlayerHand() {
		this.handsPlayer++;
	}
	
	/**
	 * Increase amount of hands played by the dealer, to use for statistics
	 */
	public void addDealerHand() {
		this.handsDealer++;
	}
	
	/**
	 * Gets Player Current hand
	 * @return current hand being played
	 * Used when splitting
	 */
	public int getCurrent() {
		return this.currentHand;
	}
	
	/**
	 * TO Quit
	 * @param curr_player current player that is quitting
	 * 
	 * Closes the game, exiting completely, have to rerun the command to play again
	 */
	protected void toquit(Player curr_player) {
			System.out.println("Exiting Blackjack Simulator. See you next time!");
			System.exit(0);
	}
	
	/**
	 * Betting Method
	 * @param curr_player Player that made the bet
	 * @param amount Amount being used for bet
	 */
	public void betting(Player curr_player, int amount) {
		if(this.getState() == INIT) {
			curr_player.updatePrevious(amount);
			System.out.println("player is betting " + amount);
			this.setState(BET);
		}else {
			System.out.println("player is betting " + curr_player.getPrevious());
		}
		
		System.out.println("");
	}
	
	/**
	 * Dealing Method
	 * @param curr_dealer Dealer
	 * @param curr_player Player
	 * @param GameDeck Current Deck
	 * @return player's hand score
	 */
	public int dealing(Dealer curr_dealer, Player curr_player, Deck GameDeck) {
		int score = 0;
		if(this.getState() == BET) {
			curr_player.minusBalance(curr_player.getBalance(), curr_player.getPrevious());
			for(int i=0;i<2;i++) {
				curr_dealer.Add_cardtohand(GameDeck);
			}
			for(int i=0;i<2;i++) {
				curr_player.Add_cardtohand(GameDeck);
			}
			this.setState(PLAY);
			curr_dealer.showDealer(curr_dealer.hand, this.getState());
			score = curr_player.showHand(curr_player.hand);
			this.addDealerHand();
			//System.out.println("Number of hands held by dealer " + this.handsDealer);
			this.addPlayerHand();
			//System.out.println("Number of hands held by player " + this.handsPlayer);
		}else {
			this.illegalCommand('d');
		}
		System.out.println("");
		return score;
	}
	
	/*
	 * Method that automatically prints illegal commands depending on what char is introduced
	 */
	

	/**
	 * Hitting Method
	 * @param curr_player player that is hitting
	 * @param GameDeck Current Game Deck
	 * @return player's hand score
	 */
	public int hitting(Player curr_player, Deck GameDeck) {
		int score = 0;
		if(this.getState() == PLAY) {
			
			System.out.println("player hits");
			curr_player.Add_cardtohand(GameDeck);
			score = curr_player.showHand(curr_player.hand);
			
		}else {
			this.illegalCommand('h');
			System.out.println("");
		}
		
		if (score > 21) {
				System.out.println("player busts");
				this.setState(STAND);	
		}
		return score;
	}
	
	/**
	 * Standing method
	 * @param dealer Dealer 
	 * @param curr_player Player
	 * @param pscore player's hand score
	 * @return player's score
	 */
	public int standing(Dealer dealer, Player curr_player, int pscore) {
		int score = 0;
		int pbj = 0;
		while(this.getState()==DEAL) {
			score = dealer.showDealer(dealer.hand, this.getState());
			if(pscore == 21 && curr_player.hand.size() == 2 && score <21 && dealer.hand.size() == 2) {
				pbj = 1;
				this.setState(SHOWDOWN);
			}else if(score > 21) {
				if(currentHand == 0)
				System.out.println("dealer busts");
				this.setState(SHOWDOWN);
			}
			if(score >= 17 && score <= 21) {
				System.out.println("dealer stands");
				this.setState(SHOWDOWN);
			}else if(score < 17 && pbj != 1){
				System.out.println("dealer hits");
				dealer.Add_cardtohand(GameDeck);
			}					
		}
		return score;
	}
	
	/**
	 * Showdown Method
	 * @param pscore player's total score at the end of the turn
	 * @param dscore dealer's total score at the end of the turn
	 * @param curr_player Player object
	 * @param dealer Dealer
	 * @param insured if player has insured or not
	 */
	public void showdown(int pscore, int dscore, Player curr_player, Dealer dealer, Boolean insured) {
		if(insured == false) {	
			if(pscore > 21) { /*bust*/
				curr_player.addLose();
				System.out.println("player loses and his current balance is " + curr_player.getBalance());
			}
			else if(pscore < 22 && dscore > 21) { /*dealer busts, player doesn't*/
				curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()*2));
				curr_player.addWin();
				System.out.println("player wins and his current balance is " + curr_player.getBalance());
			}else if(pscore == 21 && curr_player.hand.size() == 2) { /*player blackjack*/
				curr_player.addPBJ();
				if(dscore != 21) { /*auto win, dealer has no blackjack*/
					curr_player.addWin();
					System.out.println("blackjack!!");
					curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()*2.5));
					System.out.println("player wins and his current balance is " + curr_player.getBalance());
				}else if(dscore == 21 && dealer.hand.size() == 2) { /*dealer has blackjack as well*/
					dealer.addDBJ();
					curr_player.addPush();
					System.out.println("blackjack!!");
					curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()));
					System.out.println("player pushes and his current balance is " + curr_player.getBalance());
				}
			}else if(dscore == 21 && dealer.hand.size() == 2 && pscore < 22) { /*dealer has blackjack, but player doesn't*/
				System.out.println("blackjack!!");
				curr_player.addLose();
				System.out.println("player loses and his current balance is " + curr_player.getBalance());
			}else if(pscore > dscore) { /*player has more value on his hand than dealer, without busts*/
				curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()*2));
				curr_player.addWin();
				System.out.println("player wins and his current balance is " + curr_player.getBalance());
			}else if(pscore == dscore) { /*player pushes*/
				curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()));
				curr_player.addPush();
				System.out.println("player pushes and his current balance is " + curr_player.getBalance());
			}else {/*dealer has higher valued hand*/
				curr_player.addLose();
				System.out.println("player loses and his current balance is " + curr_player.getBalance());
			}
		}else { /*player is insured*/
			if(pscore > 21) { /*bust*/
				curr_player.addLose();
				System.out.println("player loses and his current balance is " + curr_player.getBalance());
			}
			else if(pscore < 22 && dscore > 21) { /*dealer busts, player doesn't*/
				curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()));
				curr_player.addWin();
				System.out.println("player wins and his current balance is " + curr_player.getBalance());
			}else if(pscore == 21 && curr_player.hand.size() == 2) { /*player blackjack*/
				curr_player.addPBJ();
				if(dscore != 21) { /*auto win, dealer has no blackjack*/
					curr_player.addWin();
					System.out.println("blackjack!!");
					curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()*1.5));
					System.out.println("player wins and his current balance is " + curr_player.getBalance());
				}else if(dscore == 21 && dealer.hand.size() == 2) { /*dealer has blackjack as well*/
					dealer.addDBJ();
					curr_player.addPush();
					System.out.println("blackjack!!");
					curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()));
					System.out.println("player pushes and his current balance is " + curr_player.getBalance());
				}
			}else if(dscore == 21 && dealer.hand.size() == 2 && pscore < 22) { /*dealer has blackjack, but player doesn't*/
				System.out.println("blackjack!!");
				curr_player.addLose();
				curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()*2));
				System.out.println("player loses and his current balance is " + curr_player.getBalance());
			}else if(pscore > dscore) { /*player has more value on his hand than dealer, without busts*/
				curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()));
				curr_player.addWin();
				System.out.println("player wins and his current balance is " + curr_player.getBalance());
			}else if(pscore == dscore) { /*player pushes*/
				curr_player.plusBalance(curr_player.getBalance(), (curr_player.getPrevious()/2));
				curr_player.addPush();
				System.out.println("player pushes and his current balance is " + curr_player.getBalance());
			}else {/*dealer has higher valued hand*/
				curr_player.addLose();
				System.out.println("player loses and his current balance is " + curr_player.getBalance());
			}
		}
		System.out.println("");
	}
	
	/**
	 * Insurance method
	 * @param justdealt flag to see if a deal was just made
	 * @param curr_player Player Object
	 * @param dealer Dealer object
	 * @param amount 
	 * @return
	 */
	public Boolean insurance(int justdealt, Player curr_player, Dealer dealer) {
		if(justdealt == 1 && Card.cardRank(dealer.hand.getFirst()) == 1) {
			curr_player.minusBalance(curr_player.getBalance(), curr_player.getPrevious());
			System.out.println("");
			return true;
		}else {
			this.illegalCommand('i');
		}
		System.out.println("");
		return false;
		
	}
	
	/**
	 * Surrender method
	 * @param justdealt if hand was just dealt
	 * @param curr_player Player Object
	 * @param dealer Dealer Object
	 * @param insured insured flag
	 */
	public void surrender(int justdealt, Player curr_player, Dealer dealer, Boolean insured) {
		if(justdealt == 1 || justdealt == 2) {
			System.out.println("player is surrendering");
			int dealer_score = dealer.showDealer(dealer.hand, DEAL);
			if(dealer_score == 21 && insured == true) { // no need to put limit on hand size, since dealer will always have 2 cards max on surrender
				System.out.println("blackjack!!");
				curr_player.plusBalance((double)curr_player.getBalance(), (double)curr_player.getPrevious()*2.5);
			}else {
				curr_player.plusBalance((double)curr_player.getBalance(), (double)curr_player.getPrevious()/2);
			}

			System.out.println("player loses and his current balance is " + (double)curr_player.getBalance());
			this.setState(INIT);
			curr_player.clear_hand();
			dealer.clear_hand();
			}else {
			this.illegalCommand('u');
		}
		System.out.println("");
	}
	
	/**
	 * Doubling Down Method
	 * @param curr_player Player Object
	 * @param dealer Dealer Object
	 * @return returns flag Aux for simulation
	 */
	public Boolean doublingdown(Player curr_player, Dealer dealer) {
		//return true if doublingdown is possible, False otherwise
		int player_score = 0;
		int dealer_score = 0;
		/*checks if player hand is 9 or higher*/
		if(curr_player.handscore() >= 9 && curr_player.hand.size() == 2 && this.getState() == PLAY) {
			curr_player.Add_cardtohand(GameDeck);
			player_score = curr_player.showHand(curr_player.hand);
			curr_player.minusBalance(curr_player.getBalance(), curr_player.getPrevious());
			//System.out.println("player balance is after doubling" + curr_player.getBalance());
			curr_player.updatePrevious(curr_player.getPrevious()*2);
			//System.out.println("Player new bet is " + curr_player.getPrevious());
			
			if(player_score > 21) {
				System.out.println("player busts");
			}
			/*runs the same code as if stand was commanded*/
			this.setState(DEAL);
			if(player_score < 22) {
				dealer_score = this.standing(dealer, curr_player, player_score);
			}else {
				dealer_score = dealer.showDealer(dealer.hand, DEAL);
			}
			this.setState(SHOWDOWN);
			this.showdown(player_score, dealer_score, curr_player, dealer, false);
			this.setState(INIT);
			
			curr_player.clear_hand();
			dealer.clear_hand();

			curr_player.updatePrevious(curr_player.getPrevious()/2);
			System.out.println("");
			return true;
			
			
		}
		curr_player.updatePrevious(curr_player.getPrevious()/2);
		System.out.println("");
		return false;
		

	}
	
	
	public int adviceBS(Player curr_player, Dealer dealer) {
		//For Basic advice
		int dealerScoreShowing = dealer.dealerHandScore(6);
		int table = curr_player.getTable(curr_player.hand);
		int nextMoveBS = curr_player.basicStrategy(dealerScoreShowing, table);

		return nextMoveBS;

	}
	
	public int adviceHL(Player curr_player, Dealer dealer, int numberOfShufflesLeft) {
		// For HI-LO advice	
		int playerScore = curr_player.showHand(curr_player.hand);
	
		int runningCount = 0;
		runningCount = runningCount + curr_player.assignValueToRank(curr_player.hand);
		runningCount = runningCount + dealer.assignValueToRankD(dealer.hand);
	
		float trueCount = (runningCount/numberOfShufflesLeft);
	
		int nextMoveHL = curr_player.Illustrious18ANDFab4(trueCount, playerScore, dealer.hand);
		
		return nextMoveHL;
	}
	
	
	/**
	 * Statistics 
	 * @param curr_player Player Object
	 * @param dealer Dealer Object
	 */
	public void statistics(Player curr_player, Dealer dealer) {
		double averagePBJ = 0;
		double averageDBJ = 0;
		double averageWin = 0;
		double averageLose = 0;
		double averagePush = 0;
		double gain = 0;
		
		/*
		 * This NumberFormat class is to present the averages with only 2 decimals
		 */
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(false);
				
		averagePBJ = (double)curr_player.pbj / (double)this.handsPlayer;
		averageDBJ = (double)dealer.dbj / (double)this.handsDealer;
		averageWin = (double)curr_player.win / (double)this.handsPlayer;
		averageLose = (double)curr_player.lose / (double)this.handsPlayer;
		averagePush = (double)curr_player.push / (double)this.handsPlayer;
		gain = curr_player.playerGain();
		
		System.out.println("BJ P/D		" + +curr_player.pbj +"  " + nf.format(averagePBJ) +"/" + nf.format(averageDBJ));
		System.out.println("Win		" + nf.format(averageWin));
		System.out.println("Lose		" + nf.format(averageLose));
		System.out.println("Push		" + nf.format(averagePush));
		System.out.println("Balance		" + curr_player.getBalance() +"(" + gain +"%)");
		System.out.println("");
		
				
	}
	
	/**
	 * Splitting Method
	 * @param splits number of splits avaiable
	 * @param curr_player Player Object
	 * @param hand Player hand LinkedList of Card objects
	 */
	public void splitting(int splits, Player curr_player, LinkedList<Card> hand) {
		
		int currentHand = 0;
		
		if(splits > 3) {
			this.illegalCommand('p');
			return;
		
		}
		
		if(splits == 1) {
			splitHand1 = (LinkedList<Card>) hand.clone();
			splitHand1.removeFirst();
			hand.removeLast();
		
			curr_player.multipleHands[0].add(hand.get(0));
			curr_player.multipleHands[1].add(splitHand1.get(0));
		
			for(int i = 0; i<4; i++) {
			System.out.println(curr_player.multipleHands[i]);

			}
			this.currentHand = 1;
			System.out.println("hand 1");
		}
		/*if(splits == 2) {
			splitHand1 = (LinkedList<Card>) hand.clone();
			splitHand1.removeFirst();
			hand.removeLast();
			
			curr_player.multipleHands[0].add(hand.get(0));
			curr_player.multipleHands[2].add(splitHand1.get(0));
		
			for(int i = 0; i<4; i++) {
			System.out.println(curr_player.multipleHands[i]);

			}
			this.currentHand = 1;
			System.out.println("hand 1");
		}
		if(splits == 3) {
			splitHand1 = (LinkedList<Card>) hand.clone();
			splitHand1.removeFirst();
			hand.removeLast();
		
			curr_player.multipleHands[0].add(hand.get(0));
			curr_player.multipleHands[3].add(splitHand1.get(0));
		
			for(int i = 0; i<4; i++) {
			System.out.println(curr_player.multipleHands[i]);

			}
			this.currentHand = 1;
			System.out.println("hand 1");
		}*/
			
		return;
		

		
	}
	
	/**
	 * Illegal Command printing Method
	 * @param cmd char with the cmd given
	 */
	protected void illegalCommand(char cmd) {
		System.out.println(cmd + ": illegal command");
		return;
	}
}
