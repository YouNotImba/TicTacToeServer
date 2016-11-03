package model;

import java.io.IOException;
import java.net.Socket;

import controller.Controller;
import model.networkutilites.Connection;
import model.networkutilites.Message;
import view.View;

public class Client {
	private static Controller controller;
	private static boolean isGameStart = false;
	private static boolean isYoutTurn = false;
	private static boolean isGameOver = false;
	private static int[][] matrix = new int[3][3];
	public static int yourFigure;
	protected static Connection connection;
	private static String serverAddress;
	private static int serverPort;
	

	public class SocketThread extends Thread {

		protected void processIncomingMessage(Message message) {
			matrix = message.getMatrix();
			controller.updateFields(matrix);
		}

		/*
		 * protected void notifyConnectionStatusChanged(boolean clientConnected)
		 * { Client.this.clientConnected = clientConnected; synchronized
		 * (Client.this) { Client.this.notify(); } }
		 */

		public void clientHandshake() throws IOException, ClassNotFoundException {
			while (true) {
				String name = controller.defineName();
				Message msg = new Message(1, name);
				connection.send(msg);
				Message answer = connection.receive();
				if (answer.getType() == 2) {
					/*
					 * sessionId = answer.getKey();
					 * notifyConnectionStatusChanged(true);
					 */
					System.out.println("Connection established !");
					return;
				} else
					throw new IOException("Connection failed !");
			}
		}

		public void gameStarted() throws ClassNotFoundException, IOException {
			while (true) {
				Message msg = connection.receive();
				if (msg.getType() == 3) {
					isGameStart = true;
					String figure = msg.getData();
					if (figure.equals("o")) {
						yourFigure = 1;
						isYoutTurn = false;
						controller.messageAboutGameStart(figure);
					} else {
						yourFigure = 2;
						isYoutTurn = true;
						controller.messageAboutGameStart(figure);
					}
				}
				// else Throw new RuntimeException("")
			}
		}

		protected void clientMainLoop() throws IOException, ClassNotFoundException {
			while (!isGameOver) {
				if (isYoutTurn) {
					if (!controller.isTurnComplited())
						continue;
					else {
						Message message = new Message(4, "", matrix);
						connection.send(message);
						controller.setTurnComp(false);
						isYoutTurn = false;
					}
				} else {
					Message msg = connection.receive();
					if (msg.getType() == 4) {
						matrix = msg.getMatrix();
						controller.updateFields(matrix);
						isYoutTurn = true;
					} else if (msg.getType() == 5) {
						isGameOver = true;
						controller.gameOver();
					}
				}
			}
		}

		public void run() {

			try {
				Socket socket = new Socket(serverAddress, serverPort);
				connection = new Connection(socket);
				clientHandshake();
				gameStarted();
				clientMainLoop();
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			}
		}
	}

	// ************************************************************************************************

	protected SocketThread getSocketThread() {
		return new SocketThread();
	}

	public void run() {
		SocketThread socketThread = getSocketThread();
		socketThread.setDaemon(true);
		socketThread.start();

	}

	// **************************************************************************************************
	public static void defineServerAddress() {
		String result = "";
		serverAddress = controller.getServerAddress();
	}

	public static void defineServerPort() {
		while (true) {
			try {
				int port = Integer.parseInt(controller.getServerPort());
				serverPort = port;
				return;
			} catch (NumberFormatException e) {

			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		controller = new Controller();
		Client client = new Client(controller);
		controller.setModel(client);
		controller.initView();
		defineServerAddress();
		defineServerPort();
		//controller.initView();
	//	client.run();
	}

	public Client(Controller controller) {
		this.controller = controller;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public boolean isYoutTurn() {
		return isYoutTurn;
	}

	public void setYoutTurn(boolean isYoutTurn) {
		this.isYoutTurn = isYoutTurn;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public int getYourFigure() {
		return yourFigure;
	}

	public void setYourFigure(int yourFigure) {
		this.yourFigure = yourFigure;
	}

	public boolean isGameStart() {
		return isGameStart;
	}

	public void setGameStart(boolean isGameStart) {
		this.isGameStart = isGameStart;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public int getSreverPort() {
		return serverPort;
	}

	public void setSreverPort(int sreverPort) {
		this.serverPort = sreverPort;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

}
