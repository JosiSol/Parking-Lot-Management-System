package App;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShowReportPage {
    private JFrame reportFrame;
    private JTable reportTable;
    private JScrollPane scrollPane;
    private JLabel totalBillLabel;

    public ShowReportPage(JFrame parentFrame) {
       
        // Create JFrame for showing the report
        reportFrame = new JFrame("Parking Report");
        reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reportFrame.setSize(900, 600);
        reportFrame.setLocationRelativeTo(null);
    
        ImageIcon icon = new ImageIcon(HomePage.class.getResource("/img/Icon.jpg"));
        reportFrame.setIconImage(icon.getImage());

        // Create a panel to hold the table
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Column names for the table including Check Out Time and Bill
        String[] columnNames = {"Name", "Phone Number", "Plate Number", "Parking Spot", "Check In Time", "Check Out Time", "Bill"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Create the JTable with the model
        reportTable = new JTable(tableModel);
        scrollPane = new JScrollPane(reportTable);
        
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create "Back" button to go back to the previous page
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            reportFrame.dispose();
            parentFrame.setVisible(true);
        });

        // Create total bill label at the bottom
        totalBillLabel = new JLabel("Total: $0", JLabel.CENTER);
        totalBillLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Create a panel for the buttons and total bill
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(totalBillLabel, BorderLayout.CENTER);
        bottomPanel.add(backButton, BorderLayout.EAST);

        reportFrame.add(panel, BorderLayout.CENTER);
        reportFrame.add(bottomPanel, BorderLayout.SOUTH);
        reportFrame.setVisible(true);

        // Load the data from the report.csv and populate the table
        loadReportData(tableModel);
    }

    // Method to load data from the report.csv file and populate the table
    private void loadReportData(DefaultTableModel tableModel) {
        File reportFile = new File("report.csv");
        double totalBill = 0.0;

        // Check if the report.csv file exists
        if (!reportFile.exists()) {
            JOptionPane.showMessageDialog(reportFrame, "No report data found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(reportFile))) {
            String line;
            // Skip the header line
            br.readLine();

            // Read each line of the file and add data to the table model
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Ensure that the line has enough columns (7 fields: 6 data + 1 for Bill)
                if (data.length == 7) {
                    tableModel.addRow(data);  // Add the data to the table model

                    // Add the bill to the total (the bill is in the last column)
                    try {
                        double bill = Double.parseDouble(data[6]);  // Bill is in the 7th column (index 6)
                        totalBill += bill;
                    } catch (NumberFormatException e) {
                        // Handle the case where the bill data is not a valid number
                        e.printStackTrace();
                    }
                }
            }

            // Update the total bill label
            totalBillLabel.setText("Total: $" + String.format("%.2f", totalBill));

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(reportFrame, "Error reading the report file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
