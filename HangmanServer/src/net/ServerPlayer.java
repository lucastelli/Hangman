package net;

import console.LocalPlayer;
import hangman.Game;
import hangman.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPlayer extends LocalPlayer {
    private int port;
    private PrintWriter out;
    private BufferedReader in;

    public ServerPlayer(int port) throws IOException{
        this.port = port;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void update(Game game) {
        switch(game.getResult()) {
            case FAILED:
                out.println("failed"+" "+game.getSecretWord());
                break;
            case SOLVED:
                out.println("solved"+" "+game.getSecretWord());
                break;
            case OPEN:
                int rem = Game.MAX_FAILED_ATTEMPTS - game.countFailedAttempts();
                out.println("open"+" "+rem+" "+game.countFailedAttempts()+" "+game.getKnownLetters());
                break;
        }
    }

    @Override
    public char chooseLetter(Game game){
        for (;;) {
            String line = null;
            try {
                line = in.readLine().trim();
            } catch (IOException e) {
                line = "";
            }
            if (line.length() == 1 && Character.isLetter(line.charAt(0))) {
                return line.charAt(0);
            } else {
                System.out.println("Lettera non valida.");
            }
        }
    }
}
