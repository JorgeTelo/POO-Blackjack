package gameactions;

import cards.*;

import player.*;
import java.text.*;


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
	protected final static int SHOWDOWN = 5; /*game goes into showdown*/
	protected final static int SIMULATION = 6; /*only for simulation mode*/
	protected final static int QUIT = 7; /*player quits*/
	protected int state = INIT; /*first state is 0*/
	
	private int handsPlayer; /*numbers of hands obtained by the player*/
	private int handsDealer; /*numbers of hands obtained by the dealer*/
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
	
	public void addPlayerHand() {
		this.handsPlayer++;
	}
	
	public void addDealerHand() {
		this.handsDealer++;
	}
	
	protected void toquit(Player curr_player) {
			System.out.println("Exiting Blackjack Simulator. See you next time!");
			System.exit(0);
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
		return score;
	}
	
	/*
	 * Method that automatically prints illegal commands depending on what char is introduced
	 */
	

	
	public int hitting(Player curr_player, Deck GameDeck) {
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
		return score;
	}
	
	public int standing(Dealer dealer, Player curr_player, int pscore) {
		int score = 0;
		int pbj = 0;
		while(this.getState()==DEAL) {
			score = dealer.showDealer(dealer.hand, this.getState());
			if(pscore == 21 && curr_player.hand.size() == 2 && score <21 && dealer.hand.size() == 2) {
				pbj = 1;
				this.setState(SHOWDOWN);
			}else if(score > 21) {
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
	}
	
	public Boolean insurance(int justdealt, Player curr_player, Dealer dealer, int amount) {
		if(justdealt == 1 && Card.cardRank(dealer.hand.getFirst()) == 1) {
			curr_player.minusBalance(curr_player.getBalance(), curr_player.getPrevious());
			return true;
		}else {
			this.illegalCommand('i');
		}
		
		return false;
		
	}
	
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
	}
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

			return true;
			
		}
		curr_player.updatePrevious(curr_player.getPrevious()/2);
		return false;
		

	}
	
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
		
				
	}
	
	
	protected void illegalCommand(char cmd) {
		System.out.println(cmd + ": illegal command");
		return;
	}
}
