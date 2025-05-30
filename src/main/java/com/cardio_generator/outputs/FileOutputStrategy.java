package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Outputs patient data to individual files based on labels.
 * Each label maps to its own file inside the directory.
 */
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory;

    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    /**
     * Constructor that sets the directory where files will be written.
     *
     * @param baseDirectory the folder where output files will be stored
     */
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Writes patient data to a file corresponding to the data label.
     * Each label has its own file.
     *
     * @param patientId  ID of the patient
     * @param timestamp  timestamp when data was generated
     * @param label      type of data
     * @param data       the actual data value to write
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }

        String filePath = fileMap.computeIfAbsent(label,
                k -> Paths.get(baseDirectory, label + ".txt").toString());

        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(
                Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n",
                    patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
