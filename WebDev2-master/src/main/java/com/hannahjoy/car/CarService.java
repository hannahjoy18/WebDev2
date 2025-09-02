package com.hannahjoy.car;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    private final List<Car> carList = new ArrayList<>();
    private final String CSV_FILE = "cars.csv";

    // Constructor loads data at startup
    public CarService() {
        loadCarsFromFile();
    }

    public synchronized List<Car> findAll() {
        return new ArrayList<>(carList);
    }

    public synchronized Car getCar(int id) {
        return carList.stream()
                .filter(c -> c.getCarId() == id)
                .findFirst()
                .orElse(null);
    }

    public synchronized void addCar(Car car) {
        // ✅ Car already assigns its own ID in the constructor
        // so don’t override here, just add it
        carList.add(car);
        saveCarsToFile();
    }

    public synchronized void updateCar(int id, Car updatedCar) {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getCarId() == id) {
                updatedCar.setCarId(id);  // Ensure ID stays the same
                carList.set(i, updatedCar);
                saveCarsToFile();
                break;
            }
        }
    }

    public synchronized void deleteCar(int id) {
        carList.removeIf(c -> c.getCarId() == id);
        saveCarsToFile();
    }

    public synchronized int getLastId() {
        return carList.isEmpty() ? 0 : carList.get(carList.size() - 1).getCarId();
    }

    // Save car list to CSV file
    private synchronized void saveCarsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Car c : carList) {
                writer.write(c.getCarId() + ","
                        + escapeCsv(c.getLicensePlateNumber()) + ","
                        + escapeCsv(c.getMake()) + ","
                        + escapeCsv(c.getModel()) + ","
                        + c.getYear() + ","
                        + escapeCsv(c.getColor()) + ","
                        + escapeCsv(c.getBodyType()) + ","
                        + escapeCsv(c.getEngineType()) + ","
                        + escapeCsv(c.getTransmission()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load cars from CSV
    public synchronized void loadCarsFromFile() {
        carList.clear();
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.out.println("CSV file not found, starting with empty list.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = splitCsvLine(line);
                if (parts.length < 9) continue; // skip malformed lines

                int id = Integer.parseInt(parts[0].trim());
                String licensePlate = parts[1].trim();
                String make = parts[2].trim();
                String model = parts[3].trim();
                int year = Integer.parseInt(parts[4].trim());
                String color = parts[5].trim();
                String bodyType = parts[6].trim();
                String engineType = parts[7].trim();
                String transmission = parts[8].trim();

                Car car = new Car();
                car.setCarId(id);
                car.setLicensePlateNumber(unescapeCsv(licensePlate));
                car.setMake(unescapeCsv(make));
                car.setModel(unescapeCsv(model));
                car.setYear(year);
                car.setColor(unescapeCsv(color));
                car.setBodyType(unescapeCsv(bodyType));
                car.setEngineType(unescapeCsv(engineType));
                car.setTransmission(unescapeCsv(transmission));

                carList.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Escape CSV values containing comma or quotes
    private String escapeCsv(String input) {
        if (input == null) return "";
        if (input.contains(",") || input.contains("\"")) {
            input = input.replace("\"", "\"\"");
            return "\"" + input + "\"";
        }
        return input;
    }

    // Unescape CSV quoted values
    private String unescapeCsv(String input) {
        if (input.startsWith("\"") && input.endsWith("\"")) {
            input = input.substring(1, input.length() - 1);
            input = input.replace("\"\"", "\"");
        }
        return input;
    }

    // Split CSV line into tokens handling quoted commas
    private String[] splitCsvLine(String line) {
        List<String> tokens = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                tokens.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        tokens.add(sb.toString());
        return tokens.toArray(new String[0]);
    }

    // ✅ Fixed: return actual list of cars instead of null
    public synchronized List<Car> getAllCars() {
        return new ArrayList<>(carList);
    }
}
