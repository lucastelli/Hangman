/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import hangman.Hangman;
import hangman.Player;

import java.io.IOException;


/**
 *
 * @author Claudio Cusano <claudio.cusano@unipv.it>
 */
public class HangmanServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try{
            Player player = new ServerPlayer(8888);
            Hangman game = new Hangman();
            System.out.println("Server aperto...");
            game.playGame(player);
        }catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    
}
