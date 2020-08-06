package com.blackjack.model;

import java.util.Set;

import com.blackjack.model.User.UserBuilder;

public class SignUpManager {
	private UserRepository userRepository = UserRepository.BuildUserRepository();
	private StatisticsRepository statisticsRepository = StatisticsRepository.BuildStatisticsRepository();
	
	public void signNewUser(String userName,String password) throws Exception
	{
		userNameValidation(userName);
		passwordValidation(password);
		userNameAvailable(userName);
		User user = new UserBuilder()
		       .userName(userName)
		       .password(password)
		 	   .build();
		userRepository.Update(user);
		statisticsRepository.Update(new Statistic(user.getUserName()));
	}	
	private void userNameValidation(String userName)throws Exception
	{
		if(userName.contains(" "))
			throw new Exception("Spaces Not Allowed");
		if (userName.length() < 3)
			throw new Exception("UserName Length Less Than 3 Letters");
		if (userName.length() > 10)
			throw new Exception("UserName Length More Than 10 Letters");
	}
	
	private void passwordValidation(String password)throws Exception
	{
		if (password.length() < 3)
			throw new Exception("Password Length Less Than 3 Letters");
		if (password.length() > 10)
			throw new Exception("Password Length More Than 10 Letters");
	}
	
	private void userNameAvailable(String userName)throws Exception
	{
		Set<User> users = userRepository.getUsers();
		for (User a : users)
		{
			if (a.getUserName().toUpperCase().equals(userName.toUpperCase()))
				throw new Exception("Username Already Exists");
		}
	}
}
