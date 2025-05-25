package com.alerts.strategy;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.Alert;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy for checking oxygen saturation alerts in the healthcare system.
 * This class implements the AlertStrategy interface and checks for low blood oxygen levels
 * and big drops in oxygen saturation.
 */
public class OxygenSaturationStrategy implements AlertStrategy {

    /**
     * Checks for oxygen saturation alerts for the given patient.
     * It analyzes the patient's records to find low blood oxygen levels
     * and significant drops in oxygen saturation.
     *
     * @param patient the patient whose records are to be checked
     * @return a list of alerts generated based on the patient's oxygen records
     */
    @Override
    public List<Alert> checkAlert(Patient patient) {
        List<Alert> alerts = new ArrayList<>();
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        String id = String.valueOf(patient.getPatientId());

        for (int i = 0; i < records.size(); i++) {
            PatientRecord record = records.get(i);
            if (record.getRecordType().equalsIgnoreCase("oxygen")) {
                double value = record.getMeasurementValue();
                if (value < 92) {
                    alerts.add(new Alert(id, "Low blood oxygen: " + value, record.getTimestamp()));
                }
                // check for drop of ≥5% in 10 minutes
                for (int j = i + 1; j < records.size(); j++) {
                    PatientRecord later = records.get(j);
                    if (!later.getRecordType().equalsIgnoreCase("oxygen")) continue;
                    long delta = later.getTimestamp() - record.getTimestamp();
                    if (delta <= 600000 && (record.getMeasurementValue() - later.getMeasurementValue()) >= 5) {
                        alerts.add(new Alert(id, "Rapid oxygen drop: " + record.getMeasurementValue() + " → " + later.getMeasurementValue(), later.getTimestamp()));
                        break;
                    }
                }
            }
        }

        return alerts;
    }
}
