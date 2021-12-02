package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable {
    protected Socket socket;
    public ServerThread(Socket clientSocket){
        this.socket = clientSocket  ;
    }
    public void run(){
        BufferedReader brinp = null;
        DataOutputStream out = null;
        String threadName = Thread.currentThread().getName();

        try {
            System.out.println("Inicjalizacja strumieni...");
            brinp = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e){
            System.out.println(threadName + " | Błąd przy tworzeniu strumieni" + e);
            return;
        }

        System.out.println("Rozpoczęcie pętli głównej...");
        while (true){
            try{
                String line = brinp.readLine();

                System.out.println(threadName + " | Odczytano linę: " + line);
                if (line == null || "quit".equals(line)){
                    try{
                        System.out.println(threadName + " | Kończenie pracy z klientem: " + socket);
                        socket.close();
                    } catch (IOException e) {
                        System.out.println("Błąd przy zamykaniu gniazda serwerowego");
                    }
                    return;
                }
                String result [] = line.split(" ");

                int a = Integer.parseInt(result[0]);
                int b = Integer.parseInt(result[1]);
                int c = a + b;
                String output = Integer.toString(c);
                out.writeBytes(output + "\n");
                System.out.println(threadName + " | Wysłano linię: " + output);
            }
            catch (IOException e){
                System.out.println(threadName + " | Błąd wejścia-wyjścia." + e);
                return;
            }
            catch (NumberFormatException e){
                System.out.println(threadName + " | Wprowadono niepoprawne dane!");
                try {
                    out.writeBytes("Wprowadź poprawne dane!" + "\n");
                } catch (IOException ex) {
                    System.out.println(threadName + " | Błąd wejścia-wyjścia" + e);
                    return;
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(threadName + " | Wprowadzono za mało argumentów!");
                try {
                    out.writeBytes("Wprowadzono za mało argumentów!" + "\n");
                }
                catch (IOException ex){
                    System.out.println(threadName + " | Błąd wejścia-wyjścia" + e);
                    return;
                }
            }
        }
    }
}
