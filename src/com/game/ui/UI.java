package com.game.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.game.controller.Controller;

/**
 * The primary container of the game. It has three sections -- menu, gameZone,
 * and rightPanel. The rightPanel is further divided into topSection and
 * bottomSection, containing LeaderboardUI and ChatUI, respectively.
 * @author Han Qiu [Student id 041135330]
 * @version 1.0
 * @see com.game.controller.Controller
 * @since 1.8
 */
public class UI {
	private Controller controller;
	private JFrame frame;
	private MenuBarUI menu;
	private GameZoneUI gameZone;
	private JPanel rightPanel;
	private LeaderboardUI lead;
	private ChatUI chat;

	/**
	 * Default constructor.
	 */
	public UI() {
		frame = new JFrame("Crazy Eights");
		rightPanel = new JPanel();
	}

	/**
	 * Initializes the UI components.
	 * 
	 * @param controller The controller to link with the UI.
	 */
	public void initializer(Controller controller) {
		this.controller = controller;
		this.menu = new MenuBarUI(controller);
		this.gameZone = new GameZoneUI(controller);
		this.lead = new LeaderboardUI(controller);
		this.chat = new ChatUI(controller);
		display();
	}

	/**
	 * Displays the main game window.
	 */
	public void display() {
		showSplashScreen();

		frame.setMinimumSize(new Dimension(1440, 720));
		frame.setJMenuBar(menu);
		frame.add(gameZone, BorderLayout.CENTER);

		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(lead, BorderLayout.NORTH);
		rightPanel.add(chat, BorderLayout.SOUTH);
		frame.add(rightPanel, BorderLayout.EAST);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}

	/**
	 * Displays a splash screen for 1 second.
	 */
	private void showSplashScreen() {
		JFrame splashFrame = new JFrame();
		JPanel splash = new JPanel();
		ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/Assets/icon.png"));
		JLabel label = new JLabel(backgroundImage);

		splash.add(label);
		splashFrame.add(splash);

		splashFrame.setLocationRelativeTo(null);
		splashFrame.setUndecorated(true); // Hide window decorations
		splashFrame.pack();
		splashFrame.setLocationRelativeTo(null); // center the window
		splashFrame.setVisible(true);
		try {
			Thread.sleep(1000); // Display splash screen for 1 second
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		splashFrame.dispose();
	}

	/**
	 * Updates the UI components.
	 */
	public void updateUI() {
		gameZone.refreshGameZone();
		menu.revalidate();
		menu.repaint();
		rightPanel.revalidate();
		rightPanel.repaint();
		lead.revalidate();
		lead.repaint();
		chat.revalidate();
		chat.repaint();

	}

	/**
	 * Updates all UI text elements based on the selected language.
	 */
	public void updateText() {
		menu.getGameMenu().setText(controller.getMessage("game"));
		menu.getNetworkMenu().setText(controller.getMessage("network"));
		menu.getSettingsMenu().setText(controller.getMessage("setting"));
		menu.getLang().setText(controller.getMessage("lang"));
		menu.getEn().setText(controller.getMessage("eng"));
		menu.getCh().setText(controller.getMessage("ch"));
		menu.getStartGame().setText(controller.getMessage("start"));
		menu.getGameMode().setText(controller.getMessage("mode"));
		menu.getTwo().setText(controller.getMessage("nplayer", "2"));
		menu.getThree().setText(controller.getMessage("nplayer", "3"));
		menu.getFour().setText(controller.getMessage("nplayer", "4"));
		menu.getEndGame().setText(controller.getMessage("quit"));

		lead.updateLeaderboard(controller.getGameModel().players);

		chat.getSendButton().setText(controller.getMessage("send"));
		chat.getInputBox().setText(controller.getMessage("type"));

	}

	// Getters and Setters

	public JFrame getFrame() {
		return frame;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public MenuBarUI getMenu() {
		return menu;
	}

	public void setMenu(MenuBarUI menu) {
		this.menu = menu;
	}

	public GameZoneUI getGameZone() {
		return gameZone;
	}

	public void setGameZone(GameZoneUI gameZone) {
		this.gameZone = gameZone;
	}

	public LeaderboardUI getLead() {
		return lead;
	}

	public void setLead(LeaderboardUI lead) {
		this.lead = lead;
	}

	public ChatUI getChat() {
		return chat;
	}

	public void setChat(ChatUI chat) {
		this.chat = chat;
	}

}