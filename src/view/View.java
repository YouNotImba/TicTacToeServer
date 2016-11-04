package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import controller.Controller;

public class View extends JFrame {

	private static int WIDTH = 640;
	private static int HEIGHT = 640;
	private static FieldButton[] fields = new FieldButton[9];
	private Controller controller;
	private JLabel info;
	private StartButton start;

	public void init() {

		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		
		info = new JLabel();
		info.setSize(new Dimension(200,20));
		info.setForeground(Color.BLUE);
		info.setText("TicTacToe game");
		
		start = new StartButton(this);
		
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(600,600));
		
		panel.setBackground(Color.black);
		panel.setBorder(new LineBorder(Color.YELLOW,2));
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		
		panel.setLayout(new GridLayout(3, 3,5,5));
		for (int i = 0; i < 9; i++) {
			panel.add(fields[i] = new FieldButton());
			fields[i].setView(this);
			
		}
		
				
		add(panel,BorderLayout.CENTER);
		add(info,BorderLayout.NORTH);
		add(start,BorderLayout.SOUTH);
		
		fields[0].setXx(0);
		fields[0].setYy(0);
		fields[1].setXx(1);
		fields[1].setYy(0);
		fields[2].setXx(2);
		fields[2].setYy(0);
		fields[3].setXx(0);
		fields[3].setYy(1);
		fields[4].setXx(1);
		fields[4].setYy(1);
		fields[5].setXx(2);
		fields[5].setYy(1);
		fields[6].setXx(0);
		fields[6].setYy(2);
		fields[7].setXx(1);
		fields[7].setYy(2);
		fields[8].setXx(2);
		fields[8].setYy(2);
		setVisible(true);
		
	}

	public void updateFields(int[][] matrix) {
		for (int i = 0; i < fields.length; i++) {
			int xOrO = matrix[fields[i].getYy()][fields[i].getXx()];
			if (matrix[fields[i].getYy()][fields[i].getXx()] != 0) {
				if (!fields[i].isAlreadyDraw()) {
					
					fields[i].setImage(xOrO);
					fields[i].setAlreadyDraw(true);
					fields[i].setClicable(false);
				}
			}
		}
	}

	public String getServerAddress() {

		String input = JOptionPane.showInputDialog(this, "Enter server address : ");

		return input;
	}

	public void newGame(){
		for (int i = 0; i < fields.length; i++) {
			fields[i].setClicable(true);
			fields[i].setAlreadyDraw(false);
			fields[i].setxOrO(0);
			fields[i].setImage(0);
		}
		controller.newGame();
	}
	
	public void findGame(){
		controller.findGame();
	}
	
	public void messageAboutGameStart(String figure) {
		String message = "";
		switch (figure) {
		case "o":
			message = "Game found ! You go second";
			break;
		case "x":
			message = "Game found ! You go first";
			break;
		default:
			message = "Sonthing wrong with starting game !";
			break;
		}
		
		JOptionPane.showMessageDialog(this, message);
	}
	
	public void gameOver(String message){
		JOptionPane.showMessageDialog(this, message);
	}

	public String defineName() {
		String name = JOptionPane.showInputDialog(this, "Enter your name : ");
		return name;
	}
	

	public String getServerPort() {
		String port = JOptionPane.showInputDialog(this, "Enter server port : ");
		return port;
	}

	public boolean isYourTurn() {
		return controller.isYourTurn();
	}

	public static FieldButton[] getFields() {
		return fields;
	}

	public static void setFields(FieldButton[] fields) {
		View.fields = fields;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void setInfo(String text){
		info.setText(text);
	}
	
	public static class StartButton extends JButton implements ActionListener{

		private View view;
		
		public StartButton(View view){
			this.view = view;
			setSize(new Dimension(100,60));
			setForeground(Color.BLUE);
			setText("Start Game");
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			view.setInfo("Searching game ...");
			view.findGame();
		}
		
	}
	
}
