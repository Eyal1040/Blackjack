package com.blackjack.model;

public class Dealer {
	private Hand hand = new Hand();
	public void pushCard(Card card)
	{
		hand.addCard(card);
	}
	public int handValue()
	{
		return hand.getHandValue();
	}
	public Card removeCard()
	{
		return hand.removeCard();
	}
	public String showHand()
	{
		return hand.returnHand();
	}
	public Card getCard(int index)
	{
		return hand.getCard(index);
	}
	public int handSize()
	{
		return hand.handSize();
	}
}
