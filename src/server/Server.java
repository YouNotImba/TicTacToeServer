package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import model.networkutilites.Connection;
import model.networkutilites.Message;

public class Server {
	protected static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();
	private static final AtomicInteger idsCount = new AtomicInteger(0);
	protected static ConcurrentLinkedQueue<String> gamePlayers = new ConcurrentLinkedQueue<>();

	
	public static class QueueService extends Thread{

		@Override
		public void run() {
			while(true){
				if(gamePlayers.size()>1){
					System.out.println("QeueService find game ! ");
					Connection con1 = connectionMap.get(gamePlayers.remove());
					Connection con2 = connectionMap.get(gamePlayers.remove());
				    GameLoopSession gls = new GameLoopSession(con1, con2);
					gls.start();
				}
			}
		}
		
	}
	
	
	
	public static class Handler extends Thread {
		protected Socket socket;

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
						if (!connectionMap.containsKey(sessionKey)) {
							connectionMap.put(sessionKey, connection);
							gamePlayers.add(sessionKey);
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

		/*
		 * protected void sendListOfUsers(Connection connection, String
		 * userName) throws IOException{ for (Map.Entry<String,Connection>
		 * stringConnectionEntry : connectionMap.entrySet()) { if
		 * (!stringConnectionEntry.getKey().equals(userName)) { Message
		 * userAdded = new
		 * Message(MessageType.USER_ADDED,stringConnectionEntry.getKey());
		 * connection.send(userAdded); } }
		 * 
		 * }
		 */
		/*public void serverCoinStart() throws IOException, ClassNotFoundException {
			while (true) {
				if (gamePlayers.size() > 1) {
					Connection connection = connectionMap.get(gamePlayers.get(0));
					connection.send(new Message(3, "o"));
					Connection connection1 = connectionMap.get(gamePlayers.get(1));
					connection1.send(new Message(3, "x"));
					break;
				}
			}
		}*/

	/*	public void serverMainLoop() throws IOException, ClassNotFoundException {
			System.out.println(Thread.currentThread() + "mainloop starteed !");
			Connection connection = connectionMap.get(gamePlayers.get(0));
			Connection connection1 = connectionMap.get(gamePlayers.get(1));
			while (true) {
				//connection.send(new Message(5, "Your turn :", ""));
				Message answer = connection1.receive();
				System.out.println("answer from connection1 being received ");
				int matrix [][] = answer.getMatrix();
				
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[0].length; j++) {
						System.out.print(matrix[i][j]+" ");
					}
					System.out.println();
					
				}
				connection.send(new Message(4,"",matrix));
				System.out.println("message to connection being sended ");
				Message answer1 = connection.receive();
				System.out.println("answer from connection being received ");
				connection1.send(new Message(4,"",answer1.getMatrix()));
	
				// else
				// ConsoleHelper.writeMessage("wrong turn" + message.getType() +
				// " " + message.getData() + " " + message.getKey());
			}

		}*/

		/*
		 * protected void serverMainLoop(Connection connection, String userName)
		 * throws IOException, ClassNotFoundException{ while (true) { Message
		 * message = connection.receive();
		 * if(message.getType().equals(MessageType.TEXT)){ String text =
		 * userName+": "+message.getData(); Message broadcast = new
		 * Message(MessageType.TEXT,text); sendBroadcastMessage(broadcast); }
		 * else ConsoleHelper.writeMessage("error"); } }
		 */

		public void run() {
			ConsoleHelper.writeMessage("connection established:" + socket.getRemoteSocketAddress());
			String clientName = "";
			try (Connection connection = new Connection(socket);) {
				clientName = serverHandshake(connection);
				while(true){
					
				}
				//serverCoinStart();
				// sendBroadcastMessage(new
				// Message(MessageType.USER_ADDED,clientName));
				// sendListOfUsers(connection,clientName);
				//serverMainLoop();
			} catch (IOException e) {
				ConsoleHelper.writeMessage("cannot connect to remote server");
			} catch (ClassNotFoundException e) {
				ConsoleHelper.writeMessage("cannot connect to remote server");
			} /*finally {
				if (!clientName.isEmpty()) {
					connectionMap.remove(clientName);
					// sendBroadcastMessage(new
					// Message(MessageType.USER_REMOVED,clientName));
				}
			}
			ConsoleHelper.writeMessage("connection to remote server closed");
*/
		}
	}

	public static void sendBroadcastMessage(Message message) {
		for (Map.Entry<String, Connection> a : connectionMap.entrySet()) {
			try {
				a.getValue().send(message);
			} catch (IOException e) {
				ConsoleHelper.writeMessage("message send failed");
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
