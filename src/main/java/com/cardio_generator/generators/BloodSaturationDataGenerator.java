package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates blood saturation data for a given number of patients.
 * The data fluctuates within a healthy range.
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private int[] lastSaturationValues;

    /**
     * Constructs the generator for a specified number of patients.
     * Initializes each patient with a base blood saturation value between 95% and 100%.
     *
     * @param patientCount the number of patients to generate data for
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Random value between 95 and 100
        }
    }

    /**
     * Generates a new blood saturation value for a patient and outputs it.
     * The value fluctuates slightly within range of 90% to 100%.
     *
     * @param patientId the ID of the patient
     * @param outputStrategy the strategy used to output data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation changes
            int variation = random.nextInt(3) - 1;
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within the range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;

            // Output the data
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }
}
