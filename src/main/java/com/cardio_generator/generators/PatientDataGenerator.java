package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface for generating patient data.
 * 
 * <p>Interface are responsible for generating specific types of
 * data for patients,like ECG readings, blood pressure measurements,
 * etc. The generated data is output using the specified strategy.
 */
public interface PatientDataGenerator {
    
    /**
     * Generates health data for a specific patient and outputs it using the given strategy.
     * 
     * <p>This method is responsible for generating data for a specific patient depending on the
     * data type being generated. The output strategy determines where and how
     * the data will be recorded or sent.
     *
     * @param patientId ID for who to generate data
     * @param outputStrategy The output strategy to use for recording the data
     * 
     * @throws IllegalArgumentException if patientId is invalid
     * @throws NullPointerException if strategy is null
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}