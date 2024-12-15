package server_code;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1234);
            System.out.println("Server started on port 1234");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                     BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {

                    System.out.println("Client connected.");

                    String msgFromClient;
                    while ((msgFromClient = bufferedReader.readLine()) != null) {
                        System.out.println("Client: " + msgFromClient);

                        bufferedWriter.write("MSG Received.");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();

                        if (msgFromClient.equalsIgnoreCase("Kraj"))
                            break;
                    }
                } catch (IOException e) {
                    System.err.println("Error during communication with client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error setting up the server: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing the server socket: " + e.getMessage());
                }
            }
        }
    }
}