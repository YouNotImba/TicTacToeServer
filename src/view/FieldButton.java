package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.Client;

public class FieldButton extends JButton implements ActionListener {

	private View view;
	private int xOrO;
	private boolean isAlreadyDraw = false;
	private boolean shouldDraw = false;
	private boolean isClicable = true;
	private int xx;
	private int yy;
	
	private Icon xIcon;
	private Icon oIcon;
	
	public FieldButton(){
		setSize(new Dimension(190,190));
		xIcon = new ImageIcon(getClass().getResource("/x.jpg"));
		oIcon = new ImageIcon(getClass().getResource("/o.jpg"));
		addActionListener(this);
	
	}
	
	public void setImage(int value){
		if (!isAlreadyDraw) {
			if (value == 1) {
				setIcon(xIcon);
				isAlreadyDraw = true;
				isClicable = false;
			}
			if (value == 2) {
				setIcon(oIcon);
				isAlreadyDraw = true;
				isClicable = false;
			}
			// isAlreadyDraw = true;
			// isClicable = false;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (isClicable) {
			if (view.isYourTurn()) {
				// shouldDraw = true;
				xOrO = Client.yourFigure;
				//System.out.println("xOrO when clicked = " + xOrO);
				// repaint();
				view.setTurnComp(true);
				view.getController().getModel().getMatrix()[yy][xx] = Client.yourFigure;
				// isAlreadyDraw = true;
				setImage(Client.yourFigure);
			}
			isClicable = false;
			// isAlreadyDraw = true;
		}
		
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public int getxOrO() {
		return xOrO;
	}

	public void setxOrO(int xOrO) {
		this.xOrO = xOrO;
	}

	public boolean isAlreadyDraw() {
		return isAlreadyDraw;
	}

	public void setAlreadyDraw(boolean isAlreadyDraw) {
		this.isAlreadyDraw = isAlreadyDraw;
	}

	public boolean isShouldDraw() {
		return shouldDraw;
	}

	public void setShouldDraw(boolean shouldDraw) {
		this.shouldDraw = shouldDraw;
	}

	public boolean isClicable() {
		return isClicable;
	}

	public void setClicable(boolean isClicable) {
		this.isClicable = isClicable;
	}

	public int getXx() {
		return xx;
	}

	public void setXx(int x) {
		this.xx = x;
	}

	public int getYy() {
		return yy;
	}

	public void setYy(int y) {
		this.yy = y;
	}
	
	
	
}
