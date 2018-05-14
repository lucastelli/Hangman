package console;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;

public class ClientGame {
    public static void main(String[] args){
        try{
            ClientPlayer clientPlayer = new ClientPlayer(InetAddress.getByName("172.20.10.2"),8888);
            clientPlayer.playGame();

        }catch(IOException e){
            System.out.println("Errore sulla porta");
            System.exit(1);
        }
    }
}
