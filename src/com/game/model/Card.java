package com.game.model;

/**
 * Represents a playing card with a suit and rank.
  * @author Han Qiu [Student id 041135330]
 * @version 1.0
 * @since 1.8
 */
public class Card {

	private String suit;
	private String rank;

	/**
	 * Creates a new card with the specified suit and rank.
	 * 
	 * @param suit - four suits
	 * @param rank - 13 ranks
	 */
	public Card(String suit, String rank) {
		this.rank = rank;
		this.suit = suit;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {

		return rank + suit;

	}
}
