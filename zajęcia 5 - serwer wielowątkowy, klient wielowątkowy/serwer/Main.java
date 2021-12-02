package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try{
            serverSocket = new ServerSocket(6666);
        } catch (IOException e) {
            System.out.println("Błąd przy tworzeniu gniazda serwerowego. " + e);
            System.exit(-1);
        }
        System.out.println("Inicjalizacja gniazda zakończona.");
        System.out.println("Parametry gniazda: " + serverSocket);

        System.out.println("Trwa oczekiwanie na połączenie...");
        while(true){
            try{

                socket = serverSocket.accept();
            }
            catch (IOException e){
                System.out.println(e);
                System.exit(-1);
            }
            System.out.println("Nadeszło połączenie...");
            System.out.println("Parametry połączenia: " + socket);
            new Thread(new ServerThread(socket)).start();
        }
    }
}
