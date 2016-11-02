package controller;

import javax.swing.JOptionPane;

import model.Client;
import view.View;

public class Controller {

	private static View VIEW;
	private Client model;

	public Controller() {
		VIEW = new View();
		VIEW.setController(this);
		//VIEW.init();				
	}
	
	public void initView(){
	
		VIEW.init();
	}
	
	public void gameOver(){
		VIEW.gameOver();
	}
	
	public boolean isTurnComplited(){
		return VIEW.isTurnComplited();
	}
	
	public void setTurnComp(boolean condition){
		VIEW.setTurnComp(condition);
	}
	
	public void messageAboutGameStart(String figure){
		VIEW.messageAboutGameStart(figure);
	}
	
	public String defineName(){
		return VIEW.defineName();
	}
	
	public void updateFields(int[] [] matrix){
		VIEW.updateFields(matrix);
	}
	
	
	public String getServerAddress(){
		return VIEW.getServerAddress();
	}
	
	public String getServerPort(){
		return VIEW.getServerPort();
	}
	
	public boolean isYourTurn(){
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
