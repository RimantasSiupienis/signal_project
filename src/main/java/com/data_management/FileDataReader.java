package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader implements DataReader {

    private final String filePath;

    public FileDataReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Expected format: patientId,measurementValue,recordType,timestamp
                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                int patientId = Integer.parseInt(parts[0].trim());
                double measurementValue = Double.parseDouble(parts[1].trim());
                String recordType = parts[2].trim();
                long timestamp = Long.parseLong(parts[3].trim());

                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            }
        }
    }
}
