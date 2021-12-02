package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        Socket clientSocket = null;
        String host;
        int port;
        String line = null;
        String line1;
        BufferedReader brSockInp = null;
        BufferedReader brLocalInp = null;
        DataOutputStream out = null;
        String threadName = Thread.currentThread().getName();

        try{
            host = args[0];
            port = Integer.parseInt(args[1]);
            System.out.println(host + " " + port);
            clientSocket = new Socket(host, port);
            //System.exit(0);
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


        try{
            out = new DataOutputStream(clientSocket.getOutputStream());
            brSockInp = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            brLocalInp = new BufferedReader(new InputStreamReader(System.in));
        }
        catch (IOException e){
            System.out.println("Błąd przy tworzeniu strumieni: " + e);
            System.exit(-1);
        }



        while (true){
            try{
                line = brLocalInp.readLine();
                if(line != null) {
                    System.out.println("Wysyłam: " + line);
                    out.writeBytes(line + "\n");
                    //out.flush();
                }
                if (line == null || "quit".equals(line)){
                    System.out.println("Kończenie pracy...");
                    clientSocket.close();
                    System.exit(0);
                }
                line1 = brSockInp.readLine();
                System.out.println(threadName + " | Otrzymano: " + line1);
            }
           catch (IOException e){
               System.out.println("Błąd wejścia-wyjścia: " + e);
               System.exit(-1);
           }
        }
    }
}