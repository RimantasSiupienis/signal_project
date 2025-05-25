package com.alerts.strategy;

import com.data_management.Patient;
import com.alerts.Alert;

import java.util.List;

/**
 * Interface for alert strategies in the healthcare system.
 * Defines a method to check alerts for a given patient.
 */
public interface AlertStrategy {
    List<Alert> checkAlert(Patient patient);
}