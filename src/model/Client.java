package model;

import java.io.IOException;
import java.net.Socket;

import controller.Controller;
import model.networkutilites.Connection;
import model.networkutilites.Message;

public class Client {
	private static Controller controller;
	private static boolean isGameStart = false;
	private static boolean isYoutTurn = false;
	private static boolean isGameOver = false;
	private static int[][] matrix = new int[3][3];
	public static int yourFigure;
	protected static Connection connection;
	private static String serverAddress = "localhost";
	private static int serverPort = 4444;

	public class SocketThread extends Thread {

		protected void processIncomingMessage(Message message) {
			matrix = message.getMatrix();
			controller.updateFields(matrix);
			// changed
		}

		public void clientHandshake() throws IOException, ClassNotFoundException {
			while (true) {
				String name = controller.defineName();
				Message msg = new Message(1, name);
				connection.send(msg);
				Message answer = connection.receive();
				if (answer.getType() == 2) {

					return;
				} else
					throw new IOException("Connection failed !");
			}
		}

		public void gameStarted() throws ClassNotFoundException, IOException {
			System.out.println("Client gameStarted() started  ");
			while (true) {
				Message msg = connection.receive();
				System.out.println("gameStarteed received msg");
				if (msg.getType() == 3) {
					System.out.println("gameStarteed sucesfully received msg");
					isGameStart = true;
					String figure = msg.getData();
					if (figure.equals("o")) {
						yourFigure = 1;
						isYoutTurn = false;
						setInfo("Opponents turn ...");
						controller.messageAboutGameStart(figure);
						break;
					} else {
						yourFigure = 2;
						isYoutTurn = true;
						controller.messageAboutGameStart(figure);
						break;
					}
				}
			}
		}

		protected void clientMainLoop() throws IOException, ClassNotFoundException {
			while (true) {
				gameStarted();
				while (!isGameOver) {
					Message msg = connection.receive();
					if (msg.getType() == 10) {
						isYoutTurn = true;
						setInfo("Your turn ...");
					}
					if (msg.getType() == 4) {
						matrix = msg.getMatrix();
						controller.updateFields(matrix);
					} else if (msg.getType() == 5) {
						isGameOver = true;
						controller.gameOver(msg.getData());

					}
				}
			}

		}

		public void run() {

			try {
				Socket socket = new Socket(serverAddress, serverPort);
				connection = new Connection(socket);
				clientHandshake();
				// gameStarted();
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
		// defineServerAddress();
		// defineServerPort();
		client.run();
	}

	public void newGame() {
		matrix = new int[3][3];
		isYoutTurn = false;
		yourFigure = 0;
		isGameOver = false;
	}

	public void findGame() {
		try {
			connection.send(new Message(30, ""));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void setInfo(String msg) {
		controller.setInfo(msg);
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

	public void gameOver(String message) {
		controller.gameOver(message);
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
