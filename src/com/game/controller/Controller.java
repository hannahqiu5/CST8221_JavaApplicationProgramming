package com.game.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import com.game.ui.*;
import com.game.model.*;
/**
 * The Controller class manages the interaction between the Model (Game logic) 
 * and the View (User Interface).
 * @author Han Qiu [Student id 041135330]
 * @version 1.0
 * @see com.game.model.Game
 * @see com.game.ui.UI
 * @since 1.8
 */
public class Controller {

	private Game gameModel; // reference to Model
	private UI gameView; // reference to View
	private ResourceBundle msg;

	/**
	 * controller constructor
	 * 
	 * @param view - ui classes
	 * @param game - contains logic and data of the game
	 */
	public Controller(UI view, Game game) {
		this.gameView = view;
		this.gameModel = game;
		setLanguage("en");
	}

	/**
	 * The main method to initialize the game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Game gameModel = new Game(4);
		UI gameView = new UI();
		Controller gameController = new Controller(gameView, gameModel);
		gameView.initializer(gameController);
		gameModel.setController(gameController);

		gameModel.playTurn();
		gameView.updateUI();
		printPlayerHands(gameModel);
	}

	/**
	 * for debugging purposes This method prints out player's hand
	 * 
	 * @param gameModel - the model containing the players and their hands
	 */
	private static void printPlayerHands(Game gameModel) {
		for (Player p : gameModel.getPlayers()) {
			System.out.println(p.getName() + " ");
			for (Card card : p.getHand()) {
				System.out.print(card.toString() + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Handles the card selection by the player.
	 * 
	 * @param selectedCard - the card selected by the player
	 */
	public void handleCardSelection(Card selectedCard) {
		Player currentPlayer = gameModel.getCurrentPlayer();

		if (currentPlayer.getHand().contains(selectedCard) && gameModel.verifyCard(selectedCard)) {
			gameModel.playCard(selectedCard);
			Game.setCurrentCard(selectedCard);
			gameModel.setSystemMsg("played", currentPlayer.getName(), selectedCard.toString());

			gameModel.specialCardHandler(selectedCard, currentPlayer);
			gameView.getGameZone().updateUI();

			if (gameModel.checkWinner() != null) {
				gameModel.setSystemMsg("winner", currentPlayer.getName());
				gameView.updateUI();
				return;
			}

			if (!selectedCard.getRank().equals("Q")) {
				gameModel.setCurrentPlayerIndex(gameModel.getNextPlayerIndex());
			}
			gameModel.playTurn();
		} else {
			gameModel.setSystemMsg("select");
			gameView.updateUI();
		}

		gameView.getGameZone().updateUI();
	}

	/**
	 * Handles the action when the card stack is clicked.
	 * 
	 */
	public void handleCardStackClick() {
		Player currentPlayer = gameModel.getCurrentPlayer();
		if (gameModel.hasValidMove(currentPlayer)) {
			gameModel.setSystemMsg("hasValid");
			gameView.updateUI();
			return;
		}

		System.out.println(currentPlayer.getName() + " drew a card. ");
		currentPlayer.drawCard(true);
		// gameModel.handleInvalidMove(currentPlayer);
		gameView.getGameZone().refreshGameZone();
		gameView.updateUI();

	}

	/**
	 * Sends a message in the chat
	 * 
	 * @param text - a user-edited message
	 */
	public void handleSendMessage(String text) {
		gameView.getChat().addMessage(gameModel.getCurrentPlayer().getName(), text);
		gameModel.addChatMessage(gameModel.getCurrentPlayer().getName(), text);
		gameView.getChat().getInputBox().setText("");

	}

	/**
	 * Action Listener for Start Game
	 */
	public class StartGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameModel.resetGame(4);
			gameView.updateUI();
		}
	}

	/**
	 * Listener for ending the game.
	 */
	public class EndGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameModel.setGameOver(true);
			gameModel.setSystemMsg("close");
			gameView.updateUI();
			WindowCloserTask task = new WindowCloserTask(gameView.getFrame());
			Thread thread = new Thread(task);
			thread.start();
		}
	}

	/**
	 * Action Listener for 2-Player Game
	 */
	public class TwoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameModel.resetGame(2);
			System.out.println("2 player game");
			gameView.updateUI();
		}
	}

	/**
	 * Action Listener for 3-Player Game
	 */
	public class ThreeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameModel.resetGame(3);
			System.out.println("3 player game");
			gameView.updateUI();
		}
	}

	/**
	 * Action Listener for 4-Player Game
	 */
	public class FourListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameModel.resetGame(4);
			System.out.println("4 player game");
			gameView.updateUI();
		}
	}

	/**
	 * Listener for switching the language to English.
	 */
	public class EngListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("en"); // Corrected language code
			gameModel.setLanguage("en");
			gameView.updateText();
			gameView.getLead().updateLeaderboard(gameModel.players);
			gameView.updateUI();
		}
	}

	/**
	 * Listener for switching the language to Chinese.
	 */
	public class ChListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("ch"); // Corrected language code
			gameModel.setLanguage("ch");
			gameView.updateText();
			gameView.getLead().updateLeaderboard(gameModel.players);
			gameView.updateUI();
		}
	}

	public void handleChangeSuit() {
		String chosenSuit = gameView.getGameZone().showSuitDialog();
		if (chosenSuit != null) {
			gameModel.changeSuit(chosenSuit); 
			gameView.updateUI(); 
		}
	}

	/**
	 * Sets the language for the game.
	 * 
	 * @param langCode - the language code (e.g., "en" for English, "ch" for
	 *                 Chinese)
	 */
	public void setLanguage(String langCode) {
		System.out.println("Changing language to: " + langCode);
		try {
			if (langCode.equals("en")) {
				Locale locale = new Locale(langCode);
				msg = ResourceBundle.getBundle("resources.messages_en", locale);
				System.out.println("Loaded English bundle successfully");
			} else if (langCode.equals("ch")) {
				Locale locale = new Locale(langCode);
				msg = ResourceBundle.getBundle("resources.messages_ch", locale);
				System.out.println("Loaded Chinese bundle successfully");
			}
		} catch (Exception e) {
			System.err.println("Error loading language bundle: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Getter and Setter Methods
	public UI getGameView() {
		return gameView;
	}

	public void setGameView(UI gameView) {
		this.gameView = gameView;
	}

	public Game getGameModel() {
		return gameModel;
	}

	public void setGameModel(Game gameModel) {
		this.gameModel = gameModel;
	}

	public String getMessage(String key) {
		return msg.getString(key);
	}

	public String getMessage(String key, Object... args) {
		return java.text.MessageFormat.format(msg.getString(key), args);
	}
}

/**
 * Responsible for closing the game window after user clicked "Quit" in menu bar
 */
class WindowCloserTask implements Runnable {
	private JFrame window;

	/**
	 * Constructor
	 * 
	 * @param window - JFrame to be closed
	 */
	public WindowCloserTask(JFrame window) {
		this.window = window;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			window.dispose();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
