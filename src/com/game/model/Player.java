package com.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a player in the game
 * @author Han Qiu [Student id 041135330]
 * @version 1.0
 * @since 1.8
 */
public class Player {
	private String name = "Player";
	private int score = 0;
	private List<Card> hand = new ArrayList<>();
	protected Game game;

	/**
	 * Creates a new player with a specified name in game.
	 * 
	 * @param name - player's name
	 * @param game - the game in which the player plays
	 */
	public Player(String name, Game game) {
		this.name = name;
		this.game = game;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Card> getHand() {
		return hand;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	/**
	 * Plays a card if it is valid.
	 *
	 * @param selectedCard The card to play.
	 * @return True if the card was played successfully, false otherwise.
	 */
	public boolean playCard(Card selectedCard) {
		if (hand.contains(selectedCard)) {
			hand.remove(selectedCard);
			return true;
		}
		return false;
	}

	/**
	 * Draws a card from the deck.
	 *
	 * @param isHumanPlayer - whether the player is human.
	 */
	public void drawCard(boolean isHumanPlayer) {
		if (Game.deck.isEmpty()) {
			List<Card> tempRecycledCards = new ArrayList<>(Game.recycle);
			Game.deck.addAll(tempRecycledCards);
			Game.recycle.clear();
			Collections.shuffle(Game.deck);
		}
		if (hand.size() < 12) {
			hand.add(Game.deck.remove(0));
			System.out.println(name + " drew a card: " + hand.get(hand.size() - 1).toString());
		} else {
			game.setSystemMsg("getScore");
			this.updateScore(1);
			game.controller.getGameView().updateUI();
		}
	}

	/**
	 * Picks a valid card for the CPU player to play.
	 *
	 * @return The card to play, or null if no valid card is found.
	 */
	public Card cpuCardHandler() {
		for (Card c : hand) {
			if (c.getSuit().equals(Game.currentCard.getSuit()) || c.getRank().equals(Game.currentCard.getRank())
					|| c.getRank().equals("8")) {

				System.out.println(name + " played " + c.toString());
				return c;
			}
		}
		return null; // No valid card found
	}

	/**
	 * Updates the player's score.
	 *
	 * @param mark The points to add
	 */
	public void updateScore(int pts) {
		this.score += pts;
	}

	// getters and setters
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}