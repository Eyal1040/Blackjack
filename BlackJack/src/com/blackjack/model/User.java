package com.blackjack.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private long virtualMoney;
	private long realMoney;
	private boolean realPlay;
	
	public User(String userName, String password, long virtualMoney,long realMoney, boolean realPlay) 
	{
		this.userName = userName;
		this.password = password;
		this.virtualMoney = virtualMoney;
		this.realMoney = realMoney;
		this.realPlay = realPlay;
	}
	public void changeMode()
	{
		realPlay = realPlay ^ true;
	}
	public boolean getMode()
	{
		return realPlay;
	}
	public void addBalance(int amount) {
		if (realPlay)
			realMoney += amount;
		else
			virtualMoney += amount;
	}
	public long getBalance()
	{
		if (realPlay)
			return realMoney;
		return virtualMoney;
	}
	public String getModeString()
	{
		String result = "";
		if (realPlay)
			result += "Real ";
		else
			result += "Virtual ";
		result += "Money Mode!";
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		User user = (User)obj;
		return this.userName.equals(user.userName);
	}
	@Override
	public int hashCode() {
		return userName.hashCode();
	}
	@Override
	public String toString() {
		String result = "";
		result += "Hello " + userName +",\n" + "Money Amount: " + getBalance();
		if (realPlay)
			result += "$";
		return result;
	}
}
