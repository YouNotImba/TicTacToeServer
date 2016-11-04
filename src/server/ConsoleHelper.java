package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
	 private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	    public static void writeMessage(String message){
	        System.out.println(message);
	    }

	    public static String readString(){
	        String result = null;
	        while (true) {
	            try {
	                result = reader.readLine();
	            } catch (IOException e) {
	                writeMessage("Error occured, try again :");
	                continue;
	            }
	            return result;
	        }
	    }

	    public static int readInt(){
	        int result = 0;
	        while (true) {
	            try {
	                result = Integer.parseInt(readString());
	            } catch (NumberFormatException e) {
	                writeMessage("Error occured, try again :");
	                continue;
	            }
	            return result;
	        }
	    }
}
