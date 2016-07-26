/**
 * Created by rajat.go on 26/07/16.
 */
import java.io.*;
import java.net.*;
public class clientThread extends Thread{
    private final Socket socket1, socket2;
    private String player1, player2;
    private final TicTacToe game = new TicTacToe();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public clientThread(Socket socket1, Socket socket2) {
        this.socket1 = socket1;
        this.socket2 = socket2;
    }
    public void run() {
        try {
            BufferedReader in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            PrintStream out1 = new PrintStream(socket1.getOutputStream());
            BufferedReader in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            PrintStream out2 = new PrintStream(socket2.getOutputStream());

            out1.println("Second player has joined");
            out1.print("Player 1 enter your name: ");
            player1 = in1.readLine();

            out1.println();
            out1.println(game.getRules());
            out1.println();
            out1.println(game.drawBoard());
            out1.println();

            out2.print("Player 2 enter your name: ");
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
                    output.print(currentPlayerName() + " " + "your turn. Pick a square: ");

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
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String currentPlayerName() {
        return game.getCurrentPlayer() == 1 ? player1 : player2;
    }
}
