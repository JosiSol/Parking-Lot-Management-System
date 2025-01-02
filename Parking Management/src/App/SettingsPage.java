package App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SettingsPage {

    private String loggedInUsername;

    public SettingsPage(JFrame parentFrame, String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;

        // Create the frame for the settings page
        JFrame settingsFrame = new JFrame("Settings");
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setSize(600, 300);
        settingsFrame.setLayout(new GridLayout(5, 2, 10, 10)); // 5 rows, 2 columns layout

        // Create labels and text fields for the passwords
        JLabel currentPasswordLabel = new JLabel("Current Password:");
        JPasswordField currentPasswordField = new JPasswordField();
        JCheckBox currentPasswordFieldCheckBox = new JCheckBox("Show Password");
        currentPasswordFieldCheckBox.addActionListener(e -> {
            currentPasswordField.setEchoChar(currentPasswordFieldCheckBox.isSelected() ? '\u0000' : '\u2022');
        });

        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField();
        JCheckBox newPasswordFieldCheckBox = new JCheckBox("Show Password");
        newPasswordFieldCheckBox.addActionListener(e -> {
            newPasswordField.setEchoChar(newPasswordFieldCheckBox.isSelected() ? '\u0000' : '\u2022');
        });

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField();
        JCheckBox confirmPasswordFieldCheckBox = new JCheckBox("Show Password");
        confirmPasswordFieldCheckBox.addActionListener(e -> {
            confirmPasswordField.setEchoChar(confirmPasswordFieldCheckBox.isSelected() ? '\u0000' : '\u2022');
        });

        // Create buttons for submit and back
        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");

        // Add components to the frame
        settingsFrame.add(currentPasswordLabel);
        settingsFrame.add(currentPasswordField);
        settingsFrame.add(currentPasswordFieldCheckBox);

        settingsFrame.add(newPasswordLabel);
        settingsFrame.add(newPasswordField);
        settingsFrame.add(newPasswordFieldCheckBox);

        settingsFrame.add(confirmPasswordLabel);
        settingsFrame.add(confirmPasswordField);
        settingsFrame.add(confirmPasswordFieldCheckBox);

        settingsFrame.add(submitButton);
        settingsFrame.add(backButton);

        // Action listener for the Submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the entered passwords
                String currentPassword = new String(currentPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Check if all fields are filled
                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(settingsFrame, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (!isValidPassword(currentPassword)) {
                    JOptionPane.showMessageDialog(settingsFrame, "Incorrect Password. Try Again", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (!isValidPassword(newPassword)) {
                    JOptionPane.showMessageDialog(settingsFrame, "Password must contain at least 8 characters with a mix of uppercase, lowercase, and a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (!isValidPassword(confirmPassword)) {
                    JOptionPane.showMessageDialog(settingsFrame, "Password must contain at least 8 characters with a mix of uppercase, lowercase, and a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate current password and update password
                Employee employee = Employee.getEmployeeByUsername(loggedInUsername);
                if (employee != null) {
                    // Check if the current password matches
                    if (employee.getPassword().equals(currentPassword)) {
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
                        JOptionPane.showMessageDialog(settingsFrame, "Current Password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(settingsFrame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            private boolean isValidPassword(String password) {
                boolean hasUppercase = !password.equals(password.toLowerCase());
                boolean hasLowercase = !password.equals(password.toUpperCase());
                boolean hasNumber = password.matches(".*\\d.*");
                boolean hasCorrectLength = password.length() >= 8;
                return hasUppercase && hasLowercase && hasNumber && hasCorrectLength;
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
