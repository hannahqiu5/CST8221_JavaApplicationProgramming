
package com.game.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.game.controller.Controller;

/**
 * Chat UI contains two sections -- one to display chat history and the other to
 * let user edit and send their messages.
  * @author Han Qiu [Student id 041135330]
 * @version 1.0
 * @see com.game.controller.Controller
 * @since 1.8 
 */
public class ChatUI extends JPanel implements ActionListener {

	Controller controller;

	private static final long serialVersionUID = -3304986041555324967L;
	private JButton sendButton;
	private JTextArea inputBox;
	private JTextArea chatHistoryArea;
	private String message;

	/**
	 * Constructor
	 * 
	 * @param controller - game controller that manages communication between UI and
	 *                   the model.
	 */
	ChatUI(Controller controller) {
		this.controller = controller;

		message = controller.getMessage("type");
		// top section -- for displaying chat history
		chatHistoryArea = new JTextArea("", 21, 20); // to contain all the chat messages
		chatHistoryArea.setEditable(false);
		JScrollPane topSection = new JScrollPane(chatHistoryArea); // to contain the JTextArea above
		chatHistoryArea.setBackground(new Color(255, 185, 140));

		// bottom section -- for editting and sending message
		JPanel bottomSection = new JPanel();
		inputBox = new JTextArea(message, 2, 25); // input area
		sendButton = new JButton(controller.getMessage("send")); // the "Send" button

		bottomSection.add(inputBox); // adding the two sections to the bottom panel
		bottomSection.add(sendButton);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(topSection);
		this.add(bottomSection);

		sendButton.addActionListener(this);
		MyKeyAdapter keyAdapter = new MyKeyAdapter();
		inputBox.addKeyListener(keyAdapter);
		MyMouseAdapter mouseAdapter = new MyMouseAdapter();
		inputBox.addMouseListener(mouseAdapter);
	}

// Getters and setters
	public JButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}

	public JTextArea getInputBox() {
		return inputBox;
	}

	public void setInputBox(JTextArea inputBox) {
		this.inputBox = inputBox;
	}

	public JTextArea getChatHistoryArea() {
		return chatHistoryArea;
	}

	public void setChatHistoryArea(JTextArea chatHistory) {
		this.chatHistoryArea = chatHistory;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void addMessage(String sender, String msg) {
		chatHistoryArea.append(sender + ": " + msg + "\n");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendButton) {
			String newMessage = inputBox.getText().trim();
			if (!newMessage.isEmpty() && !newMessage.equals(message)) {
				controller.handleSendMessage(newMessage);
			}
		}
	}

	private class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			char content = e.getKeyChar();
			System.out.print(content);
			if (content == '\n') {
				String newMessage = inputBox.getText().trim();
				if (!newMessage.isEmpty() && !newMessage.equals(message)) {
					controller.handleSendMessage(newMessage);
				}
				inputBox.setText("");
				e.consume(); // prevents new line
			}
		}
	}

	private class MyMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) { // clears "Type here..."
				inputBox.setText("");
			}
			System.out.println("Clear default message");
		}
	}
}
