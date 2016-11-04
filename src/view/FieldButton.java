package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.Client;
import model.networkutilites.Message;

public class FieldButton extends JButton implements ActionListener {

	private View view;
	private int xOrO;
	private boolean isAlreadyDraw = false;
	private boolean isClicable = true;
	private int xx;
	private int yy;

	private Icon xIcon;
	private Icon oIcon;

	public FieldButton() {
		setSize(new Dimension(190, 190));
		xIcon = new ImageIcon(getClass().getResource("/x.jpg"));
		oIcon = new ImageIcon(getClass().getResource("/o.jpg"));
		addActionListener(this);

	}

	public void setImage(int value) {
		if (!isAlreadyDraw) {
			if (value == 1) {
				setIcon(oIcon);
				isAlreadyDraw = true;
				isClicable = false;
			}
			if (value == 2) {
				setIcon(xIcon);
				isAlreadyDraw = true;
				isClicable = false;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Clicked on button : "+ xx + " "+ yy +" isClicable = " + isClicable);
		if (isClicable) {
			if (view.isYourTurn()) {
				xOrO = Client.yourFigure;
				view.getController().getModel().getMatrix()[yy][xx] = Client.yourFigure;
				setImage(Client.yourFigure);
				try {
					view.getController().getModel().getConnection().send(new Message(4,"",view.getController().getModel().getMatrix()));
					view.getController().getModel().setYoutTurn(false);
				} catch (IOException e1) {
					
				}
				view.setInfo("Opponents turn ...");
				isClicable = false;
				isAlreadyDraw = true;
			}
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
