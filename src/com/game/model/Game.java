package com.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.game.controller.Controller;

/**
 * Represents the core logic and state of the game. Manages players, deck, turn
 * order, penalties, and game flow.
 *
 * @author Han Qiu [Student id 041135330]
 * @version 1.0
 * @see com.game.controller.Controller
 * @since 1.8
 */
public class Game {
	Controller controller;
	private int currentPlayerIndex = 0; // to track player's turn
	private boolean reversedOrder;
	boolean isGameOver = false;
	/**
	 * The current card in play
	 */
	public static Card currentCard = null;
	/**
	 * The shuffled deck of cards used in the game.
	 */
	public static List<Card> deck = new Deck().generateShuffledDeck(); // a shuffled deck of cards
	/**
	 * The collection of played cards
	 */
	public static List<Card> recycle = new ArrayList<>();
	public List<Player> players = new ArrayList<>();

	private ResourceBundle msg;

	Player pp; // primary player
	Player player1;
	Player player2;
	Player player3;
	List<Player> defaultCPUPlayerList;
	List<Player> cpuPlayerList = new ArrayList<>();

	public Player currentPlayer;
	String systemMsg;
	List<String> chatHistory = new ArrayList<>();

	int numPlayer = 4; // default number of player
	int cardPenaltyCounter = 0; // for cumulative penalty (i.e. a rank 2 card is played)

	/**
	 * Initializes a new game instance with the specified number of players.
	 *
	 * @param numPlayer - total number of players
	 */
	public Game(int numPlayer) {
		this.numPlayer = numPlayer;
		setLanguage("en");
		this.reversedOrder = false;
		this.currentPlayerIndex = 0;

		pp = new Player(getMessage("you"), this);

		player1 = new Player(getMessage("player") + " 1", this);
		player2 = new Player(getMessage("player") + " 2", this);
		player3 = new Player(getMessage("player") + " 3", this);

		defaultCPUPlayerList = Arrays.asList(player1, player2, player3);

		players.add(pp);
		for (int i = 0; i < numPlayer - 1; i++) {
			players.add(defaultCPUPlayerList.get(i));
			cpuPlayerList.add(defaultCPUPlayerList.get(i));
		}

		this.currentPlayer = players.get(getCurrentPlayerIndex());
		distributeCards();
		setCurrentCard(deck.remove(0)); // set the initial card
		recycle.add(currentCard);

	}

	/**
	 * Switches system language.
	 *
	 * @param langCode - en(English) or ch（Simplified Chinese)
	 */
	public void setLanguage(String langCode) {
		if (langCode.equals("en")) {
			Locale locale = new Locale(langCode);
			msg = ResourceBundle.getBundle("resources/messages_en", locale);
		} else if (langCode.equals("ch")) {
			Locale locale = new Locale(langCode);
			msg = ResourceBundle.getBundle("resources/messages_ch", locale);
		}
	}

	/**
	 * Deals 6 cards to each player from a shuffled deck of cards.
	 */
	public void distributeCards() {
		for (Player p : players) {
			for (int i = 0; i < 6; i++) {
				p.getHand().add(deck.remove(0));
			}
		}
	}

	/**
	 * Resets game state and prepares a new round of game with 2-4 players.
	 *
	 * @param numPlayer - number of players in new game
	 */
	public void resetGame(int numPlayer) {
		for (Player p : players) {
			p.getHand().clear();
		}
		players.clear();
		cpuPlayerList.clear();

		isGameOver = false;
		setSystemMsg("welcome");
		controller.getGameView().updateUI();
		deck = new Deck().generateShuffledDeck();
		players.add(pp);
		for (int i = 0; i < numPlayer - 1; i++) {
			players.add(defaultCPUPlayerList.get(i));
			cpuPlayerList.add(defaultCPUPlayerList.get(i));
		}
		distributeCards();
		setCurrentCard(deck.remove(0));
		recycle.clear();
		recycle.add(currentCard);

		cardPenaltyCounter = 0;
		currentPlayerIndex = 0;
		currentPlayer = players.get(currentPlayerIndex);
		setReversed(false);

		controller.getGameView().updateUI();
		playTurn();
	}

	/**
	 * Reverses the turn order of the game. This method is triggered when an Ace
	 * card is played,
	 */
	public void reverseOrderOfPlay() {
		setReversed(!reversedOrder);
	}

	/**
	 * Method to calculate index of next player
	 *
	 * @return an index number
	 */
	public int getNextPlayerIndex() {
		int nextIndex = reversedOrder ? (getCurrentPlayerIndex() - 1 + players.size()) % players.size()
				: (getCurrentPlayerIndex() + 1) % players.size();
		return nextIndex;
	}

	/**
	 * Used by human player exclusively. Checks if the input card is playablel. If
	 * yes, udpate game state and removes the card from their hand. if not, prompts
	 * them to draw a new card.
	 *
	 * @param selectedCard
	 */
	public void playCard(Card selectedCard) {
		if (hasValidMove(currentPlayer)) { // check if the player has more than 1 valid card
			if (selectedCard == null || !verifyCard(selectedCard)) {
				setSystemMsg("yourTurn");
				controller.getGameView().updateUI();
				return;
			}
			setCurrentCard(selectedCard);
			recycle.add(selectedCard);
			currentPlayer.getHand().remove(selectedCard);

			controller.getGameView().updateUI();
			specialCardHandler(currentCard, currentPlayer);

		} else { // if no valid card then ask them to draw a new one
			setSystemMsg("drawCard");
			controller.getGameView().updateUI();
			handleInvalidMove(currentPlayer);
			System.out.println(currentPlayer.getName() + " now have: " + currentPlayer.getHand()); // for debug
		}
	}

	/**
	 * Manages game loop. Handles player's move and update the game state.
	 */
	public void playTurn() {
		if (isGameOver) {
			setSystemMsg("winner", checkWinner().getName());
			controller.getGameView().getLead().updateLeaderboard(players);
			return;
		}

		Player winner = checkWinner();
		if (winner != null) {
			return;
		}

		setCurrentPlayer(players.get(getCurrentPlayerIndex()));
		System.out.println("Current turn: " + currentPlayer.getName());

		if (currentPlayer != pp) {//cpu players turn
			Card cpuCard;

			while (currentPlayer.getHand().size() < 12 && !hasValidMove(currentPlayer)) {
				handleInvalidMove(currentPlayer);
				System.out.println(currentPlayer.getName() + " now has: " + currentPlayer.getHand());
			}

			cpuCard = currentPlayer.cpuCardHandler();
			if (cpuCard != null) {

				currentPlayer.getHand().remove(cpuCard);
				setCurrentCard(cpuCard);
				recycle.add(currentCard);
				if (cardPenaltyCounter > 0) {
					if (cpuCard.getRank().equals("2")) {
						cardPenaltyCounter += 2;
					} else {
						for (int i = 0; i < cardPenaltyCounter; i++) {
							currentPlayer.drawCard(false);
						}
						cardPenaltyCounter = 0;
					}
				} else {
					specialCardHandler(cpuCard, currentPlayer);
				}

				controller.getGameView().updateUI();
				System.out.println(currentPlayer.getName() + " hand: " + currentPlayer.getHand());
			}

			winner = checkWinner();
			if (winner != null) {
				return;
			}
			TimerTask timerTask = new PlayTurnTask(this);
			new Timer().schedule(timerTask, 1000);

			setCurrentPlayerIndex(getNextPlayerIndex());

		} else { // human player's turn
			setSystemMsg("yourTurn");
			controller.getGameView().updateUI();

			if (!hasValidMove(currentPlayer)) {
				setSystemMsg("drawCard");
				controller.getGameView().updateUI();
				handleInvalidMove(currentPlayer);
			} else {
				setSystemMsg("playToContinue");
				controller.getGameView().updateUI();
			}
			if (currentCard != null) {
			    if (currentCard.getRank().equals("2")) {
					cardPenaltyCounter += 2;
				}

				if (cardPenaltyCounter > 0) {
					for (int i = 0; i < cardPenaltyCounter; i++) {
						currentPlayer.drawCard(false);
					}
					cardPenaltyCounter = 0;
				}
			}
		}

		controller.getGameView().getGameZone().updateUI();

		System.out.println("=============================================");
	}

	/**
	 * Handles the scenario when a player does not have a valid card to play.
	 *
	 * @param player - a player who needs to draw a new card
	 */
	public void handleInvalidMove(Player player) {
		if (player != pp) { // CPU player
			player.drawCard(false);
		} else { // Human player
			setSystemMsg("drawCard");
			controller.getGameView().updateUI();
		}

		if (!hasValidMove(player) && player.getHand().size() >= 12) {
			setSystemMsg("getScore");
			controller.getGameView().updateUI();
			player.updateScore(1);
			controller.getGameView().getLead().updateLeaderboard(players);
		}
	}

	/**
	 * Handles the effect of playing a card with a special rank.
	 *
	 * @param card          - a card with special rank
	 * @param currentPlayer - person that played the card
	 */
	public void specialCardHandler(Card card, Player currentPlayer) {

		switch (card.getRank()) {
		case "2":
			if (!(cardPenaltyCounter > 0)) {
				cardPenaltyCounter += 2;
			}

			setSystemMsg("two");
			controller.getGameView().updateUI();
			break;
		case "4":
			setSystemMsg("four");
			handleDrawFour(currentPlayer);
			controller.getGameView().updateUI();

			break;
		case "A":
			setSystemMsg("ace");
			controller.getGameView().updateUI();
			reverseOrderOfPlay();
			break;
		case "Q":
			setSystemMsg("queen");
			setCurrentPlayerIndex(getNextPlayerIndex());
			controller.getGameView().updateUI();
			break;
		case "8":
			if (currentPlayer == pp) {
				controller.handleChangeSuit();
			}
			break;
		default:
			String playerMsg;
			if (currentPlayer == pp) {
				playerMsg = getMessage("you");
			} else {
				playerMsg = getMessage("player") + " "
						+ currentPlayer.getName().charAt(currentPlayer.getName().length() - 1);
			}
			setSystemMsg("played", playerMsg, currentCard);
			break;
		}
	}

	/**
	 * Sets a new suit to the game.
	 *
	 * @param suit - a String with suit name
	 */
	public void changeSuit(String suit) {

		String suitChar = String.valueOf(suit.charAt(0));
		String convertedSuit = suitChar;
		// if system is set to a differenc language, convert suit names into English:
		if (suitChar.equals("方")) {
			convertedSuit = "d";
		}
		if (suitChar.equals("黑")) {
			convertedSuit = "s";
		}
		if (suitChar.equals("梅")) {
			convertedSuit = "c";
		}
		if (suitChar.equals("红")) {
			convertedSuit = "h";
		}
		currentCard.setSuit(convertedSuit);
		String newSuit = getMessage(convertedSuit);
		setSystemMsg("suitChangeTo", newSuit);
	}



	/**
	 * Assigns the next player 4 cards. If the player cannot take 4 more cards, the
	 * rest cards go to the current player. If current player does not have enough
	 * room, they will draw till they have 12 cards and get 1 penalty point.
	 *
	 * @param currentPlayer - this player played a rank four
	 */
	private void handleDrawFour(Player currentPlayer) {

		int drawCount = 4;
		Player nextPlayer = players.get(getNextPlayerIndex());

		int availableSpace = 12 - nextPlayer.getHand().size(); // check num of cards

		if (availableSpace >= drawCount) { // if next player can hold 4 more cards
			for (int i = 0; i < drawCount; i++) {
				nextPlayer.drawCard(false);
			}
			System.out
					.println(currentPlayer.getName() + " played a 4. " + nextPlayer.getName() + " must draw 4 cards.");

		}
		if (availableSpace < drawCount) { // next player cannot take all 4 cards
			int remainingCards = drawCount - availableSpace;
			drawCount = availableSpace;

			for (int i = 0; i < drawCount; i++) {
				nextPlayer.drawCard(true);
			}

			// leftover cards go back to caller
			if (remainingCards > 0) {
				if (currentPlayer.getHand().size() >= 12) { // If current player has no space
					System.out.println(currentPlayer.getName() + " has 12 cards and will get a penalty point.");
					currentPlayer.updateScore(1);
					controller.getGameView().getLead().updateLeaderboard(players);
				} else {
					for (int i = 0; i < remainingCards; i++) {
						currentPlayer.drawCard(true);
					}
					System.out.println(currentPlayer.getName() + " had space and took " + remainingCards + " cards.");
				}
			}
		}

		controller.getGameView().updateUI();

	}

	/**
	 * Checks if a player holds at least one valid card
	 *
	 * @param player Player object
	 * @return boolean -- true if player can play; otherwise false
	 */
	public boolean hasValidMove(Player player) {
		for (Card c : player.getHand()) {
			if (verifyCard(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Appends input to chat history list
	 *
	 * @param playerName
	 * @param message    - String object
	 */
	public void addChatMessage(String playerName, String message) {
		String chatEntry = getMessage("player") + playerName.charAt(playerName.length() - 1) + ": " + message;
		chatHistory.add(chatEntry);
	}

	/**
	 * Checks if there's a winner.
	 *
	 * @return player or null value
	 */
	public Player checkWinner() {
		for (Player p : players) {
			if (p.getHand().isEmpty()) {
				setSystemMsg("winner", p.getName());

				isGameOver = true;

				for (Player player : players) {
					if (player != p) {
						int penalty = player.getHand().size();
						player.updateScore(penalty);
					}
				}

				controller.getGameView().getLead().updateLeaderboard(players);
				controller.getGameView().updateUI();
				return p;
			}
		}
		return null;
	}

	/**
	 * Checks if a card is valid.
	 *
	 * @param card
	 * @return true or false
	 */
	public boolean verifyCard(Card card) {
		return (card.getRank().equals(currentCard.getRank()) || card.getSuit().equals(currentCard.getSuit())
				|| card.getRank().equals("8"));
	}

	// setters and getters
	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public List<Player> getCpuPlayerList() {
		return cpuPlayerList;
	}

	public void setCpuPlayerList(List<Player> cpuPlayerList) {
		this.cpuPlayerList = cpuPlayerList;
	}

	public int getCardPenaltyCounter() {
		return cardPenaltyCounter;
	}

	public void setCardPenaltyCounter(int cardPenaltyCounter) {
		this.cardPenaltyCounter = cardPenaltyCounter;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Player getPp() {
		return pp;
	}

	public void setPp(Player pp) {
		this.pp = pp;
	}

	public static Card getCurrentCard() {
		return currentCard;
	}

	public static void setCurrentCard(Card card) {
		currentCard = card;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
	}

	public String getSystemMsg() {
		return systemMsg;
	}

	public void setSystemMsg(String key, Object... args) {
		this.systemMsg = getMessage(key, args);

	}

	public String getMessage(String key) {
		return msg.getString(key);
	}

	public String getMessage(String key, Object... args) {
		return java.text.MessageFormat.format(msg.getString(key), args);
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	public boolean isReversed() {
		return reversedOrder;
	}

	public void setReversed(boolean isReversed) {
		this.reversedOrder = isReversed;
	}

	public static List<Card> getDeck() {
		return deck;
	}

	public static void setDeck(List<Card> deck) {
		Game.deck = deck;
	}

	public List<Player> getDefaultCPUPlayerList() {
		return defaultCPUPlayerList;
	}

	public void setDefaultCPUPlayerList(List<Player> defaultCPUPlayerList) {
		this.defaultCPUPlayerList = defaultCPUPlayerList;
	}
}

/**
 * Deck stores all suits and ranks and has a method to return a shuffled deck of
 * cards.
 */
class Deck {
	List<Card> deck;
	String[] suitArr = { "h", "c", "d", "s" };
	String[] rankArr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "J", "Q", "K" };

	Deck() {
		deck = new ArrayList<>();
	}

	/**
	 * Using two for loops to generate a deck of shuffled cards.
	 *
	 * @return An arraylist containing all 52 cards.
	 */
	List<Card> generateShuffledDeck() {
		for (String suit : suitArr) {
			for (String rank : rankArr) {
				deck.add(new Card(suit, rank));
			}
		}
		Collections.shuffle(deck);
		return deck;
	}
}

/**
 * A task that triggers the playTurn method of the Game class.
 */
class PlayTurnTask extends TimerTask {
	private Game game;

	public PlayTurnTask(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		game.playTurn();
	}
}