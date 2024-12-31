package App;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckOut {
    private JFrame checkoutFrame;
    private JTextField plateNumberField;
    private JButton checkoutButton;
    private JButton backButton;

    public CheckOut(JFrame parentFrame) {
        // Create the JFrame for the CheckOut page
        checkoutFrame = new JFrame("Check Out");
        checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkoutFrame.setSize(400, 200);
        checkoutFrame.setLocationRelativeTo(null);

        // Create JPanel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Create label and input field for Plate Number
        JLabel plateNumberLabel = new JLabel("Enter Plate Number:");
        plateNumberField = new JTextField(15);
        panel.add(plateNumberLabel);
        panel.add(plateNumberField);

        // Create "Check Out" button with ActionListener
        checkoutButton = new JButton("Check Out");
        checkoutButton.addActionListener(e -> processCheckOut());

        // Create "Back" button to return to the previous page
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            checkoutFrame.dispose();
            parentFrame.setVisible(true);  // Show the parent frame again
        });

        // Add buttons to the panel
        panel.add(checkoutButton);
        panel.add(backButton);

        // Add the panel to the JFrame and make it visible
        checkoutFrame.add(panel);
        checkoutFrame.setVisible(true);
    }

    // Method to process the check-out and remove the car
    private void processCheckOut() {
        String plateNumber = plateNumberField.getText().trim();

        // Check if plate number is entered
        if (plateNumber.isEmpty()) {
            JOptionPane.showMessageDialog(checkoutFrame, "Please enter a plate number.");
            return;
        }

        // Files for parking data and report
        File parkingFile = new File("parking_data.csv");
        File reportFile = new File("report.csv");

        // ArrayList to hold the data objects
        ArrayList<Car> cars = new ArrayList<>();
        boolean carFound = false;
        String checkOutTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String checkInTime = null;  // To hold the check-in time for displaying

        try (
            BufferedReader br = new BufferedReader(new FileReader(parkingFile))
        ) {
            String line;
            String header = "Name,Phone Number,Plate Number,Parking Spot,Check In Time";  // Column names
            boolean firstLine = true;  // Flag to skip the first line (header)

            // Read the parking file line by line
            while ((line = br.readLine()) != null) {
                // Skip the header line (first line of the CSV)
                if (firstLine) {
                    firstLine = false;  // After this, the first line is skipped
                    continue;  // Skip the header row
                }

                // Split each line by commas to get individual columns
                String[] columns = line.split(",");
                String name = columns[0];
                String phoneNumber = columns[1];
                String storedPlateNumber = columns[2];
                String parkingSpot = columns[3];
                String storedCheckInTime = columns[4];

                // Create Car object for each row and add to the list
                Car car = new Car(name, phoneNumber, storedPlateNumber, parkingSpot, storedCheckInTime);

                // If the plate number matches, mark as found and record the car data in the report
                if (storedPlateNumber.equals(plateNumber)) {
                    carFound = true;
                    checkInTime = storedCheckInTime;  // Store the check-in time

                    // Calculate the Bill based on check-in and check-out times
                    long bill = calculateBill(checkInTime, checkOutTime);

                    // Add the car data to the report CSV
                    writeReportFile(car, checkOutTime, bill);

                    // Display a message with check-in time, check-out time, and bill generated
                    String message = "Check In Time: " + checkInTime + "\n" +
                                     "Check Out Time: " + checkOutTime + "\n" +
                                     "Bill Generated: $" + bill;
                    JOptionPane.showMessageDialog(checkoutFrame, message);

                    continue;  // Skip writing this line back to the CSV file
                }

                // Add the car to the array list if it's not removed
                cars.add(car);
            }

            // If the car was found and removed, rewrite the parking data file
            if (carFound) {
                // Clear the existing file (or overwrite it)
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(parkingFile))) {
                    // Write the header to the file
                    bw.write(header);
                    bw.newLine();

                    // Write all remaining cars to the file
                    for (Car car : cars) {
                        bw.write(car.toCSV());
                        bw.newLine();
                    }
                }
                JOptionPane.showMessageDialog(checkoutFrame, "Car checked out and removed successfully.");
            } else {
                JOptionPane.showMessageDialog(checkoutFrame, "Car with this plate number not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(checkoutFrame, "Error processing the check-out.");
        }
    }

    // Method to calculate the parking bill
    private long calculateBill(String checkInTime, String checkOutTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date checkInDate = sdf.parse(checkInTime);
            Date checkOutDate = sdf.parse(checkOutTime);

            long durationInMillis = checkOutDate.getTime() - checkInDate.getTime();
            long durationInHours = durationInMillis / (1000 * 60 * 60);  // Convert milliseconds to hours

            // If the duration is less than an hour, the bill is 15, otherwise, 30 per hour
            if (durationInHours < 1) {
                return 15;
            } else {
                return 30 * durationInHours;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Method to write the car data to the report CSV
    private void writeReportFile(Car car, String checkOutTime, long bill) {
        File reportFile = new File("report.csv");

        try {
            boolean isNewFile = !reportFile.exists();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile, true))) {
                // Write the header if the file is newly created
                if (isNewFile) {
                    bw.write("Name,Phone Number,Plate Number,Parking Spot,Check In Time,Check Out Time,Bill");
                    bw.newLine();
                }

                // Write the car details along with checkout time and bill to the report
                bw.write(car.toCSV() + "," + checkOutTime + "," + bill);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(checkoutFrame, "Error writing to report file.");
        }
    }

    // Car class to represent the data structure
    class Car {
        private String name;
        private String phoneNumber;
        private String plateNumber;
        private String parkingSpot;
        private String checkInTime;

        public Car(String name, String phoneNumber, String plateNumber, String parkingSpot, String checkInTime) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.plateNumber = plateNumber;
            this.parkingSpot = parkingSpot;
            this.checkInTime = checkInTime;
        }

        // Convert car object to CSV format for writing back to the file
        public String toCSV() {
            return name + "," + phoneNumber + "," + plateNumber + "," + parkingSpot + "," + checkInTime;
        }
    }
}
