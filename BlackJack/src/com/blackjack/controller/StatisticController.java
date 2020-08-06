package com.blackjack.controller;

import com.blackjack.model.Statistic;
import com.blackjack.model.StatisticsRepository;
import com.blackjack.model.User;

public class StatisticController {
	private static StatisticController statisticController = null;
	private User user;
	private StatisticsRepository statisticsRepository;
	
	private StatisticController() {
		statisticsRepository = StatisticsRepository.BuildStatisticsRepository();
	}
	public static StatisticController BuildStatisticController(User user)
	{	
		if (statisticController == null)
			statisticController = new StatisticController();
		statisticController.user = user;
		return statisticController;
	}
	public boolean userClear(String choose)
	{
		if(choose.equals("clear"))
		{
			Statistic update = statisticsRepository.getUserStatistic(user);
			update.clearStatistics();
			statisticsRepository.Update(update);
			return true;
		}
		return false;
	}
	public Statistic getUserStatistics()
	{
		return statisticsRepository.getUserStatistic(user);
	}
}
