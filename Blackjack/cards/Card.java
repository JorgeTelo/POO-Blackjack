package cards;

/**
 * This class is used to represent all the Cards that will be used
 * Presents methods required for Card object management
 */
public class Card {
	/*Fields*/
	public int score;
	public int suit;
	public int rank;
	
	/*Ranks
	 * face number = rank
	 * 10 = T
	 * A = 1 or 11, changed depending on play
	 * J = 11
	 * Q = 12
	 * K = 13
	 */
	private final static int A = 1;
	private final static int J = 11;
	private final static int Q = 12;
	private final static int K = 13;
	
	
	/*Suits determined by number for easier inspection
	 * 1 = S
	 * 2 = H
	 * 3 = D
	 * 4 = C
	 */
	private final static int S = 1;
	private final static int H = 2;
	private final static int D = 3;
	private final static int C = 4;
	
	/**
	 * Constructor
	 * @param score face value of the card, used for scoring methods
	 * @param suit suit of the card, used for representations
	 * @param rank used to differentiate between cards of same score
	 */

	Card(int score, int suit, int rank){
		this.score = score;
		this.suit = suit;
		this.rank = rank;
	}
	
	/*Methods required to print the ints into readable format and strings to ints from files*/
	/**
	 * Rank to String
	 * @param rank rank of the card being evaluated
	 * @return String containing value of rank, to be used for printing purposes
	 */
	private String RanktoString(int rank) {
		if(rank == J)
			return "J";
		if(rank == Q)
			return "Q";
		if(rank == K)
			return "K";
		if(rank == A)
			return "A";
		else
			return String.valueOf(rank);
	}
	
	/**
	 * String to Rank
	 * @param rank rank of the card being evaluated, as a char
	 * @return value of the rank of the card evaluated
	 */
	public static int StringtoRank(char rank) {
		if(rank == 'J')
			return J;
		if(rank == 'Q')
			return Q;
		if(rank == 'K')
			return K;
		if(rank == 'A')
			return A;
		else
			return Character.getNumericValue(rank);
	}
	
	/**
	 * Suit to String
	 * @param suit suit of the card being evaluated
	 * @return suit value as a String for printing purposes
	 */
	private String SuittoString(int suit) {
		if(suit == S)
			return "S";
		if(suit == H)
			return "H";
		if(suit == D)
			return "D";
		if(suit == C)
			return "C";
		
		return String.valueOf(suit);
	}
	
	/**
	 * String to Suit
	 * @param suit suit of the card being evaluated, as a char
	 * @return value of the Suit for method utilization
	 */
	public static int StringtoSuit(char suit) {
		if(suit == 'S')
			return S;
		if(suit == 'H')
			return H;
		if(suit == 'D')
			return D;
		if(suit == 'C')
			return C;
		return Character.getNumericValue(suit);
	}
	
	/**
	 * Score to String
	 * @param score face value of the card evaluated
	 * @return score as a String for presentation purposes
	 */
	private String ScoretoString(int score) {
		if(score == 1)
			return "1";
		if(score== 2)
			return "2";
		if(score == 3)
			return "3";
		if(score == 4)
			return "4";
		if(score == 5)
			return "5";
		if(score == 6)
			return "6";
		if(score == 7)
			return "7";
		if(score == 8)
			return "8";
		if(score == 9)
			return "9";
		if(score == 10)
			return "10";
		if(score == 11)
			return "11";
		
		return String.valueOf(score);
	}
	
	
	/*toString method for printing*/
	public String toString() {
		String rank = RanktoString(this.rank);
		String suit = SuittoString(this.suit);
		
		return rank+suit;
	}
	
	/**
	 * Card Value: Method to return the face value of a card
	 * @param card card object that is being evaluated
	 * @return score parameter of object Card
	 */
	public static int cardvalue(Card card) {
		int value = 0;
		value = card.score;
		
		
		return value;
	}
	
	/**
	 * Card Rank: Method to return the rank of a card
	 * @param card card object that is being evaluated
	 * @return rank parameter of object Card
	 */
	public static int cardRank(Card card) {
		int rank = 0;
		rank = card.rank;
		
		
		return rank;
	}
}
