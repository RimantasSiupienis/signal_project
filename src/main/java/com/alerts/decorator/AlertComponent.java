package com.alerts.decorator;

/**
 * Interface representing an alert component in a healthcare system.
 * This interface defines the methods that any alert component must implement.
 */
public interface AlertComponent {
    String getPatientId();
    String getCondition();
    long getTimestamp();
}
