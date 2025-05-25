package com.alerts.factory;

import com.alerts.Alert;

/**
 * Abstract factory class for creating alerts in the system.
 * Defines the method for creating an alert based on patient ID, condition, and timestamp.
 */
public abstract class AlertFactory {
    public abstract Alert createAlert(String patientId, String condition, long timestamp);
}
