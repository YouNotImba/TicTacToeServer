package model.networkutilites;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import com.google.gson.Gson;

public class Connection implements Closeable {
	private final Socket socket;
	private final ObjectOutputStream out;
	private final ObjectInputStream in;

	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
	}

	public void send(Message message) throws IOException {
		ObjectOutputStream var2 = this.out;
		synchronized (this.out) {
			Gson gson = new Gson();
			String result = gson.toJson(message);
			this.out.writeObject(result);
		}
	}

	public Message receive() throws IOException, ClassNotFoundException {
		ObjectInputStream var1 = this.in;
		synchronized (this.in) {
			Message msg = null;
			Gson gson = new Gson();
			String result = (String) this.in.readObject();
			msg = gson.fromJson(result, Message.class);
			return msg;
		}
	}

	public SocketAddress getRemoteSocketAddress() {
		return this.socket.getRemoteSocketAddress();
	}

	public void close() throws IOException {
		this.socket.close();
		this.out.close();
		this.in.close();
	}

}
