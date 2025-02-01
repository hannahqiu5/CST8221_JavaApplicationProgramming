import java.awt.Color;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatUI extends JPanel{
	/**
	 * The chat area contains two sections (JPanels) 
	 * -- one to display chat history and the other 
	 * to let user edit and send their message. 
	 * 
	 * 
	 */
	private static final long serialVersionUID = -3304986041555324967L;

	ChatUI(){
		// top section -- for displaying chat history
		JTextArea chatHistory = new JTextArea("", 20, 20); // JTextArea to contain messages
		chatHistory.setEditable(false);
		JScrollPane topSection = new JScrollPane(chatHistory); //JScrollPabe to contain JTextArea
		chatHistory.setBackground(new Color(255, 185, 140 ));
		
		
		// bottom section -- for editting and sending message
		JTextArea inputBox = new JTextArea("Type here ...", 2, 25); // for input
		JButton sendButton = new JButton("Send"); // click the button to send
		JPanel bottomSection = new JPanel();
		
		bottomSection.add(inputBox);
		bottomSection.add(sendButton);
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(topSection);
		this.add(bottomSection);
		
	}
}
