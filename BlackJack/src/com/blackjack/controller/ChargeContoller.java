package com.blackjack.controller;

import com.blackjack.model.ChargeManager;
import com.blackjack.model.User;

public class ChargeContoller {
	private static ChargeContoller chargeContoller = null;
	private ChargeManager chargeManager;
	
	private ChargeContoller() {
	}
	public static ChargeContoller BuildChargeContoller(User user)
	{	
		if (chargeContoller == null)
			chargeContoller = new ChargeContoller();
		chargeContoller.chargeManager = new ChargeManager(user);
		return chargeContoller;
	}
	public void depositController(int amount) throws NumberFormatException
	{
		chargeManager.Deposit(amount);
		chargeManager.updateDepositHistory(amount);
	}
	public void withdrawController(int amount) throws Exception
	{
		chargeManager.Withdraw(amount);
	}
}
