package App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EmployeePortal {

    private String loggedInUsername;  // Store the logged-in username

    public EmployeePortal(JFrame parentFrame, String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;

        // Create the frame for the employee portal
        JFrame employeePortalFrame = new JFrame("Employee Portal");
        employeePortalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        employeePortalFrame.setSize(400, 300);
        employeePortalFrame.setLayout(new BorderLayout());

        // Create a label for the title
        JLabel titleLabel = new JLabel("Welcome to the Employee Portal", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        employeePortalFrame.add(titleLabel, BorderLayout.NORTH);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 buttons in a single column layout

        // Create buttons for the portal
        JButton checkInButton = new JButton("Check In");
        JButton checkOutButton = new JButton("Check Out");
        JButton statusButton = new JButton("Status");
        JButton settingsButton = new JButton("Change Password");
        JButton backButton = new JButton("Logout");

        // Add buttons to the panel
        buttonPanel.add(checkInButton);
        buttonPanel.add(checkOutButton);
        buttonPanel.add(statusButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(backButton);

        // Add the button panel to the center of the frame
        employeePortalFrame.add(buttonPanel, BorderLayout.CENTER);

        // Action listener for the Check In button
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open CheckIn page when Check In button is clicked
                new CheckIn(employeePortalFrame);
            }
        });

        // Action listener for Check Out button
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Check Out page
                new CheckOut(employeePortalFrame);
                employeePortalFrame.setVisible(false); // Hide the Employee Portal
            }
        });

        // Action listener for Status button
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ShowStatusPage when Status button is clicked
                new ShowStatusPage(employeePortalFrame);
            }
        });

        // Action listener for Settings button
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Settings page when Settings button is clicked
                new SettingsPage(employeePortalFrame, loggedInUsername); // Pass loggedInUsername
            }
        });

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePortalFrame.dispose(); // Close the Employee Portal page
                parentFrame.setVisible(true); // Show the HomePage again (or Employee Login page)
            }
        });

        // Set frame visibility
        employeePortalFrame.setLocationRelativeTo(null); // Center the frame on the screen
        employeePortalFrame.setVisible(true);
        parentFrame.setVisible(false); // Hide the HomePage or previous screen when Employee Portal is open
    }
}