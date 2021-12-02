package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Socket clientSocket = null;
        List<Socket> clientSockets = new ArrayList<>();
        String host;
        int port;
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        try{
            for(int i = 0; i<args.length; i++){
                host = args[i];
                port = Integer.parseInt(args[++i]);
                System.out.println(host + " " + port);
                clientSocket = new Socket(host, port);
                clientSockets.add(clientSocket);
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Podaj dwa argumenty!");
            System.exit(-1);
        }
        catch (NumberFormatException e){
            System.out.println("Drugi argument musi być liczbą!");
            System.exit(-1);
        }
        catch (UnknownHostException e) {
            System.out.println("Niepoprawny ades!");
            System.exit(-1);
        }
        catch (ConnectException e){
            System.out.println("Nie udało się nawiązać połączenia.");
            System.exit(-1);
        }
        catch (IOException e){
            System.out.println(e);

        }

        for(int i = 0; i<clientSockets.size(); i++)
            executorService.execute(new ClientThread(clientSocket));
    }
}