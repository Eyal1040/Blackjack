package com.blackjack.model;

public class LoginManager {
	private UserRepository userRepository = UserRepository.BuildUserRepository();
	public User userLogin(String userName,String password) throws Exception
	{
		User fileUser = userExists(userName);
		passwordMatch(fileUser, password);
		return fileUser;
	}
	private User userExists(String userName)throws Exception
	{
		User user = userRepository.getUser(userName);
		if(user == null)
			throw new Exception("User Doesn't Exists");
		return user;
	}
	private void passwordMatch(User user,String password)throws Exception
	{
		if (!(user.getPassword().equals(password)))
			throw new Exception("Invalid Password");
	}
}
