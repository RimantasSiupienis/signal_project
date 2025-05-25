package data_management;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AlertGenerator class.
 * Tests various alert conditions based on patient data.
 */
public class AlertGeneratorTest {

    @BeforeEach
    public void resetDataStorage() throws Exception {
        // Clear DataStorage instance for clean tests
        var field = DataStorage.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null); // reset singleton
    }

    @Test
    public void testBloodPressureAlerts() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 200, "systolic", 1000);
        storage.addPatientData(1, 40, "diastolic", 1001);

        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateAll();

        List<Alert> alerts = generator.getTriggeredAlerts();
        assertTrue(alerts.stream().anyMatch(a -> a.getCondition().toLowerCase().contains("systolic")));
        assertTrue(alerts.stream().anyMatch(a -> a.getCondition().toLowerCase().contains("diastolic")));
    }

    @Test
    public void testOxygenSaturationAlerts() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 95, "oxygen", 2000);
        storage.addPatientData(1, 88, "oxygen", 2001); // triggers low + rapid

        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateAll();

        List<Alert> alerts = generator.getTriggeredAlerts();
        assertTrue(alerts.stream().anyMatch(a -> a.getCondition().toLowerCase().contains("oxygen")));
    }

    @Test
    public void testECGSpikeAlert() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 0.9, "ecg", 3000);
        storage.addPatientData(1, 0.8, "ecg", 3001);
        storage.addPatientData(1, 1.0, "ecg", 3002);
        storage.addPatientData(1, 0.95, "ecg", 3003);
        storage.addPatientData(1, 0.9, "ecg", 3004);
        storage.addPatientData(1, 1.6, "ecg", 3005); // this will be a spike

        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateAll();

        List<Alert> alerts = generator.getTriggeredAlerts();
        assertTrue(alerts.stream().anyMatch(a -> a.getCondition().toLowerCase().contains("ecg")));
    }

    @Test
    public void testHypotensiveHypoxemiaAlert() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 85, "systolic", 4000); // low BP
        storage.addPatientData(1, 88, "oxygen", 4001);   // low O2

        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateAll();

        List<Alert> alerts = generator.getTriggeredAlerts();
        assertEquals(3, alerts.size()); // BP + O2 + combined
        assertTrue(alerts.stream().anyMatch(a -> a.getCondition().toLowerCase().contains("hypotensive")));
    }
}
