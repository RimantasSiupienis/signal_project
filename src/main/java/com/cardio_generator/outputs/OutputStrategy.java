package com.cardio_generator.outputs;

/**
 * Defines the output strategy for patient data.
 * Implementations handle how generated health data is output like console, file or network.
 */
public interface OutputStrategy {
    /**
     * Outputs patient data with the needed parameters.
     * @param patientId the patient id
     * @param timestamp the time of data recording
     * @param label the type of data 
     * @param data the actual value of the data
     */
    void output(int patientId, long timestamp, String label, String data);
}