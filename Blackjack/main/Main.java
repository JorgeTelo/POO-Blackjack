package main;

import player.*;
import blackjack.*;
import cards.*;


public class Main {
	public static int min_bet;
	public static int max_bet;
	public static int balance;
	public static int shoe;
	public static int shuffle;
	public static int s_number;
	public static String shoe_file;
	public static String cmf_file;
	public static String strat;
	public static Player p1; /*8 players max, so we make space */
	public static Player p2;
	public static Player p3;
	public static Player p4;
	public static Player p5;
	public static Player p6;
	public static Player p7;
	public static Player p8;

	

	public static void main(String[] args) {
		/*Switch case to chose what mode to start*/
		if(args.length <5) {
			running_commands();
		}
		
		Game blackjack = new Game();
		/*Now to call the different modes or give warnings*/
		/*Interactive Mode*/
		if(args[0].equals("-i")) {
			if(args.length != 6)
				running_commands();
				
			min_bet = Integer.parseInt(args[1]);
				if(min_bet<1) {
					System.out.println("Minimum bet must be above 1$");
					running_commands();
				}
			max_bet = Integer.parseInt(args[2]);
				if(max_bet < 10*min_bet || max_bet > 20*min_bet) {
					System.out.println("Maximum bet must be 10 times over minimum bet, but below 20 times the value of minimum bet");
					running_commands();
				}
				
			balance = Integer.parseInt(args[3]);
				if(balance<50*min_bet) {
					System.out.println("Entry balance must be 50 times over the minimum bet");
					running_commands();
				}
				
			shoe = Integer.parseInt(args[4]);
			if(shoe <4 || shoe > 8) {
				System.out.println("Shoe number must be inbetween 4 and 8, including those numbers");
				running_commands();
			}
			
			shuffle = Integer.parseInt(args[5]);
			if(shuffle < 10 || shuffle > 100) {
				System.out.println("Shuffle must be between 10% and 100%, including those numbers");
				running_commands();
			}
			blackjack.interactive(min_bet, max_bet, balance, shoe, shuffle);
			
		}
		//Debug Mode
		else if(args[0].equals("-d")) {
			if(args.length != 5)
				running_commands();
			
			min_bet = Integer.parseInt(args[1]);
			if(min_bet<1) {
				System.out.println("Minimum bet must be above 1$");
				running_commands();
			}
		max_bet = Integer.parseInt(args[2]);
			if(max_bet < 10*min_bet || max_bet > 20*min_bet) {
				System.out.println("Maximum bet must be 10 times over minimum bet and 20 times lower");
				running_commands();
			}
			
		balance = Integer.parseInt(args[3]);
			if(balance<50*min_bet) {
				System.out.println("Entry balance must be 50 times over the minimum bet");
				running_commands();
			}
			
			shoe_file = args[4];
			cmf_file = args[5];
		}
		//Simulation Mode
		else if(args[0].equals("-s")) {
			if(args.length != 8)
				running_commands();
			
			min_bet = Integer.parseInt(args[1]);
				if(min_bet<1) {
					System.out.println("Minimum bet must be above 1$");
					running_commands();
				}
			max_bet = Integer.parseInt(args[2]);
				if(max_bet < 10*min_bet || max_bet > 20*min_bet) {
					System.out.println("Maximum bet must be 10 times over minimum bet and 20 times lower");
					running_commands();
				}
				
			balance = Integer.parseInt(args[3]);
				if(balance<50*min_bet) {
					System.out.println("Entry balance must be 50 times over the minimum bet");
					running_commands();
				}
				
			shoe = Integer.parseInt(args[4]);
			if(shoe <4 || shoe > 8) {
				System.out.println("Shoe number must be inbetween 4 and 8, including those numbers");
				running_commands();
			}
			
			shuffle = Integer.parseInt(args[5]);
			if(shuffle < 10 || shuffle > 100) {
				System.out.println("Shuffle must be between 10% and 100%, including those numbers");
				running_commands();
			}
			
			s_number = Integer.parseInt(args[6]);           
			strat = args[7];    
			if(!strat.equals("BS") && !strat.equals("HL") && !strat.equals("AF")) {
				System.out.println("Unknown strategy");
				running_commands();
			}
			blackjack.simulation(min_bet, max_bet, balance, shoe, shuffle, s_number, strat);

		}
		/*switch(args[0]) {
		case "-i": System.out.println("Interactive mode");

				p1 = new Player(balance);
				decks Deck = new decks(shoe);
				System.out.println(p1.playertoString());
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
		}*/
		
	}
	
	/*Instructions for use*/
	public static void running_commands(){
		System.out.println("Welcome to BlackJack Simulator 2021");
		System.out.println("Please use these commands to choose your game mode and entry parameters");
		System.out.println("INTERACTIVE MODE: java -jar <<YOUR-JAR-NAME>>.jar -i min-bet max-bet balance shoe shuffle");
		System.out.println("DEBUG       MODE: java -jar <<YOUR-JAR-NAME>>.jar -d min-bet max-bet shoe-file cmd-file");
		System.out.println("SIMULATION  MODE: java -jar <<YOUR-JAR-NAME>>.jar -s min-bet max-bet balance shoe shuffle s-number strategy");
		System.exit(1);
	}
}
