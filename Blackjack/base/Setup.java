package base;

public class Setup {
	public static int min_bet;
	public static int max_bet;
	public static int balance;
	public static int shoe;
	public static int shuffle;
	public static int s_number;
	public static String shoe_file;
	public static String cmf_file;
	public static String strat;
	

	public static void main(String[] args) {
		// Switch case to chose what mode to start
		switch(args[0]) {
		case "-i": System.out.println("Interactive mode");
				   min_bet = Integer.parseInt(args[1]);
				   max_bet = Integer.parseInt(args[2]);
				   balance = Integer.parseInt(args[3]);
				   shoe = Integer.parseInt(args[4]);
				   shuffle = Integer.parseInt(args[5]);
				   break;
		case "-d": System.out.println("debug mode");
				   min_bet = Integer.parseInt(args[1]);
		   	       max_bet = Integer.parseInt(args[2]);
		           balance = Integer.parseInt(args[3]);
		           shoe_file = args[4];
		           cmf_file = args[5];
				   break;
		case "-s": System.out.println("simulation mode"); 
				   min_bet = Integer.parseInt(args[1]);
	               max_bet = Integer.parseInt(args[2]);
                   balance = Integer.parseInt(args[3]);
                   shoe = Integer.parseInt(args[4]);
                   shuffle = Integer.parseInt(args[5]);
                   s_number = Integer.parseInt(args[6]);
                   strat = args[7];               
				   break;
		default: System.out.println("Unknown input parameter");
				   break;
		}
		
		
	}

}
