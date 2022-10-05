package br.edu.ies;

import lombok.extern.java.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Log
public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Integer PORT = 2022;
        log.info("[SERVER] Starting server on port: " + PORT);
        try {
            server = new ServerSocket(PORT);
            log.info("[SERVER] Server started...");
            while(true) {
                Socket socketClient = server.accept();
                log.info(String.format("Connection accepted %s:%s", socketClient.getInetAddress(), socketClient.getPort()));
                Processor processor = new Processor();
                processor.setClient(socketClient);
                Thread thread = new Thread(processor);
                thread.start();
                log.info("Thread started");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
