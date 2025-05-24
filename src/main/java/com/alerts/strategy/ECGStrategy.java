package com.alerts.strategy;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.Alert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ECGStrategy implements AlertStrategy {

    private static final int WINDOW_SIZE = 5;
    private static final double SPIKE_THRESHOLD_MULTIPLIER = 1.5;

    @Override
    public List<Alert> checkAlert(Patient patient) {
        List<Alert> alerts = new ArrayList<>();
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        Queue<Double> window = new LinkedList<>();

        String id = String.valueOf(patient.getPatientId());

        for (PatientRecord record : records) {
            if (!record.getRecordType().equalsIgnoreCase("ecg")) continue;

            double value = record.getMeasurementValue();
            long timestamp = record.getTimestamp();

            if (window.size() == WINDOW_SIZE) {
                double avg = window.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                if (value > avg * SPIKE_THRESHOLD_MULTIPLIER) {
                    alerts.add(new Alert(id, "ECG spike detected: " + value + " (avg: " + avg + ")", timestamp));
                }
                window.poll();
            }
            window.add(value);
        }

        return alerts;
    }
}
