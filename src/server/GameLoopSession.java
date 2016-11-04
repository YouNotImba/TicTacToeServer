package server;

import java.io.IOException;
import java.net.Socket;

import model.networkutilites.Connection;
import model.networkutilites.Message;

public class GameLoopSession extends Thread {

	private int[][] matrix = new int[3][3];
	private Connection connection;
	private Connection connection1;
	private boolean gameOver = false;

	public GameLoopSession(Connection connection, Connection connection2) {

		this.connection = connection;
		this.connection1 = connection2;
	}

	private boolean isGameOver() {
		if (matrix[0][0] == matrix[0][1] && matrix[0][1] == matrix[0][2] && matrix[0][0] != 0)
			return true;
		if (matrix[1][0] == matrix[1][1] && matrix[1][1] == matrix[1][2] && matrix[1][0] != 0)
			return true;
		if (matrix[2][0] == matrix[2][1] && matrix[2][1] == matrix[2][2] && matrix[2][0] != 0)
			return true;
		if (matrix[0][0] == matrix[1][0] && matrix[1][0] == matrix[2][0] && matrix[0][0] != 0)
			return true;
		if (matrix[0][1] == matrix[1][1] && matrix[1][1] == matrix[2][1] && matrix[0][1] != 0)
			return true;
		if (matrix[0][2] == matrix[1][2] && matrix[1][2] == matrix[2][2] && matrix[0][2] != 0)
			return true;
		if (matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2] && matrix[0][0] != 0)
			return true;
		if (matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0] && matrix[0][2] != 0)
			return true;
		return false;
	}
	
	private boolean isGameTie() {
		int count = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j]!=0)
					count++;
			}
		}
		if(count == 9)
			return true;
		else
			return false;
	}

	@Override
	public void run() {

		try {

			connection.send(new Message(3, "o"));
			connection1.send(new Message(3, "x"));

		} catch (IOException e1) {
			System.out.println("Error when coin !!");

		}
		while (!gameOver) {
			try {
				connection1.send(new Message(10, ""));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Message answer = null;
			try {
				answer = connection1.receive();
				
			} catch (Exception e) {
				System.out.println("Error when receiving data from con1");
			}
			matrix = answer.getMatrix();
			if(isGameOver()){
				try {
					connection.send(new Message(4, "", matrix));
					connection1.send(new Message(5,"You win !!!"));
					connection.send(new Message(5,"You lose !!!"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				gameOver = true;
				break;
			}
			if(isGameTie()){
				try {
					connection.send(new Message(4, "", matrix));
					connection1.send(new Message(5,"Tie !!!"));
					connection.send(new Message(5,"Tie !!!"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				gameOver = true;
				break;
			}

			try {
				connection.send(new Message(4, "", matrix));
				connection.send(new Message(10, ""));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Message answer1 = null;
			try {
				answer1 = connection.receive();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				matrix = answer1.getMatrix();
				if(isGameOver()){
					try {
						connection1.send(new Message(4, "", matrix));
						connection1.send(new Message(5,"You lose !!!"));
						connection.send(new Message(5,"You win !!!"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					gameOver = true;
					break;
				}
				if(isGameTie()){
					try {
						connection1.send(new Message(4, "", matrix));
						connection1.send(new Message(5,"Tie !!!"));
						connection.send(new Message(5,"Tie !!!"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					gameOver = true;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection1.send(new Message(4, "", matrix));
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		
		System.out.println("GameSeesion end !");
	}

}
