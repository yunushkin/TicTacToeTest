package com.test.tictactoeclient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicTacToeClient {
    public static void main(String[] args)  throws InterruptedException {
        String host = "127.0.0.1";
        int port = 8086;
        if(args.length > 0) 
            host = new String(args[0]);
        if(args.length > 1)
            port = Integer.parseInt(args[1]);
        System.out.println("Waiting connection...");
        NetworkService network  = new NetworkService(host, port);
        try {
            network.connect();
        } catch (Exception e) {
            System.out.println("Couldn't connect to server...");
            return;
        }
        while(true) {
            Thread.sleep(1000);
        }
    }
}
