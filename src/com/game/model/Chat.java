package com.game.model;

/**
 * Represents a single chat message in the game.
 * @author Han Qiu [Student id 041135330]
 * @version 1.0
 * @since 1.8
 */
public class Chat {
	private String sender;
	private String content;

	/**
	 * Constructor to create a new Chat message
	 */
	public Chat(String sender, String content) {
		this.sender = sender;
		this.content = content;
	}

	// getters and setters
	public String getSender() {
		return sender;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return sender + ": " + content + "\n";
	}
}
