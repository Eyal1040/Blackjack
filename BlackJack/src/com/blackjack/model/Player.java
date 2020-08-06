package com.blackjack.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Dealer{
	private Hand secondHand = new Hand();
	private int currentPlayedHand = 1;
	
	public void secondHandPushCard(Card card)
	{
		secondHand.addCard(card);
	}
	public int secondHandValue()
	{
		return secondHand.getHandValue();
	}
	public int secondHandSize()
	{
		return secondHand.handSize();
	}
	public String showSecondHand()
	{
		return secondHand.returnHand();
	}
	
}
