package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientPlayer {
    private InetAddress hostName;
    private int port;
    private PrintWriter out;
    private BufferedReader in;
    private boolean inGame;
    private BufferedReader console;
    private int state;

    public ClientPlayer(InetAddress hostName, int port) throws IOException{
        this.port = port;
        this.hostName = hostName;
        Socket clientSocket = new Socket(hostName, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        console = new BufferedReader(new InputStreamReader(System.in));
        inGame = true;
        state = 0;
    }

    public void playGame() throws IOException{
        String[] stringaIn = {"","","",""};
        while(inGame){
            switch(state){
                case 0: stringaIn = in.readLine().split(" ");
                        update(stringaIn);
                        state = 10;
                        break;
                case 10: out.println(chooseLetter());
                        state = 0;
                        break;
            }
        }
    }

    public void update(String[] dataAcq) {
        switch(dataAcq[0]) {
            case "failed":
                printBanner("Hai perso!  La parola da indovinare era '" +
                        dataAcq[1] + "'");
                inGame = false;
                break;
            case "solved":
                printBanner("Hai indovinato!   (" + dataAcq[1] + ")");
                inGame = false;
                break;
            case "open":
                System.out.print("\n" + dataAcq[1] + " tentativi rimasti\n");
                System.out.println(this.gameRepresentation(Integer.parseInt(dataAcq[2])));
                System.out.println(dataAcq[3]);
                break;
        }
    }

    private String gameRepresentation(int countFailedAttempts) {
        int a = countFailedAttempts;

        String s = "   ___________\n  /       |   \n  |       ";
        s += (a == 0 ? "\n" : "O\n");
        s += "  |     " + (a == 0 ? "\n" : (a < 5
                ? "  +\n"
                : (a == 5 ? "--+\n" : "--+--\n")));
        s += "  |       " + (a < 2 ? "\n" : "|\n");
        s += "  |      " + (a < 3 ? "\n" : (a == 3 ? "/\n" : "/ \\\n"));
        s += "  |\n================\n";
        return s;
    }

    private void printBanner(String message) {
        System.out.println("");
        for (int i = 0; i < 80; i++)
            System.out.print("*");
        System.out.println("\n***  " + message);
        for (int i = 0; i < 80; i++)
            System.out.print("*");
        System.out.println("\n");
    }

    public char chooseLetter() {
        for (;;) {
            System.out.print("Inserisci una lettera: ");
            String line = null;
            try {
                line = console.readLine().trim();
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
