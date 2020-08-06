package com.blackjack.controller;

import com.blackjack.model.ChargeManager;
import com.blackjack.model.GameManager;
import com.blackjack.model.Statistic;
import com.blackjack.model.StatisticsRepository;
import com.blackjack.model.User;

public class GameController {
	private static GameController gameController = null;
	private ChargeManager chargeManager;
	private User user;
	private GameManager game;
	private Statistic userStatistics;
	private StatisticsRepository statisticsRepository;
	private GameController() {
		statisticsRepository = StatisticsRepository.BuildStatisticsRepository();
	}
	public static GameController BuildController(User user)
	{	
		if (gameController == null)
			gameController = new GameController();
		gameController.user = user;
		gameController.userStatistics = gameController.statisticsRepository.getUserStatistic(user);
		gameController.chargeManager = new ChargeManager(user);
		return gameController;
	}
	public GameManager playController(int betAmount) throws Exception
	{
		if (betAmount > 99999999)
			throw new NumberFormatException();
		chargeManager.Withdraw(betAmount);		
		game = new GameManager(betAmount, user.getMode());
		game.startTurn();
		return game;
	}
	public boolean hitController()
	{
		return game.playerHit();
	}
	public void endController()
	{
		try {
			chargeManager.Deposit(game.calcMoney());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		userStatistics.updateStatistic(game.checkHandResult(1));
		if(game.isSplit())
			userStatistics.updateStatistic(game.checkHandResult(2));
		statisticsRepository.Update(userStatistics);
	}
	public boolean holdController()
	{
		return game.playerHold(); 
	}
	public void splitController() throws Exception
	{
		if(user.getBalance() < game.getBetAmount(1))
			throw new Exception("Not Enough Money");
		game.playerSplit();
		chargeManager.Withdraw(game.getBetAmount(1));
		game.setBetAmount(game.getBetAmount(1) , 2);
	}
	public void doubleController() throws Exception
	{
		int hand = game.getPlayerHand();
		if(user.getBalance() < game.getBetAmount(hand))
			throw new Exception("Not Enough Money");
		game.playerDouble();
		chargeManager.Withdraw(game.getBetAmount(hand));
		game.setBetAmount(game.getBetAmount(hand) * 2, hand);
	}
	public boolean surrenderController()
	{
		return game.playerSurrender();
	}
	public String autoPlay(int betAmount,int rounds,int hit)
	{
		String result ="";
		try {
			for (int i=0;i< rounds;i++)
			{
				game = gameController.playController(betAmount);
				result += "Game Number: " + (i+1) + "\n";
				while (game.playerHandValue() <= hit)
				{
					gameController.hitController();
				}
				gameController.holdController();
				gameController.endController();
				result += game + "\n";
				result += game.endTurn() + "\n";
			}
		}catch(Exception e) {
			result += e.getMessage();
		}
		return result;
	}
}
