package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SettingsPage {

    private String loggedInUsername;

    public SettingsPage(JFrame parentFrame, String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;

        // Create the frame for the settings page
        JFrame settingsFrame = new JFrame("Settings");
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setSize(400, 300);
        settingsFrame.setLayout(new GridLayout(5, 2, 10, 10)); // 5 rows, 2 columns layout

        // Create labels and text fields for the passwords
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        JPasswordField oldPasswordField = new JPasswordField();

        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField();

        // Create buttons for submit and back
        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");

        // Add components to the frame
        settingsFrame.add(oldPasswordLabel);
        settingsFrame.add(oldPasswordField);

        settingsFrame.add(newPasswordLabel);
        settingsFrame.add(newPasswordField);

        settingsFrame.add(confirmPasswordLabel);
        settingsFrame.add(confirmPasswordField);

        settingsFrame.add(submitButton);
        settingsFrame.add(backButton);

        // Action listener for the Submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the entered passwords
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Check if all fields are filled
                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(settingsFrame, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate old password and update password
                Employee employee = Employee.getEmployeeByUsername(loggedInUsername);
                if (employee != null) {
                    // Check if the old password matches
                    if (employee.getPassword().equals(oldPassword)) {
                        // Check if new password and confirm password match
                        if (newPassword.equals(confirmPassword)) {
                            // Update the password (you can save it back to the file here)
                            employee.setPassword(newPassword);
                            Employee.removeEmployeeByUsername(loggedInUsername);
                            Employee.addEmployeeToFile(employee); // Save the updated employee

                            JOptionPane.showMessageDialog(settingsFrame, "Password updated successfully!");
                            settingsFrame.dispose();
                            parentFrame.setVisible(true); // Show the Employee Portal again
                        } else {
                            JOptionPane.showMessageDialog(settingsFrame, "New Password and Confirm Password do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(settingsFrame, "Old Password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(settingsFrame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.dispose(); // Close the settings page
                parentFrame.setVisible(true); // Show the Employee Portal again
            }
        });

        // Set frame visibility
        settingsFrame.setLocationRelativeTo(null); // Center the frame on the screen
        settingsFrame.setVisible(true);
        parentFrame.setVisible(false); // Hide the Employee Portal when Settings page is open
    }
}
