//package com.directi.training.tictactoe;

import java.io.*;
import java.net.*;

import static java.lang.System.out;

public class CommandLineClient {
    private String player1;
    private String player2;
    private final TicTacToe game = new TicTacToe();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public CommandLineClient() {
        try {


            ServerSocket myServerSocket = new ServerSocket(8885);

            Socket socket1 = myServerSocket.accept();
            BufferedReader in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            PrintStream out1 = new PrintStream(socket1.getOutputStream());

            out1.println("Welcome! Tic Tac Toe is a two player game.");
            out1.print("Enter player one's name: ");
            player1 = in1.readLine();

            out1.println();
            out1.println(game.getRules());
            out1.println();
            out1.println(game.drawBoard());
            out1.println();

            Socket socket2 = myServerSocket.accept();
            BufferedReader in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            PrintStream out2 = new PrintStream(socket2.getOutputStream());

            out2.print("Enter player two's name: ");
            player2 = in2.readLine();

            out2.println();
            out2.println(game.getRules());
            out2.println();
            out2.println(game.drawBoard());
            out2.println();

            while (!game.isOver()) {
                boolean validPick = false;
                BufferedReader in;
                PrintStream output;

                if(game.getCurrentPlayer()==1) {
                    in = in1;
                    output = out1;
                }
                else {
                    in = in2;
                    output = out2;
                }

                while (!validPick) {
                    output.print("It is " + currentPlayerName() + "'s turn. Pick a square: ");

                    try {
                        int pick = Integer.parseInt(in.readLine());
                        validPick = game.placeMarker(pick);
                    } catch (NumberFormatException e) {
                        //Do nothing here,
                    }

                    if (!validPick) {
                        output.println("Square can not be selected. Retry");
                    }
                }
                out1.println();
                out1.println(game.drawBoard());
                out1.println();

                out2.println();
                out2.println(game.drawBoard());
                out2.println();

                if (!game.isOver())
                    game.switchPlayers();

            }

            if (game.winner()) {
                out1.println("Game Over - " + currentPlayerName() + " WINS!!!");
                out2.println("Game Over - " + currentPlayerName() + " WINS!!!");
            } else {
                out1.println("Game Over - Draw");
                out2.println("Game Over - Draw");
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

  /*  protected String getPrompt() {
        String prompt = "";
        try {
            prompt = reader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prompt;
    }

*/

    public String currentPlayerName() {
        return game.getCurrentPlayer() == 1 ? player1 : player2;
    }

    public static void main(String[] args) {
        new CommandLineClient();
    }
}