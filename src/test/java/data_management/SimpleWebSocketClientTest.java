package data_management;

import java.io.PrintStream;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.SimpleWebSocketClient;

class SimpleWebSocketClientTest {

    private SimpleWebSocketClient client;
    private DataStorage dataStorage;

    @BeforeEach
    void setup() throws Exception {
        System.setOut(new PrintStream(System.out)); // force output to console
        client = new SimpleWebSocketClient(new URI("ws://localhost:8080"), 1);
        dataStorage = DataStorage.getInstance();
        DataStorage.clear();
    }

    private void setPortName(String value) {
        try {
            var field = SimpleWebSocketClient.class.getDeclaredField("portName");
            field.setAccessible(true);
            field.set(client, value);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Could not set portName via reflection");
        }
    }

    @Test
    void testReadData_ValidInput() {
        String message = "101,1652991234567,ECG,0.85";

        setPortName("ECG");
        client.readData(dataStorage, message);

        boolean found = dataStorage.getAllPatients().stream()
                .anyMatch(p -> p.getPatientId() == 101);

        System.out.println("testReadData_ValidInput found=" + found);
        assertTrue(found);
    }

    @Test
    void testReadData_CorruptedInput() {
        String corruptedMessage = "wrong,format";

        System.out.println("Running corrupted input test...");
        assertDoesNotThrow(() -> client.readData(dataStorage, corruptedMessage));
        System.out.println("testReadData_CorruptedInput passed");
    }

    @Test
    void testOnMessage_IntegrationStyle() {
        String message = "202,1652991234567,OXYGEN,95.0";

        setPortName("OXYGEN");
        client.onMessage(message);

        boolean found = dataStorage.getAllPatients().stream()
                .anyMatch(p -> p.getPatientId() == 202);

        System.out.println("testOnMessage_IntegrationStyle found=" + found);
        assertTrue(found);
    }

}