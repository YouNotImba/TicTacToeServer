package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Client;

@SuppressWarnings("serial")
public class Field extends JPanel implements MouseListener {

	private View view;
	private int width;
	private int height;
	private int xOrO;
	private boolean isAlreadyDraw = false;
	private boolean shouldDraw = false;
	private boolean isClicable = true;
	private int x;
	private int y;

	public Field(int width, int height) {
		this.width = width;
		this.height = height;
		setBackground(Color.WHITE);
		addMouseListener(this);
	}

	/*
	 * @Override public void paint(Graphics g) {
	 * System.out.println("paint method called ");
	 * 
	 * g.setColor(Color.BLACK); g.drawRect(10, 10, 175, 175);
	 * 
	 * if (!isAlreadyDraw) { if (xOrO == 1) { g.setColor(Color.RED);
	 * g.drawOval(45, 45, 100, 100); } if (xOrO == 2) { g.setColor(Color.RED);
	 * g.drawLine(45, 45, 145, 145); g.drawLine(145, 45, 45, 145); }
	 * //isAlreadyDraw = true; //isClicable = false; }
	 * 
	 * }
	 */
	// TODO
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*
		 * BufferedImage image = null;
		 * 
		 * try { image = ImageIO.read(new File(
		 * "C:/Users/i_karmatsky/workspace/TicTacToeClient/src/textures/TicTacBox.jpg"
		 * )); } catch (IOException e) { e.printStackTrace(); }
		 * 
		 * g.drawImage(image, 0, 0, null);
		 */
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.drawRect(10, 10, 175, 175);
		System.out.println("Paint component method called isAlreadyDrawn = " + isAlreadyDraw + " xOrO = " + xOrO);

		if (!isAlreadyDraw) {
			if (xOrO == 1) {
				g2d.setColor(Color.RED);
				g2d.drawOval(45, 45, 100, 100);
				isAlreadyDraw = true;
				isClicable = false;
			}
			if (xOrO == 2) {
				g2d.setColor(Color.RED);
				g2d.drawLine(45, 45, 145, 145);
				g2d.drawLine(145, 45, 45, 145);
				isAlreadyDraw = true;
				isClicable = false;
			}
			// isAlreadyDraw = true;
			// isClicable = false;
		}
	}

	public boolean isShouldDraw() {
		return shouldDraw;
	}

	public void setShouldDraw(boolean shouldDraw) {
		this.shouldDraw = shouldDraw;
	}

	public boolean isAlreadyDraw() {
		return isAlreadyDraw;
	}

	public void setAlreadyDraw(boolean isAlreadyDraw) {
		this.isAlreadyDraw = isAlreadyDraw;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isClicable() {
		return isClicable;
	}

	public void setClicable(boolean isClicable) {
		this.isClicable = isClicable;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getxOrO() {
		return xOrO;
	}

	public void setxOrO(int xOrO) {
		this.xOrO = xOrO;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouseClicked called isClicable = " + isClicable);
		if (isClicable) {
			if (view.isYourTurn()) {
				// shouldDraw = true;
				xOrO = Client.yourFigure;
				System.out.println("xOrO when clicked = " + xOrO);
				// repaint();
				view.setTurnComp(true);
				view.getController().getModel().getMatrix()[y][x] = Client.yourFigure;
				// isAlreadyDraw = true;
				repaint();

			}
			isClicable = false;
			// isAlreadyDraw = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
