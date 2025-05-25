package com.alerts;

/**
 * Represents an alert in a healthcare system.
 * This class holds the details of an alert, including patient ID, condition, and timestamp.
 */
public class Alert {
    private String patientId;
    private String condition;
    private long timestamp;
    
    /**
     * Constructs an Alert object with the specified patient ID, condition, and timestamp.
     *
     * @param patientId the ID of the patient
     * @param condition the condition associated with the alert
     * @param timestamp the time when the alert was created
     */
    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
