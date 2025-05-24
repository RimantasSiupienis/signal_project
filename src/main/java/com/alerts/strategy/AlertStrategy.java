package com.alerts.strategy;

import com.data_management.Patient;
import com.alerts.Alert;

import java.util.List;

public interface AlertStrategy {
    List<Alert> checkAlert(Patient patient);
}