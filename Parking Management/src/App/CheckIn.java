package App;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class CheckIn {
    public JFrame checkInFrame;
    private JTextField customerNameField, phoneNumberField, plateNumberField;
    private JButton checkInButton, backButton;
    private JFrame parentFrame;

    public CheckIn(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initialize();
    }

    private void initialize() {
        // Ensure the parking data file exists before proceeding
        ensureFileExists("parking_data.csv");

        checkInFrame = new JFrame("Check In");
        checkInFrame.setSize(500, 400);
        checkInFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkInFrame.setLocationRelativeTo(null);
     // Set the icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Icon.jpg"));
        checkInFrame.setIconImage(icon.getImage());
        
     // Create a label for the title
        JLabel titleLabel = new JLabel("Check In New Car", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        checkInFrame.add(titleLabel, BorderLayout.NORTH);
        
        // Create a panel for the form inputs
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/img/PortalBG.jpg"));
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns layout
        
     // Create labels and text fields for car details
        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        customerNameField = new JTextField();
        customizeTextField(customerNameField);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        phoneNumberField = new JTextField();
        customizeTextField(phoneNumberField);

        JLabel plateNumberLabel = new JLabel("Plate Number:");
        plateNumberLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        plateNumberField = new JTextField();
        customizeTextField(plateNumberField);

        checkInButton = createGradientButton("Check In");
        backButton = createGradientButton("Back");

        panel.add(customerNameLabel);
        panel.add(customerNameField);
        panel.add(phoneNumberLabel);
        panel.add(phoneNumberField);
        panel.add(plateNumberLabel);
        panel.add(plateNumberField);
        
     // Add the form panel to the center of the frame
        checkInFrame.add(panel, BorderLayout.CENTER);
       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
     // Add buttons to the button panel
        buttonPanel.add(checkInButton);
        buttonPanel.add(backButton);
        
     // Add the button panel to the bottom of the frame
        checkInFrame.add(buttonPanel, BorderLayout.SOUTH);

        
        checkInFrame.setVisible(true);

        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkInFrame.dispose();
                parentFrame.setVisible(true);
            }
        });

        // Check-in button action
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = customerNameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String plateNumber = plateNumberField.getText();

                // Validate required fields
                if (customerName.isEmpty() || phoneNumber.isEmpty() || plateNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(checkInFrame, "All fields are required!");
                    return;  // Prevent check-in if any field is empty
                }

                // Check for duplicate plate number
                if (isPlateNumberDuplicate(plateNumber)) {
                    JOptionPane.showMessageDialog(checkInFrame, "This plate number is already in use. Please enter a unique plate number.");
                    return;  // Prevent check-in if plate number is a duplicate
                }

                // Call the method to save data to CSV
                saveToCSV(customerName, phoneNumber, plateNumber);

                // Optionally, show a confirmation message
                JOptionPane.showMessageDialog(checkInFrame, "Car checked in successfully!");

                // Clear fields after check-in
                customerNameField.setText("");
                phoneNumberField.setText("");
                plateNumberField.setText("");
            }
        });
    }

    // Method to ensure the parking data file exists
    private void ensureFileExists(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                // Write the header row to the new file
                bw.write("Name,Phone Number,Plate Number,Parking Spot,Check In Time");
                bw.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(checkInFrame, "Error creating the parking data file.");
            }
        }
    }

    // Method to save check-in data to a CSV file
    private void saveToCSV(String customerName, String phoneNumber, String plateNumber) {
        String fileName = "parking_data.csv";
        String parkingSpot = assignParkingSpot();
        String checkInTime = getCurrentTime();

        try {
            // Open file in append mode
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);

            // Prepare the data to write
            String data = customerName + "," + phoneNumber + "," + plateNumber + "," + parkingSpot + "," + checkInTime + "\n";

            // Write data to the file
            bw.write(data);

            // Close the BufferedWriter
            bw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(checkInFrame, "Error saving data to file.");
        }
    }

    // Method to assign a parking spot (P01 - P50) ensuring no redundancy
    private String assignParkingSpot() {
        Set<String> occupiedSpots = getOccupiedSpots();  // Get the list of already occupied spots
        for (int i = 1; i <= 50; i++) {
            String spot = String.format("P%02d", i);  // Format as P01, P02, ..., P50
            if (!occupiedSpots.contains(spot)) {
                return spot;  // Return the first available spot
            }
        }
        return null;  // This should never happen as we have only 50 spots, and some should be free
    }

    // Method to get a list of occupied parking spots from the CSV file
    private Set<String> getOccupiedSpots() {
        Set<String> occupiedSpots = new HashSet<>();
        String fileName = "parking_data.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstLine = true;

            // Read the parking data CSV line by line
            while ((line = br.readLine()) != null) {
                // Skip header line
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] columns = line.split(",");
                String parkingSpot = columns[3];  // Parking spot is at index 3
                occupiedSpots.add(parkingSpot);  // Add occupied spot to the set
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(checkInFrame, "Error reading parking data.");
        }

        return occupiedSpots;
    }

    // Method to check if the plate number is a duplicate
    private boolean isPlateNumberDuplicate(String plateNumber) {
        String fileName = "parking_data.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstLine = true;

            // Read the parking data CSV line by line
            while ((line = br.readLine()) != null) {
                // Skip header line
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] columns = line.split(",");
                String storedPlateNumber = columns[2];  // Plate number is at index 2
                if (storedPlateNumber.equals(plateNumber)) {
                    return true;  // Plate number is a duplicate
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(checkInFrame, "Error reading parking data.");
        }

        return false;  // No duplicate found
    }

    // Method to get the current system time as a formatted string
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
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
