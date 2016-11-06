package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import model.networkutilites.Connection;
import model.networkutilites.Message;

public class Server {
	protected static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();
	protected static Map<Handler, Connection> threadsMap = new ConcurrentHashMap<>();
	private static final AtomicInteger idsCount = new AtomicInteger(0);
	protected static ConcurrentLinkedQueue<Handler> gamePlayers = new ConcurrentLinkedQueue<>();

	public static class QueueService extends Thread {

		@Override
		public void run() {
			while (true) {
				if (gamePlayers.size() > 1) {
					System.out.println("QeueService find game ! ");
					Handler hand1 = gamePlayers.remove();
					Handler hand2 = gamePlayers.remove();
					GameLoopSession gls = new GameLoopSession(hand1, hand2);
					gls.start();
				}
			}
		}

	}

	public static class Handler extends Thread {

		protected Socket socket;
		private String sessionName;
		private Connection connection;


		public void wakeUp() {
			synchronized (this) {
				notifyAll();
			}
		}


		public Connection getConnection() {
			return connection;
		}


		public String getSessionName() {
			return sessionName;
		}


		public Handler(Socket socket) {
			this.socket = socket;
		}

		protected String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
			System.out.println(Thread.currentThread() + "handshake starteed !");
			String result = "";
			while (true) {
				Message answer = connection.receive();
				if (answer.getType() == 1) {
					result = answer.getData();
					if (!result.isEmpty() && !connectionMap.containsKey(result)) {
						String sessionKey = result + idsCount.getAndIncrement();
						if (!threadsMap.containsKey(this)) {
							threadsMap.put(this, connection);
						}

						Message autorization = new Message(2, "");
						connection.send(autorization);
						return sessionKey;
					} else
						continue;
				} else
					continue;
			}
		}

		public void run() {
			ConsoleHelper.writeMessage("connection established:" + socket.getRemoteSocketAddress());
			String clientName = "";
			
			try (Connection connection = new Connection(socket);) {
				this.connection = connection;
				clientName = serverHandshake(connection);
				Message queueRequest = connection.receive();
				
				if (queueRequest.getType() == 30) {
					gamePlayers.add(this);
				}
				
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}


			} catch (IOException e) {
				ConsoleHelper.writeMessage("cannot connect to remote server");
			} catch (ClassNotFoundException e) {
				ConsoleHelper.writeMessage("cannot connect to remote server");
			} finally {
				threadsMap.remove(this);
				ConsoleHelper.writeMessage("connection to remote server closed");
			}

		}
	}

	public static void main(String[] args) {
		ConsoleHelper.writeMessage("Enter server port :");
		int port = ConsoleHelper.readInt();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			ConsoleHelper.writeMessage("server was started");
		} catch (Exception e) {
			ConsoleHelper.writeMessage(e.getMessage());
		}
		QueueService qs = new QueueService();
		qs.start();
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				Handler handler = new Handler(socket);
				handler.start();
			}
		} catch (Exception e) {
			ConsoleHelper.writeMessage(e.getMessage());
			try {
				serverSocket.close();
			} catch (Exception e1) {
				ConsoleHelper.writeMessage(e1.getMessage());
			}
		}

	}
}
