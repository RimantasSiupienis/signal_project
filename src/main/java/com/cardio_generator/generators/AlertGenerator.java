package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates random alert events for patients.
 * Alerts can be triggered or resolved based on probability.
 */
public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();

    private boolean[] alertStates; // false = resolved, true = triggered

    /**
     * Constructor that initializes alert states for patients.
     *
     * @param patientCount number of patients to track alerts for
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates alert events for a patient. An alert may be triggered or resolved
     * based on probabilities.
     *
     * @param patientId       the ID of the patient
     * @param outputStrategy  strategy to output generated alert data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                // 90% chance to resolve the alert
                if (randomGenerator.nextDouble() < 0.9) {
                    alertStates[patientId] = false;
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double lambda = 0.1; // Average alert rate

                // Probability calculation using Poisson distribution formula
                double p = -Math.expm1(-lambda);
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
