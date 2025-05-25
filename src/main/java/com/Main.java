package com;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataStorage;

/**
 * Main class to run the health data simulator or data storage management.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("DataStorage")) {
            DataStorage.main(new String[]{});
        } else {
            HealthDataSimulator.getInstance().run(new String[]{});
        }
    }
}
