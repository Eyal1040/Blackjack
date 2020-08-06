package com.blackjack.model;

import java.util.*;

public class Hand {
	
	private ArrayList<Card> handCards = new ArrayList<Card>();
	
	public void addCard(Card card)
	{
		handCards.add(card);
	}
	public int getHandValue()
	{
		boolean Ace = false;
		int sum =0;
		for(Card card : handCards)
		{
			if (card.getValue() == 1)
				Ace = true;
			sum += card.getValue();
		}
		if (Ace)
		{
			if (sum < 12)
				return sum + 10;
		}
		return sum;
	}
	public Card removeCard()
	{
		return handCards.remove(1);
	}
	public String returnHand()
	{
		String result = "";
		for(Card card : handCards)
		{
			result += "[" + card.getPicture() + " " + card.getSign() + "] ";  
		}
		result += "Value: ";
		return result;
	}
	public Card getCard(int index)
	{
		return handCards.get(index);
	}
	public int handSize()
	{
		return handCards.size();
	}
}
