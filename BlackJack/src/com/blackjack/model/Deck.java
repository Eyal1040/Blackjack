package com.blackjack.model;

import java.util.*;

public class Deck {
	private static Deck gameDeck= null;
	private List<Card> deckCards = new LinkedList<Card>();
	private Deck()
	{
		addCards();
	}
	public static Deck buildDeck()
	{
		if (gameDeck == null)
			gameDeck = new Deck();
		return gameDeck;
	}
	private void addCards()
	{
		for (int j=0;j<4;j++)
		{
			addPerSign("CLUBS");
			addPerSign("DIAMONDS");
			addPerSign("HEARTS");
			addPerSign("SPADES");
		}
		Collections.shuffle(deckCards);
	}
	private void addPerSign(String sign)
	{
		for (int i=1;i<=13;i++)
		{
			deckCards.add(new Card(i,sign));
		}
	}
	public Card pull()
	{
		if (getDeckSize() == 0)
		{
			addCards();
		}
		return deckCards.remove(0);
	}
	public int getDeckSize()
	{
		return deckCards.size();
	}
}
