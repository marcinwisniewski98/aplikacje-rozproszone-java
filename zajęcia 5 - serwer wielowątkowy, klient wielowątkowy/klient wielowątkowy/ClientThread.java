package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread implements Runnable{
    protected Socket clientSocket = null;
    public ClientThread(Socket clientSocket) {this.clientSocket = clientSocket;}

    public void run(){
        //Socket clientSocket = null;
        //List<Socket> clientSockets = new ArrayList<>();
//        String host;
//        int port;
        String line = null;
        String line1;
        BufferedReader brSockInp = null;
        BufferedReader brLocalInp = null;
        DataOutputStream out = null;
        String threadName = Thread.currentThread().getName();

        try{
            out = new DataOutputStream(clientSocket.getOutputStream());
            brSockInp = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            brLocalInp = new BufferedReader(new InputStreamReader(System.in));
        }
        catch (IOException e){
            System.out.println(threadName + " | Błąd przy tworzeniu strumieni: " + e);
            return;
        }



        while (true){
            try{
                System.out.print(threadName + " | Podaj dane: ");
                line = brLocalInp.readLine();
                if(line != null) {
                    System.out.println(threadName + " | Wysyłam: " + line);
                    out.writeBytes(line + "\n");
                    out.flush();
                }
                if (line == null || "quit".equals(line)){
                    System.out.println(threadName + " | Kończenie pracy...");
                    clientSocket.close();
                    return;
                }
                line1 = brSockInp.readLine();
                System.out.println(threadName + " | Otrzymano: " + line1);
            }
            catch (IOException e){
                System.out.println(threadName + " | Błąd wejścia-wyjścia: " + e);
                return;
            }
        }
    }
}
