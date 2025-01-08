package App;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;

public class ShowStatusPage {
    private JFrame statusFrame;
    private JTable statusTable;
    private JScrollPane scrollPane;

    public ShowStatusPage(JFrame parentFrame) {
        initialize(parentFrame);
    }

    private void initialize(JFrame parentFrame) {
        // Create the frame for the Show Status Page
        statusFrame = new JFrame("Parking Status");
        statusFrame.setSize(900, 600);
        statusFrame.setLocationRelativeTo(null); // Center the window
        statusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       
        // Set the icon
        ImageIcon icon = new ImageIcon(HomePage.class.getResource("/img/Icon.jpg"));
        statusFrame.setIconImage(icon.getImage());
        // Column names for the table
        String[] columnNames = { "Name", "Phone Number", "Plate Number", "Parking Spot", "Check In Time" };

        // Get data from the CSV file
        Object[][] data = readDataFromCSV();

        // Check if there's no data
        if (data.length == 0) {
            JOptionPane.showMessageDialog(statusFrame, "No parking data available.", "No Data", JOptionPane.INFORMATION_MESSAGE);
        }

        // Create the table with the data and column names
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        statusTable = new JTable(tableModel);

        // Add a scroll pane to make the table scrollable
        scrollPane = new JScrollPane(statusTable);
        statusFrame.add(scrollPane, BorderLayout.CENTER);

        // Add "Back" button and spot info to the bottom of the frame
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Add the back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            statusFrame.dispose();
            parentFrame.setVisible(true);
        });
        bottomPanel.add(backButton, BorderLayout.WEST);

        // Add labels for free and taken spots
        JPanel spotInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        int[] spotCounts = calculateSpotCounts(data);
        JLabel freeSpotsLabel = new JLabel("Total Free Spots: " + spotCounts[0]);
        JLabel takenSpotsLabel = new JLabel("Total Taken Spots: " + spotCounts[1]);
        spotInfoPanel.add(freeSpotsLabel);
        spotInfoPanel.add(takenSpotsLabel);

        bottomPanel.add(spotInfoPanel, BorderLayout.EAST);

        // Add the bottom panel to the frame
        statusFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Make the frame visible
        statusFrame.setVisible(true);
    }

    // Method to read data from the CSV file
    private Object[][] readDataFromCSV() {
        String fileName = "parking_data.csv";
        File file = new File(fileName);

        // Check if the file exists
        if (!file.exists()) {
            return new Object[0][0]; // Return an empty array if the file doesn't exist
        }

        int rowCount = 0;

        // First pass to count the number of rows in the file (skip header)
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // Skip the first line (header)
            while (br.readLine() != null) {
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(statusFrame, "Error reading data from file.");
        }

        // Initialize the 2D array for the table data
        Object[][] data = new Object[rowCount][5];

        // Second pass to populate the 2D array with data from the CSV (skip header)
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // Skip the header line
            String line;
            int rowIndex = 0;
            while ((line = br.readLine()) != null) {
                // Split the line by commas and populate the array
                String[] columns = line.split(",");
                data[rowIndex][0] = columns[0]; // Name
                data[rowIndex][1] = columns[1]; // Phone Number
                data[rowIndex][2] = columns[2]; // Plate Number
                data[rowIndex][3] = columns[3]; // Parking Spot
                data[rowIndex][4] = columns[4]; // Check In Time
                rowIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(statusFrame, "Error reading data from file.");
        }

        // Return the populated 2D array
        return data;
    }

    // Method to calculate the total free and taken spots
    private int[] calculateSpotCounts(Object[][] data) {
        int totalSpots = 50; // Assume total parking spots are 50
        int takenSpots = data.length; // Number of rows in data represents taken spots
        int freeSpots = totalSpots - takenSpots;

        // Return an array containing free spots and taken spots
        return new int[] { freeSpots, takenSpots };
    }
}
