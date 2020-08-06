package com.blackjack.model;

public class GameManager {
	private Deck gameDeck = Deck.buildDeck();
	private Dealer dealer = new Dealer();
	private Player player = new Player();
	private int [] betAmount = new int [2];
	private boolean [] surrender = new boolean[2];
	private boolean realMoneyMode;
	
	//Test
	public GameManager(Player player , Dealer dealer)
	{
		this.player = player;
		this.dealer = dealer;
		betAmount[0] = 100;
	}
	//Test
	public GameManager(int betAmount,boolean realMoneyMode)
	{
		this.betAmount[0] = betAmount;
		this.realMoneyMode = realMoneyMode;
	}
	public void setBetAmount(int betAmount,int index)
	{
		this.betAmount[index - 1] = betAmount;
	}
	public int getBetAmount(int index)
	{
		return betAmount[index - 1];
	}
	public void startTurn()
	{
		dealer.pushCard(gameDeck.pull());
		player.pushCard(gameDeck.pull());
		player.pushCard(gameDeck.pull());
	}
	public boolean playerHit()
	{
		if (player.getCurrentPlayedHand() == 1)
		{
			player.pushCard(gameDeck.pull());
			if (player.handValue() > 21) 
			{
				if(isSplit())
					player.setCurrentPlayedHand(2);
				else 
				{
					pushUntil17();
					return false;
				}
			}

		}
		else
		{
			player.secondHandPushCard(gameDeck.pull());
			if (player.secondHandValue() > 21)
			{
				pushUntil17();
				return false;	
			}
		}
		return true;
	}
	public boolean playerHold()
	{
		if (isSplit() == false)
		{
			pushUntil17();
			return false;
		}
		if (player.getCurrentPlayedHand() == 1)
			player.setCurrentPlayedHand(2);
		else
		{
			pushUntil17();
			return false;
		}
		return true;
			
	}
	public boolean playerDouble() throws Exception
	{
		if (player.getCurrentPlayedHand() == 1)
		{
			if(player.handSize() != 2)
				throw new Exception ("Player Hand 1 Size Is Not 2");
			player.pushCard(gameDeck.pull());
			if (isSplit() == false)
				pushUntil17();
			else
			{
				player.setCurrentPlayedHand(2);
				return true;
			}
		}
		else
		{
			if(player.secondHandSize() != 2)
				throw new Exception ("Player Hand 2 Size Is Not 2");
			player.secondHandPushCard(gameDeck.pull());
			pushUntil17();
		}
		return false;
	}
	private void pushUntil17()
	{
		while (dealer.handValue() < 17)
		{
			dealer.pushCard(gameDeck.pull());
		}
	}
	public void playerSplit() throws Exception
	{
		if (isSplit())
			throw new Exception("Already Splited");
		if(player.handSize() != 2)
			throw new Exception ("Player Hand Size Is Not 2");
		if(player.getCard(0).getNumber() != player.getCard(1).getNumber())
			throw new Exception ("Player Cards Not Same");
		player.secondHandPushCard(player.removeCard());
		player.pushCard(gameDeck.pull());
		player.secondHandPushCard(gameDeck.pull());
	}
	public boolean playerSurrender()
	{
		if (player.getCurrentPlayedHand() == 1)
		{
			surrender[0] = true;
			if (isSplit() == false)
			{
				pushUntil17();
				return false;
			}
			else
			{
				player.setCurrentPlayedHand(2);
				return true;
			}
		}
		else
		{
			surrender[1] = true;
			pushUntil17();
		}
		return false;
	}
	public String endTurn()
	{
		String result = "";
		String Hand1 = checkHandResult(1);
		result += "Hand 1: " + Hand1 + ",Gain: " + handResult(Hand1 , 1);
		if (isSplit())
		{
			String Hand2 = checkHandResult(2);
			result += "\nHand 2: " + Hand2 + ",Gain: " + handResult(Hand2 , 2);
		}
		return result;
	}
	public String checkHandResult(int hand)
	{
		if (surrender[hand - 1])
			return "Surrender ";
		int playerHand = hand == 1 ? player.handValue() : player.secondHandValue();
		int dealerHand = dealer.handValue();
		if (playerHand == 21 && player.handSize() == 2)
		{
			if (dealerHand == 21 && dealer.handSize() == 2)
				return "Tie ";
			else
				return "BLACK JACK ";
		}
		if (dealerHand == 21 && dealer.handSize() == 2)
			return "LOSE ";
		if (playerHand > 21)
			return "LOSE ";
		if (dealerHand > 21)
			return "Win ";
		if (playerHand > dealerHand)
			return "Win ";
		if (playerHand < dealerHand)
			return "LOSE "; 
		return "Tie ";
	}
	public int calcMoney()
	{
		int sum = 0;
		String result = checkHandResult(1);
		sum += handResult(result, 1);
		if (isSplit())
		{
			result = checkHandResult(2);
			sum += handResult(result, 2);
		}
		return sum;
	}
	private int handResult(String result,int hand)
	{
		int Amount = betAmount[hand - 1];
		if (result.equals("BLACK JACK "))
			return (int)(Amount * 2.5);
		if (result.equals("Win "))
			return Amount * 2;
	    if (result.equals("Tie "))
	    	return Amount;
	    if (result.equals("Surrender "))
	    	return Amount / 2;
	    return 0;//LOSE
	}
	@Override
	public String toString() {
		String result = "player Hand1: Bet Amount " + betAmount[0];
		if (realMoneyMode)
			result += "$";
		result+= " " + player.showHand() + player.handValue() + "\n";
		if (isSplit())
		{
			result += "player Hand2: Bet Amount " + betAmount[1];
			if (realMoneyMode)
				result += "$";
			result += " " + player.showSecondHand() + player.secondHandValue() + "\n";
		}
		result += "dealer: " + dealer.showHand() + dealer.handValue();
		return result;
	}
	public int playerHandValue()
	{
		return player.handValue();
	}
	public int playerSecondHandValue()
	{
		return player.secondHandValue();
	}
	public boolean isSplit()
	{
		return player.secondHandValue() != 0;
	}
	public int getPlayerHand()
	{
		return player.getCurrentPlayedHand();
	}
}
