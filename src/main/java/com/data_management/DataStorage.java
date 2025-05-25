package com.data_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alerts.AlertGenerator;

/**
 * Manages storage and retrieval of patient data within a healthcare monitoring
 * system. Implements Singleton pattern.
 */
public class DataStorage {
    private static DataStorage instance; // Singleton instance
    private static Map<Integer, Patient> patientMap;
    private static final int DUPLICATE_CHECK_TIME_RANGE = 500;

    private DataStorage() {
        this.patientMap = new HashMap<>();
    }

    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public static synchronized void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }else if (!verifyRecord(patient, measurementValue,recordType,timestamp))

        patient.addRecord(measurementValue, recordType, timestamp);
    }

    public static synchronized boolean verifyRecord(Patient patient, double measurementValue, String recordType, long timestamp)
    {
        List<PatientRecord> records = patient.getRecords(timestamp - DUPLICATE_CHECK_TIME_RANGE /2
        , timestamp + DUPLICATE_CHECK_TIME_RANGE / 2);

        for(PatientRecord p : records)
        {
            if(p.getRecordType().equals(recordType) && p.getMeasurementValue() == measurementValue) return false;

        }
        return true;

    }
   

    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            if (startTime == 0 && endTime == 0) {
                return new ArrayList<>(patient.getRecords(0, Long.MAX_VALUE));
            }
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>();
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    public static void main(String[] args) {
        DataStorage storage = DataStorage.getInstance();

        List<PatientRecord> records = storage.getRecords(1, 1700000000000L, 1800000000000L);
        for (PatientRecord record : records) {
            System.out.println("Record for Patient ID: " + record.getPatientId() +
                    ", Type: " + record.getRecordType() +
                    ", Data: " + record.getMeasurementValue() +
                    ", Timestamp: " + record.getTimestamp());
        }

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        for (Patient patient : storage.getAllPatients()) {
            alertGenerator.evaluateData(patient);
        }
    }
}

