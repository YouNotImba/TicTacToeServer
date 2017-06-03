package controller;

import model.Client;
import view.View;

public class Controller {

	// lala
	private static View VIEW;
	private Client model;

	public Controller() {
		VIEW = new View();
		VIEW.setController(this);
		// VIEW.init();
		// Changed 2
	}

	public void initView() {

		VIEW.init();
	}

	public void gameOver(String message) {
		VIEW.gameOver(message);
	}

	public void findGame() {
		model.findGame();
	}

	public void newGame() {
		model.newGame();
	}

	public void setInfo(String msg) {
		VIEW.setInfo(msg);
	}

	public void messageAboutGameStart(String figure) {
		VIEW.messageAboutGameStart(figure);
	}

	public String defineName() {
		return VIEW.defineName();
	}

	public void updateFields(int[][] matrix) {
		VIEW.updateFields(matrix);
	}

	public String getServerAddress() {
		return VIEW.getServerAddress();
	}

	public String getServerPort() {
		return VIEW.getServerPort();
	}

	public boolean isYourTurn() {
		return model.isYoutTurn();
	}

	public static View getVIEW() {
		return VIEW;
	}

	public static void setVIEW(View vIEW) {
		VIEW = vIEW;
	}

	public Client getModel() {
		return model;
	}

	public void setModel(Client model) {
		this.model = model;
	}

}
