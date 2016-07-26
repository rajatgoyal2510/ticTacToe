/**
 * Created by rajat.go on 26/07/16.
 */

import java.io.*;
import java.net.*;

import static java.lang.System.out;

public class multiThreading {


    public multiThreading() {
        try {
            ServerSocket myServerSocket = new ServerSocket(5000);

            while(true) {
                Socket socket1 = myServerSocket.accept();
                PrintStream out1 = new PrintStream(socket1.getOutputStream());
                out1.println("Welcome! Tic Tac Toe is a two player game.");
                out1.println("Waiting for second player...");
                Socket socket2 = myServerSocket.accept();
                new clientThread(socket1, socket2).start();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new multiThreading();
    }
}
