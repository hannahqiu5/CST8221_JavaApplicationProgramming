package com.game.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.game.controller.*;
import com.game.model.*;

/**
 * A ui component that shows names, ranks and scores of the players.
  * @author Han Qiu [Student id 041135330]
 * @version 1.0
 * @see com.game.controller
 * @see com.game.model
 * @since 1.8
 */
public class LeaderboardUI extends JPanel {
	Controller controller;
	List<Player> playerList;
	private JLabel playerLabel;
	private JLabel scoreLabel;
	private JLabel lbl;

	private static final long serialVersionUID = 305378614847137360L;

	/**
	 * Initializes the LeaderboardUI panel with player rankings and scores.
	 * 
	 * @param @param controller - the controller that handles communication between
	 *               the model and the view.
	 */
	LeaderboardUI(Controller controller) {
		this.controller = controller;
		this.setBackground(new Color(181, 200, 255));
		this.setLayout(new GridLayout(5, 3));

		this.add(new JLabel("     ")); // skip (1,1) cell
		playerLabel = new JLabel(controller.getMessage("player"));
		this.add(playerLabel);

		scoreLabel = new JLabel(controller.getMessage("penaltyPoints"));
		this.add(scoreLabel);
		updateLeaderboard(controller.getGameModel().getPlayers());

	}

	/**
	 * Updates the board and sorts rankings in ascending order
	 * 
	 * @param players
	 */
	public void updateLeaderboard(List<Player> players) {
	    this.removeAll();

	    this.add(new JLabel("     ")); // Spacer
	    playerLabel.setText(controller.getMessage("player"));
	    scoreLabel.setText(controller.getMessage("penaltyPoints"));
	    this.add(playerLabel);
	    this.add(scoreLabel);

	    String[] medals = { "first.png", "second.png", "third.png", "fourth.png" };

	    playerList = new ArrayList<>(players); 
	    playerList.sort(Comparator.comparingInt(Player::getScore));

	    for (int i = 0; i < 4; i++) {
	        if (i < playerList.size()) {
	            Player player = playerList.get(i);

	            ImageIcon scaledIcon = new ImageIcon(
	                new ImageIcon(getClass().getResource("/Assets/" + medals[i]))
	                    .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)
	            );
	            this.add(new JLabel(scaledIcon));

	            String playerName = player.getName();
	            JLabel nameLabel = new JLabel(playerName, JLabel.CENTER);
	            this.add(nameLabel);

	            JLabel scoreLabel = new JLabel(String.valueOf(player.getScore()), JLabel.CENTER);
	            this.add(scoreLabel);
	        } else {
	            this.add(new JLabel()); 
	            this.add(new JLabel("-", JLabel.CENTER)); // Placeholder for player name
	            this.add(new JLabel("-", JLabel.CENTER)); // Placeholder for score
	        }
	    }
	    revalidate();
	    repaint();
	}

	// getters and setters
	public JLabel getLbl() {
		return lbl;
	}

	public void setLbl(JLabel lbl) {
		this.lbl = lbl;
	}

	public JLabel getPlayerLabel() {
		return playerLabel;
	}

	public void setPlayerLabel(JLabel playerLabel) {
		this.playerLabel = playerLabel;
	}

	public JLabel getScoreLabel() {
		return scoreLabel;
	}

	public void setScoreLabel(JLabel scoreLabel) {
		this.scoreLabel = scoreLabel;
	}

}
