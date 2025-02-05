import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * To show names, ranks and scores of the players.
 */
public class LeaderboardUI extends JPanel {

	private static final long serialVersionUID = 305378614847137360L;
	/**
	 * Initializes the LeaderboardUI panel with player rankings and scores.
	 */
	LeaderboardUI() {
		this.setBackground(new Color(181, 200, 255));
		this.setLayout(new GridLayout(5,3)); 
		
		this.add(new JLabel("     ")); //to skip (1,1) cell
		JLabel playerLabel = new JLabel("Player");
		playerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		this.add(playerLabel);
		
		JLabel scoreLabel = new JLabel("Score");
		scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		this.add(scoreLabel);
		
		
		String[] medals = {"first.png", "second.png", "third.png","fourth.png"};
		String[] names = {"player 2", "you", "player 1", "player 3"}; // name strings
		String[] scores = {"4", "2", "1", "0"};
		for(int i = 0; i < 4; i++) {
			ImageIcon image = new ImageIcon(getClass().getResource("Assets/" + medals[i]));
			Image img = image.getImage();
			Image scaledImg = img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
		    ImageIcon scaledIcon = new ImageIcon(scaledImg);
			JLabel medal = new JLabel(scaledIcon);
			JLabel name = new JLabel(names[i]);
			name.setFont(new Font("SansSerif", Font.PLAIN, 16));
			JLabel score = new JLabel(scores[i]);
			score.setFont(new Font("SansSerif", Font.PLAIN, 16));
			
			this.add(medal);
			this.add(name);
			this.add(score);
		}

	}
}
