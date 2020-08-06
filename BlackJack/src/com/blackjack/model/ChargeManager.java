package com.blackjack.model;

public class ChargeManager {
	private UserRepository userRepository;
	private StatisticsRepository statisticsRepository;
	private User user;
	public ChargeManager(User user) {
		userRepository = UserRepository.BuildUserRepository();
		statisticsRepository = StatisticsRepository.BuildStatisticsRepository();
		this.user = user;
	}
	public void Withdraw(int amount)throws Exception {
		if (amount <= 0)
			throw new NumberFormatException();
		if (amount > user.getBalance())
			throw new Exception("Amount Less Than Money");
		user.addBalance(-amount);
		userRepository.Update(user);
	}
	public void Deposit(int amount)throws NumberFormatException {
		if (amount < 0)
			throw new NumberFormatException();
		user.addBalance(amount);
		userRepository.Update(user);
	}
	public void updateDepositHistory(int amount)
	{
		if (user.getMode())
		{
			statisticsRepository.getUserStatistic(user).updateDepositHistory(amount);
			statisticsRepository.Update(statisticsRepository.getUserStatistic(user));
		}
	}
}
