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
 * GameZoneUI is where the players play their cards and see announcements.
 * East, north and west panels are for opponents. 
 * South panel has upper section to display announcements and lower section shows my cards.
 * Central panel has left and right sections, displaying the current card and the stack of 
 * cards, respectively. 
 */
public class GameZoneUI extends JPanel {

	private static final long serialVersionUID = -7025809731986875797L;
	  /**
      * Constructor for initializing the GameZoneUI.
      * It adds panels for player cards, announcements, and the central game zone.
      */
	GameZoneUI(){
		this.setBackground(new Color(206, 237, 206));
		this.setLayout(new BorderLayout());
		
		
		
		// panels to show opponents' hands
		JPanel west = createOpponentPanel("west", "player 1", 12);
		JPanel north = createOpponentPanel("north", "player 2", 12);
		JPanel east = createOpponentPanel("east", "player 3", 12);

		this.add(east, BorderLayout.EAST);
		this.add(north, BorderLayout.NORTH);
		this.add(west, BorderLayout.WEST);
		

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
		
		// *** south panel for displaying my cards ***
		JPanel south = new JPanel();
		south.setLayout(new GridLayout(2,1)); // one to contain announcements, one for cards
		
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
	
	
	/**
	 * createOpponentPanel creates panels for opponents.
	 * The layout varies based on the opponent's position on the game board.
	 * @param dir A String specifying the opponent's direction ("north", "west", or "east").
	 * @param playerName  A String containing the opponent's name
	 * @param numOfCards An integer storing the number of cards the opponent has.
	 * @return JPanel containing opponents' names and cards
	 */
		public JPanel createOpponentPanel(String dir, String playerName, int numOfCards) {
		JPanel mainPanel = new JPanel();
		JPanel cardPanel = new JPanel();
		mainPanel.setBackground(new Color(206, 237, 206));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		cardPanel.setBackground(new Color(206, 237, 206));
		JLabel nameLabel = new JLabel(playerName);
		nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		switch (dir) {
		case "north":
			cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 45)); 
			for(int i = 0; i < numOfCards - 1; i++) { 
				cardPanel.add(createCardButton("lback.png"));
			}
			break;
		case "west":
			cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS)); 
			for(int i = 0; i < numOfCards - 1; i++) { 
				cardPanel.add(createCardButton("tback.png"));
			}
			break;
		case "east":
			cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS)); 
			for(int i = 0; i < numOfCards - 1; i++) { 
				cardPanel.add(createCardButton("tback.png"));
			}
			break;
		}
		cardPanel.add(createCardButton("back.png"));
		mainPanel.add(nameLabel);
		mainPanel.add(cardPanel);	
		
		return mainPanel;
	}
	
	/**
	 * createCardButton creates an ImageIcon then add it to a JButton.
	 * The conditional statement decides what size the png should have.
	 * @param cardFileName a string that contains relative path to the target png
	 * @return JButton containing desired png
	 */
	JButton createCardButton(String cardFileName) {
	    ImageIcon myCard = new ImageIcon(getClass().getClassLoader().getResource("Assets/" + cardFileName));
	    JButton myCardButton = new JButton(myCard);
	    myCardButton.setBorderPainted(false);
	    if (cardFileName.charAt(0) == 'l') {
		    myCardButton.setPreferredSize(new Dimension(14, 96));
	    } else if (cardFileName.charAt(0) == 't') {
		    myCardButton.setPreferredSize(new Dimension(71, 14));
	    } else { // set to full size
		    myCardButton.setPreferredSize(new Dimension(71, 96));
	    }
	    return myCardButton;
	}
	
}
