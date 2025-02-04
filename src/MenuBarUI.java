import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 * Menu interface for user to interact with.
 * 
 * Game
 *	|_ Start Game 
 *  |_ Mode -- 2-, 3-, 4-Player Game
 *  |_ Quit Game
 * Network
 *  |_ ***TBD*** 
 * Settings
 *  |_ Language -- En, Ch
 * About
 *  |_ About the game
 *  
 */

public class MenuBarUI extends JMenuBar {


	private static final long serialVersionUID = 2347000320348280562L;

	MenuBarUI() {

		JMenu game = new JMenu("Game");
		JMenu network = new JMenu("Network");
		JMenu settings = new JMenu("Settings");
		JMenu about = new JMenu("About");


		//Game:
		JMenuItem startGame = new JMenuItem("Start");
		JMenu gameMode = new JMenu("Mode");
		JMenuItem two = new JMenuItem("2-Player Game");
		JMenuItem three = new JMenuItem("3-Player Game");
		JMenuItem four = new JMenuItem("4-Player Game");
		gameMode.add(two);
		gameMode.add(three);
		gameMode.add(four);
		JMenuItem endGame = new JMenuItem("Quit Game");
		game.add(startGame);
		game.add(gameMode);
		game.add(endGame);
		this.add(game);

		// Network:
		this.add(network);

		// Settings:
		JMenu lang = new JMenu("Language");
		JMenuItem en = new JMenuItem("English");
		JMenuItem ch = new JMenuItem("Simplified Chinese");
		lang.add(en);
		lang.add(ch);
		settings.add(lang);
		this.add(settings);

		// About:
		JMenuItem gameInfo = new JMenuItem("About the game");
		about.add(gameInfo);
		this.add(about);

	}

}
