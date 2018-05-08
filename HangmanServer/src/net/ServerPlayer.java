package net;

import hangman.Game;
import hangman.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPlayer extends Player {
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

    @Override
    public char chooseLetter(Game game){
        char c = 0;
        try{
            c = in.readLine().charAt(0);
        }catch(IOException e){
            System.out.println("Lettera letta invalida");
            System.exit(1);
        }
        return c;
    }

    @Override
    public void update(Game game) {

    }
}
