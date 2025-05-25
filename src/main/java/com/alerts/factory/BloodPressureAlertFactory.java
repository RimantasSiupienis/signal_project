package com.alerts.factory;

import com.alerts.Alert;

public class BloodPressureAlertFactory extends AlertFactory {

    /**
     * Creates an alert for blood pressure levels.
     *
     * @param patientId the ID of the patient
     * @param condition the condition of the blood pressure level
     * @param timestamp the timestamp of the alert
     * @return a new Alert object with the specified parameters
     */
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, "Blood Pressure: " + condition, timestamp);
    }
}
