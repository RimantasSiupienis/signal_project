package com.alerts;

import com.alerts.strategy.*;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.alerts.decorator.*;

import java.util.ArrayList;
import java.util.List;

public class AlertGenerator {

    private final DataStorage dataStorage;
    private final List<Alert> triggeredAlerts;
    private final List<AlertStrategy> strategies;

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
