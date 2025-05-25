package com.alerts.strategy;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.Alert;

import java.util.ArrayList;
import java.util.List;


/**
 * Strategy for checking blood pressure alerts in the healthcare system.
 * This class implements the AlertStrategy interface and checks for critical
 * blood pressure readings, as well as trends in the readings.
 */
public class BloodPressureStrategy implements AlertStrategy {

    /**
     * Checks for blood pressure alerts for the given patient.
     * It analyzes the patient's records to find critical s and d readings
     * and detects trends in the blood pressure values.
     *
     * @param patient the patient whose records are to be checked
     * @return a list of alerts generated based on the patient's blood pressure records
     */
    @Override
    public List<Alert> checkAlert(Patient patient) {
        List<Alert> alerts = new ArrayList<>();
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);

        List<Double> systolics = new ArrayList<>();
        List<Double> diastolics = new ArrayList<>();

        for (PatientRecord r : records) {
            String type = r.getRecordType().toLowerCase();
            double value = r.getMeasurementValue();
            long time = r.getTimestamp();
            String id = String.valueOf(patient.getPatientId());

            if (type.equals("systolic")) {
                systolics.add(value);
                if (value > 180 || value < 90)
                    alerts.add(new Alert(id, "Critical systolic pressure: " + value, time));
            }
            if (type.equals("diastolic")) {
                diastolics.add(value);
                if (value > 120 || value < 60)
                    alerts.add(new Alert(id, "Critical diastolic pressure: " + value, time));
            }
        }

        // Detect increasing or decreasing trend
        if (detectTrend(systolics, 10)) {
            alerts.add(new Alert(String.valueOf(patient.getPatientId()), "Increasing systolic BP trend", System.currentTimeMillis()));
        }
        if (detectTrend(diastolics, -10)) {
            alerts.add(new Alert(String.valueOf(patient.getPatientId()), "Decreasing diastolic BP trend", System.currentTimeMillis()));
        }

        return alerts;
    }

    /**
     * Detects a trend in the given list of values.
     * A trend is considered increasing if the last three values show a consistent increase
     * greater than the specified direction threshold, or decreasing if its the opposite.
     *
     * @param values the list of values to analyze
     * @param directionThreshold the threshold for detecting increasing or decreasing trend
     * @return true if a trend is detected
     */
    private boolean detectTrend(List<Double> values, int directionThreshold) {
        if (values.size() < 3) return false;
        double d1 = values.get(values.size() - 3);
        double d2 = values.get(values.size() - 2);
        double d3 = values.get(values.size() - 1);

        if (directionThreshold > 0) {
            return d2 - d1 > directionThreshold && d3 - d2 > directionThreshold;
        } else {
            return d1 - d2 > -directionThreshold && d2 - d3 > -directionThreshold;
        }
    }
}
