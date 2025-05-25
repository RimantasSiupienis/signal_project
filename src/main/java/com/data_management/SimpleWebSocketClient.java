package com.data_management;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class SimpleWebSocketClient extends WebSocketClient implements DataReader{

    int clientID;
    String portName;

    public SimpleWebSocketClient(URI serverUri,int clientID) {
        super(serverUri);
        
        this.clientID = clientID;
    }

    

    @Override
    public void readData(DataStorage dataStorage, String message){
         
        
        try {
            
        
        String[] segmentedMessage = message.split(",");
        
        int patientId = Integer.parseInt(segmentedMessage[0]);
        long timestamp = Long.parseLong(segmentedMessage[1]);
        String label = segmentedMessage[2];
        String data = segmentedMessage[3];

        dataStorage.addPatientData(patientId, timestamp, portName, timestamp);
        } catch (Exception e) { 
            
            System.out.println("Error: Message data corrupted: " + message);
        }
        
    }

   @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Connected client " + clientID +  " on: " + uri);
        System.out.println("Connected client " + clientID +  " on: " + uri);
    }

   @Override
    public void onMessage(String message){
        
        DataStorage dataStorage = DataStorage.getInstance();
        
        readData(dataStorage, message);
       

       
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
       send("Disconnected client " + clientID +  " from: " + uri + 
       " with code: " + code + " for reason: " + reason);
        System.out.println("Disonnected client " + clientID + " from: " + uri +
         " with code: " + code + " for reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        
        System.out.println("Encountered error: " + ex);
        ex.printStackTrace();
    }
    
}
