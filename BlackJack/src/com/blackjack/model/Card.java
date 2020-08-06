package com.blackjack.model;

import lombok.Getter;

@Getter
public class Card {
	private int number;
	private String picture;
	private String sign;
	private int value;
	public Card(int number, String sign)
	{
		this.number = number;
		this.picture = trueNumber(number);
		this.sign = sign;
		this.value = calcValue();
	}
	private String trueNumber(int number)
	{
		switch(number)
		{
		case 1:
		return "A";
		case 11:
		return "J";
		case 12:
		return "Q";
		case 13:
		return "K";
		default:
		return String.valueOf(number);
		}
	}
	private int calcValue()
	{
		if (number >= 10)
			return 10;
		return number;
	}
}
