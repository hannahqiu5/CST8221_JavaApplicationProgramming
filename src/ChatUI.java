import java.awt.Color;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * Chat UI contains two sections -- one to display chat history 
 * and the other to let user edit and send their messages. 
 * 
 */
public class ChatUI extends JPanel{

	private static final long serialVersionUID = -3304986041555324967L;

	ChatUI(){
		// top section -- for displaying chat history
		JTextArea chatHistory = new JTextArea("", 21,20); // to contain all the messages
		chatHistory.setEditable(false);
		JScrollPane topSection = new JScrollPane(chatHistory); // to contain the JTextArea above
		chatHistory.setBackground(new Color(255, 185, 140 ));
		
		
		// bottom section -- for editting and sending message
		JPanel bottomSection = new JPanel();
		JTextArea inputBox = new JTextArea("Type here ...", 2, 25); // input area
		JButton sendButton = new JButton("Send"); // the "Send" button
		
		bottomSection.add(inputBox); // adding the two sections to the bottom panel
		bottomSection.add(sendButton);
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS)); 
		this.add(topSection);
		this.add(bottomSection);
		
	}
}
