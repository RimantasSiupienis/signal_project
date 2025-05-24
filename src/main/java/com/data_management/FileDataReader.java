package com.data_management;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

/**
 * Reads patient data from files in a directory and stores it using DataStorage.
 */
public class FileDataReader implements DataReader {

    private final String directoryPath;

    public FileDataReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile)
                 .forEach(file -> {
                     try (BufferedReader reader = Files.newBufferedReader(file)) {
                         String line;
                         while ((line = reader.readLine()) != null) {
                             String[] parts = line.split(",");
                             if (parts.length != 4) continue;

                             int patientId = Integer.parseInt(parts[0].trim());
                             long timestamp = Long.parseLong(parts[1].trim());
                             String recordType = parts[2].trim();
                             double measurementValue = Double.parseDouble(parts[3].trim());

                             dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                         }
                     } catch (IOException | NumberFormatException e) {
                         e.printStackTrace(); // For now, print and skip bad lines
                     }
                 });
        }
    }
}
