import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * The area (JPanel with BorderLayout) is to play cards and receive announcements.
 * East, North and West panel (BoxLayout) -- opponent's name (JLabel in JPanel) and their cards （JButton in JPanel using Flowlayout)
 * South panel (GridLayout) -- announcement (JTextField in JPanel using FlowLayout) and my cards (JButtons in JPanel using FlowLayout)
 * Center (GridLayout） -- the last card and card stack to draw from (JButtons)
 * 
 */
public class GameZoneUI extends JPanel {

	private static final long serialVersionUID = -7025809731986875797L;
	  /**
     * Constructor for initializing the GameZoneUI.
     */
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
			for(int j = 0; j < 11; j++) { 
				cardsPanel[i].add(createCardButton("lback.png"));
			}
			
			// the card on the top
			cardsPanel[i].add(createCardButton("back.png"));
			
			panels[i].add(player);
			panels[i].add(cardsPanel[i]);
			
			this.add(panels[i], layout[i]);
		} 

		// center panel to show the last card being played and card stack
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1, 1));
		center.setBackground(new Color(206, 237, 206));
		
		// JButton to contain the last card
		JButton currentCardButton = createCardButton("Qc.png");
		currentCardButton.setContentAreaFilled(false);
		center.add(currentCardButton);

		// another JButton to contain card stack
		ImageIcon stack = new ImageIcon(getClass().getResource("Assets/cardstack.png"));
		JButton stackButton = new JButton(stack);
		stackButton.setBorderPainted(false);
		stackButton.setBackground(new Color(206, 237, 206));
		
		center.add(stackButton);
		this.add(center, BorderLayout.CENTER);
		
		
		// *** south panel for my cards ***
		JPanel south = new JPanel();
		south.setLayout(new GridLayout(2,1)); // one for announcements, one for cards
		
		JPanel announcement = new JPanel();
		announcement.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	
		String message = "It's your turn!";
		JTextField content = new JTextField("System: " + message, 40);
		content.setFont(new Font("SansSerif", Font.PLAIN, 18));
		content.setBackground(Color.LIGHT_GRAY);
		content.setEditable(false);
		
		announcement.setBackground(new Color(206, 237, 206));
		announcement.add(content);
		
		JPanel myHand = new JPanel();
		myHand.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));  // remove gaps between buttons
		myHand.setBackground(new Color(206, 237, 206));
	
		String[] myCards = {"l6s.png", "l9h.png", "lKh.png", "l5s.png", "l1h.png", "l6d.png", "lKs.png", "l7h.png", "l6c.png", "l1s.png", "l9s.png", "8c.png"};
		for (int i = 0; i < myCards.length; i++ ) {
			JButton cardButton = createCardButton(myCards[i]);
		    myHand.add(cardButton);
		}
		
		
		south.add(announcement);
		south.add(myHand);

		this.add(south, BorderLayout.SOUTH);
		
		
	}
	
	JButton createCardButton(String cardFileName) {
	    ImageIcon myCard = new ImageIcon(getClass().getClassLoader().getResource("Assets/" + cardFileName));
	    JButton myCardButton = new JButton(myCard);
	    myCardButton.setBorderPainted(false);
	    if (cardFileName.charAt(0)!= 'l') {
		    myCardButton.setPreferredSize(new Dimension(71, 96));

	    } else {
		    myCardButton.setPreferredSize(new Dimension(14, 96));

	    }
	    return myCardButton;
	}
	
}
