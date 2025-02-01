import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameZoneUI extends JPanel {
	/**
	 * The area (JPanel with BorderLayout) is to play cards and receive announcements.
	 * East, North and West panel (BoxLayout) -- opponent's name (JLabel in JPanel) and their cards （JButton in JPanel using Flowlayout)
	 * South panel (GridBagLayout) -- announcement (JTextField) and my cards (JPanel FlowLayout)
	 * Center (GridLayout） -- the last card and card stack to draw from (JButtons)
	 * 
	 */
	private static final long serialVersionUID = -7025809731986875797L;

	GameZoneUI(){
		this.setBackground(new Color(206, 237, 206));
		this.setLayout(new BorderLayout());
		
		// ***panels for opponents' cards***
		JPanel west = new JPanel();
		JPanel north = new JPanel();
		JPanel east = new JPanel();
		JPanel cardsWest= new JPanel();
		JPanel cardsNorth = new JPanel();
		JPanel cardsEast = new JPanel();
		JPanel[] cardsPanel = {cardsWest, cardsNorth , cardsEast};
		JPanel[] panels = {west, north , east}; 
		String[] layout = {BorderLayout.WEST, BorderLayout.NORTH, BorderLayout.EAST};
		String[] playerName = {"player 1", "player 2", "player 3"}; // to store player's names
		
		
		for(int i = 0; i <3; i++) { 
			panels[i].setBackground(new Color(206, 237, 206));
			panels[i].setLayout(new BoxLayout(panels[i],BoxLayout.Y_AXIS));
			
			JPanel player = new JPanel();
			player.setBackground(new Color(206, 237, 206));
			JLabel name = new JLabel(playerName[i]);
			name.setFont(new Font("SansSerif", Font.PLAIN, 18));
			player.add(name);
			cardsPanel[i].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 45)); 
			cardsPanel[i].setBackground(new Color(206, 237, 206));
			for(int j = 0; j < 11; j++) { // the cards at bottom
				ImageIcon lback = new ImageIcon(getClass().getResource("Assets/lback.png"));
				JButton cardBehind = new JButton(lback);
				cardBehind.setPreferredSize(new Dimension(14,96));
				cardBehind.setBorderPainted(false); // to hide border of the button
				cardsPanel[i].add(cardBehind);
			}
			
			// the card on the top
			ImageIcon back = new ImageIcon(getClass().getResource("Assets/back.png"));
			JButton cardTop = new JButton(back);
			cardTop.setPreferredSize(new Dimension(71,96));
			cardTop.setBorderPainted(false);
			cardsPanel[i].add(cardTop);
			
			panels[i].add(player);
			panels[i].add(cardsPanel[i]);
			
			this.add(panels[i], layout[i]);
		} 

		// center panel to show the last card being played and card stack
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1, 1));
		center.setBackground(new Color(206, 237, 206));
		
		// JButton to contain the last card
		ImageIcon currentCard = new ImageIcon(getClass().getResource("Assets/Qc.png"));
		JButton currentCardButton = new JButton(currentCard);
		currentCardButton.setBorderPainted(false);
		currentCardButton.setContentAreaFilled(false);
		currentCardButton.setPreferredSize(new Dimension(71,96));
		center.add(currentCardButton);

		// another JButton to contain card stack
		ImageIcon cardStack = new ImageIcon(getClass().getResource("Assets/back.png"));
		JButton stackButton = new JButton(cardStack);
		stackButton.setBorderPainted(false);
		stackButton.setContentAreaFilled(false);
		stackButton.setPreferredSize(new Dimension(71,96));
		center.add(stackButton);
		this.add(center, BorderLayout.CENTER);
		
		
		// *** south panel for my cards ***
		JPanel south = new JPanel();
		south.setLayout(new GridLayout(2,1)); // one for announcements, one for cards
		
		JPanel announcement = new JPanel();
		announcement.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		String message = "It's your turn!";
		JTextField content = new JTextField("System: " + message, 40);
		content.setFont(new Font("SansSerif", Font.PLAIN, 18));
		content.setBackground(Color.LIGHT_GRAY);
		content.setEditable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5,5,5,5);
		gbc.anchor = GridBagConstraints.WEST;
		
		announcement.setBackground(new Color(206, 237, 206));
		announcement.add(content);
		
		JPanel myHand = new JPanel();
		myHand.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));  // remove gaps between buttons
		myHand.setBackground(new Color(206, 237, 206));
		String[] myCards = {"Assets/l6s.png","Assets/l9h.png","Assets/lKh.png","Assets/l5s.png","Assets/l1h.png","Assets/l6h.png"};
		for(String card:myCards) {
			ImageIcon myCard = new ImageIcon(getClass().getResource(card));
			JButton myCardButton = new JButton(myCard);
			myCardButton.setBorderPainted(false);
			myCardButton.setPreferredSize(new Dimension(14,96));
			myHand.add(myCardButton);
		}
		
		ImageIcon myCardTop = new ImageIcon(getClass().getResource("Assets/8c.png"));
		JButton topCardButton = new JButton(myCardTop);
		topCardButton.setBorderPainted(false);
		topCardButton.setPreferredSize(new Dimension(71,96));
		myHand.add(topCardButton);
		
		
		south.add(announcement);
		south.add(myHand);

		this.add(south, BorderLayout.SOUTH);
		
		
	}
	
}
