package com.alerts.factory;

import com.alerts.Alert;

public class BloodOxygenAlertFactory extends AlertFactory {

    /**
     * Creates an alert for blood oxygen levels.
     *
     * @param patientId the ID of the patient
     * @param condition the condition of the blood oxygen level
     * @param timestamp the timestamp of the alert
     * @return a new Alert object with the specified parameters
     */
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, "Blood Oxygen: " + condition, timestamp);
    }
}
