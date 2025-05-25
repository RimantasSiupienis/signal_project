package com.alerts;

import com.alerts.strategy.*;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.alerts.decorator.*;

import java.util.ArrayList;
import java.util.List;

/**
 * AlertGenerator is responsible for evaluating patient data and generating alerts based on strategies we made
 * and triggers appropriate alerts.
 */
public class AlertGenerator {

    private final DataStorage dataStorage;
    private final List<Alert> triggeredAlerts;
    private final List<AlertStrategy> strategies;

    /**
     * Constructs an AlertGenerator with the specified DataStorage.
     *
     * @param dataStorage the DataStorage instance to use for patient data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.triggeredAlerts = new ArrayList<>();

        this.strategies = List.of(
            new BloodPressureStrategy(),
            new OxygenSaturationStrategy(),
            new ECGStrategy()
        );
    }

    public void evaluateAll() {
        for (Patient patient : dataStorage.getAllPatients()) {
            evaluateData(patient);
        }
    }

    /**
     * Evaluates the data of a specific patient and generates alerts based on the defined strategies.
     *
     * @param patient the patient whose data is to be evaluated
     */
    public void evaluateData(Patient patient) {
        boolean lowBP = false;
        boolean lowOxygen = false;

        for (AlertStrategy strategy : strategies) {
            List<Alert> alerts = strategy.checkAlert(patient);
            for (Alert alert : alerts) {
                if (alert.getCondition().toLowerCase().contains("systolic") && alert.getCondition().contains("85")) {
                    lowBP = true;
                }
                if (alert.getCondition().toLowerCase().contains("oxygen") && alert.getCondition().contains("88")) {
                    lowOxygen = true;
                }

                triggerAlert(alert);
            }
        }

        if (lowBP && lowOxygen) {
            Alert hypoxemiaAlert = new Alert(String.valueOf(patient.getPatientId()), "Hypotensive Hypoxemia", System.currentTimeMillis());
            triggerAlert(hypoxemiaAlert);
        }
    }

    /**
     * Triggers an alert based on the provided Alert object.
     * It decorates the alert with additional information and adds it to the list of triggered alerts.
     *
     * @param alert the Alert object to be triggered
     */
    private void triggerAlert(Alert alert) {
        AlertComponent decorated = new BasicAlert(alert);
        decorated = new PriorityAlertDecorator(decorated);
        decorated = new RepeatedAlertDecorator(decorated, 1); // Dummy repeat count

        triggeredAlerts.add(new Alert(
                decorated.getPatientId(),
                decorated.getCondition(),
                decorated.getTimestamp()
        ));

        System.out.println("ALERT: " + decorated.getCondition() + " (Patient ID: " + decorated.getPatientId() + ")");
    }

    public List<Alert> getTriggeredAlerts() {
        return triggeredAlerts;
    }
}
