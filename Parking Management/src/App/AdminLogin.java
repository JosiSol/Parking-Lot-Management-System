package App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdminLogin {

    public AdminLogin(JFrame parentFrame) {
        // Create a new frame for the Admin Login page
        JFrame adminFrame = new JFrame("Admin Login");
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setSize(400, 300);
        adminFrame.setLayout(new BorderLayout());

        // Create a panel for the login form
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10)); // GridLayout for fields and labels

        // Create username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        // Create password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        // Create a checkbox to toggle password visibility
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(e -> {
            passwordField.setEchoChar(showPassword.isSelected() ? '\u0000' : '\u2022');
        });

        // Add components to the login panel
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(showPassword);

        // Add login panel to the center of the frame
        adminFrame.add(loginPanel, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create login and back buttons
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        // Add buttons to the button panel
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        // Add button panel to the bottom of the frame
        adminFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for Login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // For demonstration, we'll assume admin username and password are fixed
                if (username.equals("123") && password.equals("123")) {
                    JOptionPane.showMessageDialog(adminFrame, "Login Successful!");
                    new AdminPortal(adminFrame); // Open the Admin Portal
                    adminFrame.dispose(); // Close the Admin Login frame
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose(); // Close the Admin Login frame
                parentFrame.setVisible(true); // Show the Home Page again
            }
        });

        // Set frame visibility
        adminFrame.setLocationRelativeTo(null); // Center the frame on the screen
        adminFrame.setVisible(true);
        parentFrame.setVisible(false); // Hide the Home Page
    }
}
