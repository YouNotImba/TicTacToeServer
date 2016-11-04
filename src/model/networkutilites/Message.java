package model.networkutilites;

import java.io.Serializable;

public class Message implements Serializable {

	//TYPES
	//1 - HANDSHAKE data = your Name
	//2 - ACCEPTED
	//3 - GAME STARTED : data = "o" or "x"
	//4 - GAME TURN
	//10 - YOUR TURN
	//5 - GAME OVER
	//30 - SEARCH GAME
	
	private final int type;
	private final String data;
	private int [][] matrix;
	
	

	public Message(int type, String data, int[][] matrix) {
		this.type = type;
		this.data = data;
		this.matrix = matrix;
	}

	public Message(int type) {
		this.type = type;
		this.data = null;
	}

	public Message(int type, String data) {
		this.type = type;
		this.data = data;
	}

	public int getType() {
		return this.type;
	}

	public String getData() {
		return this.data;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	
}
