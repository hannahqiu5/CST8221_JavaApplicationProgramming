package com.game.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.game.controller.Controller;

/**
 * Represents the menu bar UI for the game, containing menus and menu items for
 * game settings and actions.
  * @author Han Qiu [Student id 041135330]
 * @version 1.0
 * @see com.game.controller.Controller
 * @since 1.8
 */
public class MenuBarUI extends JMenuBar {

	private static final long serialVersionUID = 2347000320348280562L;
	private JMenuItem startGame, two, three, four, endGame, en, ch;
	private JMenu gameMode, lang, gameMenu, networkMenu, settingsMenu;
	private Controller controller;

	/**
	 * nitializes the menu bar with various game menus, network settings, and
	 * language options.
	 * 
	 * @param controller - for handling user actions
	 */
	public MenuBarUI(Controller controller) {
		this.controller = controller;
		// Create Menus
		gameMenu = new JMenu(controller.getMessage("game"));
		networkMenu = new JMenu(controller.getMessage("network"));
		settingsMenu = new JMenu(controller.getMessage("setting"));

		// Game Menu
		startGame = new JMenuItem(controller.getMessage("start"));
		gameMode = new JMenu(controller.getMessage("mode"));
		two = new JMenuItem(controller.getMessage("nplayer", "2"));
		three = new JMenuItem(controller.getMessage("nplayer", "3"));
		four = new JMenuItem(controller.getMessage("nplayer", "4"));

		gameMode.add(two);
		gameMode.add(three);
		gameMode.add(four);

		endGame = new JMenuItem(controller.getMessage("quit"));

		gameMenu.add(startGame);
		gameMenu.add(gameMode);
		gameMenu.addSeparator();
		gameMenu.add(endGame);
		this.add(gameMenu);

		// Network Menu (Placeholder for Assignment 3)
		this.add(networkMenu);

		// Settings Menu
		lang = new JMenu(controller.getMessage("lang"));
		en = new JMenuItem(controller.getMessage("eng"));
		ch = new JMenuItem(controller.getMessage("ch"));
		lang.add(en);
		lang.add(ch);
		settingsMenu.add(lang);
		this.add(settingsMenu);

		// Add Action Listeners
		addStartGameListener(controller);
		addTwoListener(controller);
		addThreeGameListener(controller);
		addFourGameListener(controller);
		addEndGameListener(controller);
		addEngListener(controller);
		addChListener(controller);

	}

	// listeners
	public void addStartGameListener(Controller controller) {
		startGame.addActionListener(controller.new StartGameListener());
	}

	public void addTwoListener(Controller controller) {
		two.addActionListener(controller.new TwoListener());
	}

	public void addThreeGameListener(Controller controller) {
		three.addActionListener(controller.new ThreeListener());
	}

	public void addFourGameListener(Controller controller) {
		four.addActionListener(controller.new FourListener());
	}

	public void addEndGameListener(Controller controller) {
		endGame.addActionListener(controller.new EndGameListener());
	}

	public void addEngListener(Controller controller) {
		en.addActionListener(controller.new EngListener());
	}

	public void addChListener(Controller controller) {
		ch.addActionListener(controller.new ChListener());
	}

	public JMenuItem getStartGame() {
		return startGame;
	}

	public void setStartGame(JMenuItem startGame) {
		this.startGame = startGame;
	}

	public JMenuItem getTwo() {
		return two;
	}

	public void setTwo(JMenuItem two) {
		this.two = two;
	}

	public JMenuItem getThree() {
		return three;
	}

	public void setThree(JMenuItem three) {
		this.three = three;
	}

	public JMenuItem getFour() {
		return four;
	}

	public void setFour(JMenuItem four) {
		this.four = four;
	}

	public JMenuItem getEndGame() {
		return endGame;
	}

	public void setEndGame(JMenuItem endGame) {
		this.endGame = endGame;
	}

	public JMenuItem getEn() {
		return en;
	}

	public void setEn(JMenuItem en) {
		this.en = en;
	}

	public JMenuItem getCh() {
		return ch;
	}

	public void setCh(JMenuItem ch) {
		this.ch = ch;
	}

	public JMenu getGameMode() {
		return gameMode;
	}

	public void setGameMode(JMenu gameMode) {
		this.gameMode = gameMode;
	}

	public JMenu getLang() {
		return lang;
	}

	public void setLang(JMenu lang) {
		this.lang = lang;
	}

	public JMenu getGameMenu() {
		return gameMenu;
	}

	public void setGameMenu(JMenu gameMenu) {
		this.gameMenu = gameMenu;
	}

	public JMenu getNetworkMenu() {
		return networkMenu;
	}

	public void setNetworkMenu(JMenu networkMenu) {
		this.networkMenu = networkMenu;
	}

	public JMenu getSettingsMenu() {
		return settingsMenu;
	}

	public void setSettingsMenu(JMenu settingsMenu) {
		this.settingsMenu = settingsMenu;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
