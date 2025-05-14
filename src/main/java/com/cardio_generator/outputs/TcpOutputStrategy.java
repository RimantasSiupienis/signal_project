package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Implements {@link OutputStrategy} to output patient data via TCP.
 * Creates a TCP server that listens for peoples connections and streams  data.
 */
public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * Creates TCP server on the specified port.
     * @param port The port number to listen on
     * @throws IOException If server creation fails
     */
    public TcpOutputStrategy(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("TCP Server started on port " + port);

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.println("Client connected: " + clientSocket.getInetAddress());
            } catch (IOException e) {
                System.err.println("Error accepting client connection: " + e.getMessage());
            }
        });
    }

    /**
     * Outputs patient data to the connected TCP client.
     * @param patientId The patient id
     * @param timestamp The time of data recording in milliseconds since epoch
     * @param label The type of health data
     * @param data The measurement value
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}