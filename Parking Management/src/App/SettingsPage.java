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
        settingsFrame.setSize(500, 400);
        settingsFrame.setLayout(new BorderLayout());
        
     // Set the icon
        ImageIcon icon = new ImageIcon(HomePage.class.getResource("/img/Icon.jpg"));
        settingsFrame.setIconImage(icon.getImage());
        
     // Create a label for the title
        JLabel titleLabel = new JLabel("Change Password", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        settingsFrame.add(titleLabel, BorderLayout.NORTH);
        
        // Create a panel for the form inputs
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/img/PortalBG.jpg"));
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        formPanel.setLayout(new GridLayout(5, 2, 10, 10)); // 5 rows, 2 columns layout


        // Create labels and text fields for the passwords
        JLabel currentPasswordLabel = new JLabel("Current Password");
        currentPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JPasswordField currentPasswordField = new JPasswordField();
        customizePasswordField(currentPasswordField);
       
        JLabel newPasswordLabel = new JLabel("New Password");
        newPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JPasswordField newPasswordField = new JPasswordField();
        customizePasswordField(newPasswordField);
        

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JPasswordField confirmPasswordField = new JPasswordField();
        customizePasswordField(confirmPasswordField);
        
     // Create a checkbox to toggle password visibility
        JCheckBox confirmPasswordFieldCheckBox = new JCheckBox("Show Password");
        confirmPasswordFieldCheckBox.setFont(new Font("Arial", Font.PLAIN, 15));
        confirmPasswordFieldCheckBox.setOpaque(false);
        confirmPasswordFieldCheckBox.addActionListener(e -> {
            confirmPasswordField.setEchoChar(confirmPasswordFieldCheckBox.isSelected() ? '\u0000' : '\u2022');
            newPasswordField.setEchoChar(confirmPasswordFieldCheckBox.isSelected() ? '\u0000' : '\u2022');
            currentPasswordField.setEchoChar(confirmPasswordFieldCheckBox.isSelected() ? '\u0000' : '\u2022');
        });

        // Add components to the form panel
        formPanel.add(currentPasswordLabel);
        formPanel.add(currentPasswordField);
        formPanel.add(newPasswordLabel);
        formPanel.add(newPasswordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);
        formPanel.add(confirmPasswordFieldCheckBox);
        
     // Add the form panel to the center of the frame
        settingsFrame.add(formPanel, BorderLayout.CENTER);
        
     // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        // Create buttons for submit and back
        JButton submitButton = createGradientButton("Submit");
        JButton backButton = createGradientButton("Back");
        
     // Add buttons to the button panel
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        
     // Add the button panel to the bottom of the frame
        settingsFrame.add(buttonPanel, BorderLayout.SOUTH);

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
    // Helper method to customize password field
    private void customizePasswordField(JPasswordField passwordField) {
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        passwordField.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
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
