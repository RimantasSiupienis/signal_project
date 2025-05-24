package com.data_management;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("DataStorage")) {
            DataStorage.main(new String[]{});
        } else {
            System.out.println("Defaulting to HealthDataSimulator... (not implemented here)");
            // HealthDataSimulator.main(new String[]{});
        }
    }
}
