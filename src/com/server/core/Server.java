package com.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private boolean isRunning = false;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();

    }

    public void start() {
        try {
            String filePath = "src/studentInfo.txt";
            StudentInfoHandler.loadInfo(filePath);
            serverSocket = new ServerSocket(8080);
            isRunning = true;
            receive();
        } catch (Exception e) {
            System.out.println("failed to start up");
            throw new RuntimeException(e);

        }

    }

    public void receive() throws Exception {
            int number = 1;
            while (isRunning) {
                // handle multiple threads
                Socket clientSocket = serverSocket.accept();
                System.out.println("start thread "+ number++);
                new Thread(new Dispatcher(clientSocket)).start();
            }
    }

    public void stop() {
        isRunning = false;
        try {
            this.serverSocket.close();
            System.out.println("server stopped");
        } catch (IOException e) {
            System.out.println("failed to close serverSocket");
            throw new RuntimeException(e);
        }
    }


}
