package com.game.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import com.game.controller.Controller;
import com.game.model.*;

/**
 * GameZoneUI is where the players play their cards and see announcements. East,
 * north and west panels are for opponents. South panel has upper section to
 * display announcements and lower section shows my cards. Central panel has
 * left and right sections, displaying the current card and the stack of cards,
 * respectively.
  * @author Han Qiu [Student id 041135330]
 * @version 1.0
 * @see com.game.controller.Controller
 * @see com.game.model
 * @since 1.8
 */
public class GameZoneUI extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5864614854261623037L;

	Controller gameController;

	List<Card> myHandList;
	private JTextField content;
	private JPanel currentCardPanel;
	private JPanel south;
	private JPanel announcement;
	private JPanel center;
	private JPanel stackPanel = new JPanel();
	private JPanel oPanel; // panel for opponents
	String message;
	List<String> dirList = List.of("west", "north", "east");
	List<String> borderLayoutList = List.of(BorderLayout.WEST, BorderLayout.NORTH, BorderLayout.EAST);

	/**
	 * Constructor for initializing the GameZoneUI. It adds panels for player cards,
	 * announcements, and the central game zone.
	 * 
	 * @param controller - a game controller object
	 */
	public GameZoneUI(Controller controller) {
		this.gameController = controller;
		this.content = new JTextField(40);
		this.setBackground(new Color(206, 237, 206));
		this.setLayout(new BorderLayout());

		// panels to show opponents' hands
		for (int i = 0; i < controller.getGameModel().getNumPlayer() - 1; i++) {
			Player cpuPlayer = controller.getGameModel().getPlayers().get(i + 1);
			oPanel = createOpponentPanel(dirList.get(i), controller.getMessage("player") + i,
					cpuPlayer.getHand().size());

			this.add(oPanel, borderLayoutList.get(i));

		}

		// center panel to show current card and card stack
		center = new JPanel();
		center.setLayout(new GridLayout(1, 1));
		center.setBackground(new Color(206, 237, 206));

		myHandList = gameController.getGameModel().getPp().getHand();
		controller.getGameModel();

		// another JButton to contain card stack
		ImageIcon stack = new ImageIcon(getClass().getResource("/Assets/cardstack.png"));
		JButton stackButton = new JButton(stack);
		stackButton.setBorderPainted(false);
		stackPanel.setBackground(new Color(206, 237, 206));
		stackButton.setContentAreaFilled(false);
		stackPanel.add(stackButton);
		center.add(stackPanel);
		CardStackAdapter stackAdapter = new CardStackAdapter();
		stackButton.addMouseListener(stackAdapter);

		// JButton to contain the currentCard
		currentCardPanel = createMyPanel(Arrays.asList(Game.getCurrentCard()));
		center.add(currentCardPanel);
		this.add(center, BorderLayout.CENTER);

		// *** south panel for displaying my cards ***
		south = new JPanel();
		south.setLayout(new GridLayout(2, 1)); // one to contain announcements, one for cards

		announcement = new JPanel();
		announcement.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		message = gameController.getGameModel().getSystemMsg();
		content = new JTextField(gameController.getMessage("system") + message, 40);
		content.setFont(new Font("SansSerif", Font.PLAIN, 18));
		content.setBackground(Color.LIGHT_GRAY);
		content.setEditable(false);

		announcement.setBackground(new Color(206, 237, 206));
		announcement.add(content);

		south.add(announcement);

		south.add(createMyPanel(myHandList));

		this.add(south, BorderLayout.SOUTH);
		south.setVisible(true);

	}

	/**
	 * createOpponentPanel creates panels for opponents. The layout varies based on
	 * the opponent's position on the game board.
	 * 
	 * @param dir        A String specifying the opponent's direction ("north",
	 *                   "west", or "east").
	 * @param playerName A String containing the opponent's name
	 * @param numOfCards The number of cards the opponent has.
	 * @return JPanel containing opponents' names and cards
	 */
	private JPanel createOpponentPanel(String dir, String playerName, int numOfCards) {
		JPanel mainPanel = new JPanel();
		if (numOfCards == 0) {
			return mainPanel;
		}
		JPanel cardPanel = new JPanel();
		mainPanel.setBackground(new Color(206, 237, 206));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		cardPanel.setBackground(new Color(206, 237, 206));
		JLabel nameLabel = new JLabel(playerName);
		nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		if ("north".equals(dir)) {
			cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 45));
			for (int i = 0; i < numOfCards; i++) {
				String img = (i == numOfCards - 1) ? "back.png" : "lback.png";
				cardPanel.add(opponentCard(img));
			}

		} else if ("west".equals(dir) || "east".equals(dir)) {
			cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
			for (int i = 0; i < numOfCards; i++) {
				String img = (i == numOfCards - 1) ? "back.png" : "tback.png";
				JButton cardButton = opponentCard(img);
				cardButton.setAlignmentX(Component.LEFT_ALIGNMENT);
				cardButton.setMaximumSize(cardButton.getPreferredSize());
				cardPanel.add(cardButton);
			}

		} else {
			System.err.println("Unexpected direction: " + dir);
		}

		mainPanel.add(nameLabel);
		mainPanel.add(cardPanel);

		return mainPanel;
	}

	/**
	 * createCardButton creates an ImageIcon then add it to a JButton. The
	 * conditional statement decides what size the png should have.
	 * 
	 * @param cardFileName a string that contains relative path to the target png
	 * @return JButton containing desired png
	 */
	private JPanel createMyPanel(List<Card> myHand) {
		JPanel myCardPanel = new JPanel();
		myCardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); 
		myCardPanel.setBackground(new Color(206, 237, 206));
		int cardNum = myHand.size();
		Card card;
		JButton myCardButton;
		String path;

		for (int i = 0; i < cardNum; i++) {
			card = myHand.get(i);
			path = "Assets/" + card.toString() + ".png";
			java.net.URL imageUrl = getClass().getClassLoader().getResource(path);
			if (imageUrl == null) {
				System.err.println("Error: Image not found - " + myHand.get(0).toString());
				myCardPanel.add(new JButton()); // return empty button to avoid crash
			}

			ImageIcon myCard = new ImageIcon(imageUrl);
			myCardButton = new JButton(myCard);
			myCardButton.setPreferredSize(new Dimension(myCard.getIconWidth(), myCard.getIconHeight()));
			myCardButton.setBorderPainted(false);
			myCardButton.setContentAreaFilled(false);

			myCardButton.setActionCommand(card.toString()); // card's identifier

			myCardPanel.add(myCardButton);

			MyCardAdapter cardAdapter = new MyCardAdapter();
			myCardButton.addMouseListener(cardAdapter);
		}
		return myCardPanel;
	}

	/**
	 * creates a JButton with an image representing the opponent's card.
	 * 
	 * @param path the relative path to the image of the card
	 * @return A JButton containing the opponent's card image
	 */
	private JButton opponentCard(String path) {
		java.net.URL imageUrl = getClass().getClassLoader().getResource("Assets/" + path);
		if (imageUrl == null) {
			System.err.println("Error: Image not found ");
			return new JButton();
		}
		ImageIcon theirCard = new ImageIcon(imageUrl);
		JButton theirCardButton = new JButton(theirCard);
		theirCardButton.setBorderPainted(false);
		theirCardButton.setContentAreaFilled(false);
		theirCardButton.setPreferredSize(new Dimension(theirCard.getIconWidth(), theirCard.getIconHeight()));
		return theirCardButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton myCard = (JButton) (e.getSource());
			System.out.println(myCard.getText());
		}

	}

	// Adapter for handling card selection
	private class MyCardAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof JButton) {
				JButton myCardButton = (JButton) (e.getSource());
				String cardIdentifier = myCardButton.getActionCommand();

				for (Card card : myHandList) {
					if (card.toString().equals(cardIdentifier)) {
						gameController.handleCardSelection(card);
						break;
					}
				}
			}
		}
	}

	private class CardStackAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof JButton) {
				Player currentPlayer = gameController.getGameModel().getCurrentPlayer();
				if (currentPlayer == gameController.getGameModel().getPp()) {
					gameController.handleCardStackClick();
				}
			}
		}
	}

	/**
	 * Refreshes the game zone by updating the UI components.
	 */
	public void refreshGameZone() {
	    this.removeAll(); 

	    for (int i = 0; i < gameController.getGameModel().getCpuPlayerList().size(); i++) {
	        Player cpuPlayer = gameController.getGameModel().getCpuPlayerList().get(i);
	        JPanel opponentPanel = (cpuPlayer.getHand().size() == 0) 
	                ? new JPanel() 
	                : createOpponentPanel(dirList.get(i), 
	                		gameController.getMessage("player") + " " + cpuPlayer.getName().charAt(cpuPlayer.getName().length() - 1),
	                		cpuPlayer.getHand().size());
	        this.add(opponentPanel, borderLayoutList.get(i));
	    }

	    if (content != null) { 
	        message = gameController.getGameModel().getSystemMsg();
	        content.setText(gameController.getMessage("system") + ": " + message);
	    }

	    center.removeAll();
	    center.add(createMyPanel(Arrays.asList(Game.getCurrentCard())));
	    center.add(stackPanel);
	    this.add(center, BorderLayout.CENTER);

	    south.removeAll();
	    announcement.removeAll();
	    if (content != null) {
	        announcement.add(content);
	    }
	    south.add(announcement);
	    south.add(createMyPanel(myHandList));
	    this.add(south, BorderLayout.SOUTH);

	    this.revalidate();
	    this.repaint();
	}


	/**
	 * A modal dialog for the player to choose a suit
	 * 
	 * @return the suit selected by the player.
	 */
	public String showSuitDialog() {
		String[] suits = { gameController.getMessage("c"), 
				gameController.getMessage("s"),
				gameController.getMessage("d"), 
				gameController.getMessage("h") };

		JOptionPane optionPane = new JOptionPane(gameController.getMessage("chooseSuit"), 
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.DEFAULT_OPTION,      
				null, suits, suits[0]);

		JDialog dialog = optionPane.createDialog(gameController.getMessage("changeSuit"));

		dialog.setModal(true);
		dialog.setVisible(true);

		return (String) optionPane.getValue();
	}

}
