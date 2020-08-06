package com.blackjack.model;

import java.util.Set;

public class StatisticsRepository implements IRepository{
	private static StatisticsRepository statisticsRepository = null;
	private FileManager<Statistic> fileManager;
	private Set<Statistic> statistics;
	@SuppressWarnings("unchecked")
	private StatisticsRepository()
	{
		fileManager = new FileManager<Statistic>("statistics.dat");
		try {
		statistics = (Set<Statistic>) fileManager.read();
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public static StatisticsRepository BuildStatisticsRepository()
	{	
		if (statisticsRepository == null)
			statisticsRepository = new StatisticsRepository();
		return statisticsRepository;
	}
	public void Update(Statistic Statistic)
	{
		try{
		statistics.remove(Statistic);//old
		statistics.add(Statistic);//new
		fileManager.write(statistics);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	public Set<Statistic> getStatistics()
	{
		return statistics;
	}
	public Statistic getUserStatistic(User user)
	{
		try {
		String userName = user.getUserName();
		if (statistics.contains(new Statistic(userName)) == false)
			throw new Exception ("Somthing Went Wrong...");
		for (Statistic a : statistics)
		{
			if(a.equals(new Statistic(userName)))
				return a;
		}
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	public void clean()
	{
		statistics.clear();
		try{
		fileManager.write(statistics);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}
