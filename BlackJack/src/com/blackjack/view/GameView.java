package com.blackjack.view;

import java.util.Scanner;
import com.blackjack.controller.ChargeContoller;
import com.blackjack.controller.GameController;
import com.blackjack.controller.RegisterContoller;
import com.blackjack.controller.StatisticController;
import com.blackjack.model.GameManager;
import com.blackjack.model.User;


public class GameView {
	private Scanner scanner = new Scanner(System.in);
	private GameController gameController;
	private User user;
	private GameManager game;

	public void mainScreen()
	{
		System.out.println("Welcome To BlackJack!");
		System.out.println("1 - Login");
		System.out.println("2 - Signup");
		System.out.println("Q - Quit");
		String select= scanner.nextLine();
		switch (select){
			case "1":
				loginScreen();
				break;
			case "2":
				signUpScreen();
				break;
			case "q":
			case "Q":
				System.out.println("Bye!");
				System.exit(0);
	    	default:
	    		System.out.println("Invalid Input Please Try Again!");
	    		mainScreen();
			}
		}
	public void loginScreen()
	{
		try {
		System.out.println("~Login~");
		System.out.println("Enter Username: ");
		String username = scanner.nextLine();
		System.out.println("Enter Password: ");
		String password = scanner.nextLine();
		user = RegisterContoller.BuildRegisterContoller().loginController(username, password);
		gameController = GameController.BuildController(user);
		System.out.println("Login Succesfully");
		inGameMenuScreen();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			mainScreen();
		}				
	}
	public void signUpScreen()
	{
		try {
		System.out.println("~SignUp~");
		System.out.println("Enter Username: ");
		String username = scanner.nextLine();
		System.out.println("Enter Password: ");
		String password = scanner.nextLine();
		RegisterContoller.BuildRegisterContoller().signupController(username, password);
		System.out.println("User Added Succsefully");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		mainScreen();
	}
	public void inGameMenuScreen()
	{
		System.out.println(user);
		System.out.println(user.getModeString());
		System.out.println("1 - Play");
		System.out.println("2 - Auto Play");
		System.out.println("3 - Deposit Money");
		System.out.println("4 - Withdraw Money");
		System.out.println("5 - Statistics");
		System.out.println("6 - To Change Mode");
		System.out.println("Q - LogOut");
		String select = scanner.nextLine();
		switch (select)
		{
		case "1":
			betScreen();
			break;
		case "2":
			autoPlayScreen();
			break;
		case "3":
			depositScreen();
			break;
		case "4":
			withdrawScreen();
			break;
		case "5":
			statisticsScreen();
			break;
		case "6":
			user.changeMode();
			inGameMenuScreen();
			break;
		case "Q":
		case "q":
			mainScreen();
			break;
	    default:
			System.out.println("Invalid Input Please Try Again!");
			inGameMenuScreen();
		}
	}
	public void betScreen()
	{
		try {
		System.out.println(user);
		System.out.println("Enter Bet Amount: ");
		int betAmount = Integer.parseUnsignedInt(scanner.nextLine());
		game = gameController.playController(betAmount);
		System.out.println("Succsefull Bet Amount : " + betAmount);
		playScreen();
		}catch(NumberFormatException e) {
			System.out.println("Invalid Input Accept Integers Between 1 to 99999999");
			inGameMenuScreen();
		}catch(Exception e) {
			System.out.println(e.getMessage());//withdraw
			inGameMenuScreen();
		}
	}
	public void playScreen()
	{	
		System.out.println(user);
		System.out.println(game);
		System.out.println("Hand : " + game.getPlayerHand());
		System.out.println("1 - Hit");
		System.out.println("2 - Hold");
		System.out.println("3 - Double");
		System.out.println("4 - Split");
		System.out.println("5 - Surrender");
		String select = scanner.nextLine();
		switch (select)
		{
		case "1":
			if(gameController.hitController())
				playScreen();
			else
				endScreen();
			break;
		case "2":
			if(gameController.holdController())
				playScreen();
			else
				endScreen();
			break;
		case "3":
			try {
				gameController.doubleController();
				endScreen();
			}catch (Exception e) {
				System.out.println(e.getMessage());
				playScreen();
			}
			break;
		case "4":
			try {
				gameController.splitController();
				System.out.println("Split Succesfully");
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			playScreen();
			break;
		case "5":
			if(gameController.surrenderController())
				playScreen();
			else
				endScreen();
			break;
	    default:
	    	System.out.println("Invalid Input Please Try Again!");
	    	playScreen();
		}
	}
	public void endScreen()
	{
		System.out.println(game);
		System.out.println(game.endTurn());
		gameController.endController();
		inGameMenuScreen();
	}
	public void depositScreen()
	{
		System.out.println(user.getModeString());
		System.out.println("~Deposit~");
		System.out.println(user);
		System.out.println("Enter Amount To Deposit: ");
		try {
			int amount = Integer.parseInt(scanner.nextLine());
			ChargeContoller.BuildChargeContoller(user).depositController(amount);
			System.out.println("Succsefully Added " + amount);
		}catch(NumberFormatException e) {
			System.out.println("Invalid Input Accept Integers Between 0 to " + Integer.MAX_VALUE);
		}
		inGameMenuScreen();
	}
	public void withdrawScreen()
	{
		System.out.println(user.getModeString());
		System.out.println("~Withdraw~");
		System.out.println(user);
		System.out.println("Enter Amount To Withdraw: ");
		try {
			int amount = Integer.parseInt(scanner.nextLine());
			ChargeContoller.BuildChargeContoller(user).withdrawController(amount);
			System.out.println("Succsefully Taken " + amount);
		} catch(NumberFormatException e) {
			System.out.println("Invalid Input Accept Integers Between 1 to " + Integer.MAX_VALUE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		inGameMenuScreen();
	}
	public void statisticsScreen()
	{
		System.out.println("~Statistics~");
		System.out.println(user);
		StatisticController statisticController =  StatisticController.BuildStatisticController(user);
		System.out.println(statisticController.getUserStatistics());
		System.out.println("To Clear Statistics - clear");
		System.out.println("Back - Any");
		if(statisticController.userClear(scanner.nextLine()))
			statisticsScreen();
		inGameMenuScreen();
	}
	public void autoPlayScreen()
	{
		long startMoney = user.getBalance();
		try {
		System.out.println(user);
		System.out.println("Enter Bet Amount Per Round: ");
		System.out.println("Bet Amount Need To Be Integers Between 1 to 99999999");
		int betAmount = Integer.parseInt(scanner.nextLine());
		if (betAmount <= 0 || betAmount > 99999999)
			throw new NumberFormatException();
		System.out.println("Enter Number Of Rounds: ");
		System.out.println("Rounds Need To Be Integers Between 1 to 999");
		int rounds = Integer.parseInt(scanner.nextLine());
		if (rounds <= 0 || rounds > 999)
			throw new NumberFormatException();
		System.out.println("Enter Maximum Hand Value To Hit: ");
		System.out.println("The Number Need To Be Integers Between 1 to 21");
		int hit = Integer.parseInt(scanner.nextLine());
		if (hit <= 0 || hit > 21)
			throw new NumberFormatException();
		System.out.println(gameController.autoPlay(betAmount, rounds, hit));
		}catch(NumberFormatException e) {
			System.out.println("\n" + "Invalid Input" + "\n");
		}catch(Exception e) {
			System.out.println("\n" + e.getMessage() + "\n");
		}
		System.out.println("Started Money: " + startMoney + " End Money: " + user.getBalance() + " Money Profit: " + (user.getBalance() - startMoney));
		StatisticController statisticController =  StatisticController.BuildStatisticController(user);
		System.out.println(statisticController.getUserStatistics());
		inGameMenuScreen();
	}
}
