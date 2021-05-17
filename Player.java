package base;

public class Player {
	/* Fields */
	private static int activeplayers = 1; /* determines what is the number of the new Player object */
	public int playernum; /*number of the current player, max 8*/
	private float curr_balance;
	private String hand;
	
	/*Constructors*/
	/*to use when initialising the new player*/
	Player(int b){
		playernum = activeplayers++;
		curr_balance = b;
		System.out.print("Player number " + playernum);
		System.out.println(" initiated with balance of " + b);
		
		
	}
	/*to use middle of game, only changes current balance and current hand*/
	Player(int b, String s){
		curr_balance = b;
		hand = s;
		
	}
	
	/*Methods*/
	/*Adds the value of a bet if players wins*/
	public void Add_balance(int change) {
		this.curr_balance += change;
		
	}
	/*Subtracts the value of a bet if players wins*/
	public void Subtract_balance(int change) {
		this.curr_balance += change;
	}
	/*Adds new card to current hand*/
	public void Add_cardtohand(String curr_hand, String added_card) {
		String new_hand = curr_hand + added_card;
		
		added_card ="";
		curr_hand = new_hand;
		
	}
	/*Cleans current hand when players gets bust, surrenders or round is over*/
	public void clear_hand(String curr_hand) {
		curr_hand = "";
	}
	
}
