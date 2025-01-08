package App;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckOut {
    public JFrame checkoutFrame;
    private JTextField plateNumberField = new JTextField(25); // Set a wider width for the text field

    public CheckOut(JFrame parentFrame) {
        // Create the JFrame for the CheckOut page
        checkoutFrame = new JFrame("Check Out");
        checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkoutFrame.setSize(420, 200);
        checkoutFrame.setLayout(new BorderLayout());
     // Set the icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Icon.jpg"));
        checkoutFrame.setIconImage(icon.getImage());
     // Create a custom JPanel for the background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image and scale it to fit the panel
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/img/PortalBG.jpg")); // Replace with your image path
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // Draw the image scaled to panel size
            }
        };
        
        backgroundPanel.setLayout(new BorderLayout()); // Set layout for background panel
        
     // Create a label for the title
        JLabel titleLabel = new JLabel("CheckOut Car", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
     // Add the title label to the background panel
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);
        
        
        // Create a panel for the form input
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // FlowLayout to place the label and text field next to each other
        formPanel.setOpaque(false);
        
        // Create label and text field for the username
        JLabel plateNumberLabel = new JLabel("CheckOut");
        plateNumberLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Increase label font size

        customizeTextField(plateNumberField);
        plateNumberField.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font size for the text field

        // Add components to the form panel
        formPanel.add(plateNumberLabel);
        formPanel.add(plateNumberField);
        
     // Add the form panel to the center of the background panel
        backgroundPanel.add(formPanel, BorderLayout.CENTER);
        
     // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());
        
        // Create Delete button, Add button and Back button
        JButton checkoutButton = createGradientButton("CheckOut");
        JButton backButton = createGradientButton("Back");
        
        // Add buttons to the button panel
        buttonPanel.add(checkoutButton);
        buttonPanel.add(backButton);
        
        // Add the button panel to the bottom of the background panel
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        checkoutButton.addActionListener(e -> processCheckOut());
        backButton.addActionListener(e -> {
            checkoutFrame.dispose();
            parentFrame.setVisible(true);  // Show the parent frame again
        });
        
     // Set frame visibility
        checkoutFrame.setLocationRelativeTo(null); // Center the frame on the screen
        checkoutFrame.setContentPane(backgroundPanel); // Set the background panel as the content pane
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
 // Helper method to customize text fields
    private void customizeTextField(JTextField textField) {
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        textField.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
    }
    // Helper method to create gradient and rounded buttons
    private JButton createGradientButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                // Create a gradient paint
                GradientPaint gradientPaint = new GradientPaint(0, 0, new Color(72, 209, 204), width, height, new Color(25, 25, 112));
                g2d.setPaint(gradientPaint);
                g2d.fillRoundRect(0, 0, width, height, 30, 30);

                // Draw the button text
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                g2d.drawString(getText(), (width - textWidth) / 2, (height + textHeight) / 2 - 2);

                // Avoid default painting
                setContentAreaFilled(false);
                setFocusPainted(false);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.WHITE);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };

        button.setPreferredSize(new Dimension(200, 50));
        button.setOpaque(false); // Ensures the button background remains transparent
        button.setFocusPainted(false); // Removes focus highlight
        button.setBorderPainted(false); // Removes default border
        return button;
    }
}
