import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * The primary container of the game. It has three sections -- menu, gameZone 
 * and rightPanel. The rightPanel is further divided into topSection and 
 * bottomSection, containing LeaderboardUI and ChatUI, respectively. 
 */
public class UI {
	/**
	 * display() creates a JFrame object and puts all the game components
	 * in specific positions using BorderLayout
	 */
	public void display() {
		JFrame frame = new JFrame("Crazy Eights");
		frame.setMinimumSize(new Dimension(1440, 720));
		
		MenuBarUI menu = new MenuBarUI();
		frame.setJMenuBar(menu);
		
		GameZoneUI gameZone = new GameZoneUI();
		frame.add(gameZone, BorderLayout.CENTER);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		LeaderboardUI lead = new LeaderboardUI();
		rightPanel.add(lead, BorderLayout.NORTH);
		
		ChatUI chat = new ChatUI();
		rightPanel.add(chat, BorderLayout.SOUTH);
		
		frame.add(rightPanel, BorderLayout.EAST);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
	}
	
	/** 
	 * default constructor 
	 */
	public UI() {}

}
