package com.alerts.decorator;

public interface AlertComponent {
    String getPatientId();
    String getCondition();
    long getTimestamp();
}
