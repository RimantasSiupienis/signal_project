package com.alerts.factory;

import com.alerts.Alert;

public class ECGAlertFactory extends AlertFactory {

    /**
     * Creates an alert for ECG readings.
     *
     * @param patientId the ID of the patient
     * @param condition the condition of the ECG reading
     * @param timestamp the timestamp of the alert
     * @return a new Alert object with the specified parameters
     */
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, "ECG: " + condition, timestamp);
    }
}
