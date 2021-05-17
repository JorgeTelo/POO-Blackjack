package base;

public class Cards {
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
	private final static int T = 10;
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
	
	/*Constructor*/
	Cards(int score, int suit, int rank){
		this.score = score;
		this.suit = suit;
		this.rank = rank;
	}
	
	/*Methods required to print the ints into readable format and strings to ints from files*/
	private String RanktoString(int rank) {
		if(rank == T)
			return "10";
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
	public static int StringtoRank(char rank) {
		if(rank == 'T')
			return T;
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
	
	/*toString method for printing*/
	public String toString() {
		String rank = RanktoString(this.rank);
		String suit = SuittoString(this.suit);
		
		return rank+suit;
	}
}
