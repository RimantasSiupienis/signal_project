package com.alerts.decorator;

import com.alerts.Alert;

/**
 * BasicAlert is a concrete implementation of the AlertComponent interface.
 * It represents a basic alert in the healthcare system, encapsulating an Alert object.
 */
public class BasicAlert implements AlertComponent {
    private final Alert alert;

    /**
     * Constructs a BasicAlert with the specified Alert object.
     *
     * @param alert the Alert object to encapsulate
     */
    public BasicAlert(Alert alert) {
        this.alert = alert;
    }

    @Override
    public String getPatientId() {
        return alert.getPatientId();
    }

    @Override
    public String getCondition() {
        return alert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return alert.getTimestamp();
    }
}
