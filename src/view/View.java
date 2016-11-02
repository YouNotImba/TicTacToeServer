package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import controller.Controller;

public class View extends JFrame {

	private static int WIDTH = 600;
	private static int HEIGHT = 600;
	private static Field[] fields = new Field[9];
	private Controller controller;
	private boolean turnComp = false;

	public void init() {

		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		/*
		 * JLabel waitForConnect = new JLabel("", SwingConstants.CENTER);
		 * waitForConnect.setForeground(Color.RED); waitForConnect.setFont(new
		 * Font("Serif", Font.PLAIN, 40));
		 * waitForConnect.setText("Waiting for opponent "); add(waitForConnect,
		 * BorderLayout.CENTER);
		 */
		setLayout(new GridLayout(3, 3));

		for (int i = 0; i < fields.length; i++) {
			fields[i] = new Field(190, 190);
			fields[i].setView(this);
			add(fields[i]);
				
			
		}
		//revalidate();
		fields[0].setX(0);
		fields[0].setY(0);
		fields[1].setX(0);
		fields[1].setY(1);
		fields[2].setX(0);
		fields[2].setY(2);
		fields[3].setX(1);
		fields[3].setY(0);
		fields[4].setX(1);
		fields[4].setY(1);
		fields[5].setX(1);
		fields[5].setY(2);
		fields[6].setX(2);
		fields[6].setY(0);
		fields[7].setX(2);
		fields[7].setY(1);
		fields[8].setX(2);
		fields[8].setY(2);
		setVisible(true);
		
	}

	public void updateFields(int[][] matrix) {
		for (int i = 0; i < fields.length; i++) {
			int xOrO = matrix[fields[i].getY()][fields[i].getX()];
			if (matrix[fields[i].getY()][fields[i].getX()] != 0) {
				if (!fields[i].isAlreadyDraw()) {
					fields[i].setxOrO(xOrO);
					fields[i].repaint();
					fields[i].setAlreadyDraw(true);
					fields[i].setClicable(false);
				}
			}
		}
	}

	public String getServerAddress() {

		// pane.setVisible(true);
		String input = JOptionPane.showInputDialog(this, "Enter server address : ");

		return input;
	}

	public void messageAboutGameStart(String figure) {
		String message = "";
		switch (figure) {
		case "o":
			message = "You go second ";
			break;
		case "x":
			message = "You go first - your turn :";
			break;
		default:
			message = "Sonthing wrong with starting game !";
			break;
		}
		
		JOptionPane.showMessageDialog(this, message);
	}
	
	public void gameOver(){
		JOptionPane.showMessageDialog(this, "Game Over !!! ");
	}

	public String defineName() {
		String name = JOptionPane.showInputDialog(this, "Enter your name : ");
		return name;
	}
	
	public boolean isTurnComplited(){
		return turnComp;
	}

	public String getServerPort() {
		String port = JOptionPane.showInputDialog(this, "Enter server port : ");
		return port;
	}

	public boolean isYourTurn() {
		return controller.isYourTurn();
	}

	public static Field[] getFields() {
		return fields;
	}

	public static void setFields(Field[] fields) {
		View.fields = fields;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setTurnComp(boolean turnComp) {
		this.turnComp = turnComp;
	}

	
}
