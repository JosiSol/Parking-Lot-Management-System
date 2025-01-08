package App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EmployeeLogin {

    public EmployeeLogin(JFrame parentFrame) {
        
        // Create a new frame for the Admin Login page
        JFrame employeeFrame = new JFrame("Employee Login");
        employeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        employeeFrame.setSize(900, 600);
        employeeFrame.setLayout(new GridLayout(1, 2));
        
        ImageIcon icon = new ImageIcon(HomePage.class.getResource("/img/Icon.jpg"));
        employeeFrame.setIconImage(icon.getImage());
        
        // Left panel for company logo
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(new Color(240, 248, 255)); // Alice blue

        // Add company logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/img/Icon.jpg"));
        Image scaledLogo = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo), JLabel.CENTER);
        leftPanel.add(logoLabel, BorderLayout.CENTER);

        // Right panel for login form with background image
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("/img/LoginBG.jpg"));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setOpaque(false); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 0, 0); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setOpaque(false);
        JTextField usernameField = new JTextField();
        usernameField.setOpaque(false);
        usernameField.setBackground(new Color(255, 255, 255, 50)); 
        usernameField.setForeground(Color.WHITE); 
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14)); 

        // Create password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setOpaque(false);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setOpaque(false);
        passwordField.setBackground(new Color(255, 255, 255, 50));
        passwordField.setForeground(Color.WHITE); 
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        showPassword.setBackground(new Color(255, 255, 255, 150));
        showPassword.setOpaque(false);
        showPassword.setForeground(Color.WHITE);
        showPassword.addActionListener(e -> {
            passwordField.setEchoChar(showPassword.isSelected() ? '\u0000' : '\u2022');
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(usernameLabel, gbc);
        gbc.gridy = 1;
        rightPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        rightPanel.add(passwordLabel, gbc);
        gbc.gridy = 3;
        rightPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        rightPanel.add(showPassword, gbc);

        // Create login and back buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setOpaque(false);
        loginButton.setBackground(new Color(255, 255, 255, 150));
        loginButton.setForeground(Color.BLACK);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setOpaque(false);
        backButton.setBackground(new Color(255, 255, 255, 150));
        backButton.setForeground(Color.BLACK);

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        rightPanel.add(buttonPanel, gbc);

     // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(employeeFrame, "Both username and password are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(employeeFrame, "Invalid password format. Password must contain at least 8 characters, including uppercase, lowercase letters, and a number.", "Password Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Validate the employee credentials
                boolean loginSuccess = Employee.validateEmployeeLogin(username, password);

                if (loginSuccess) {
                    JOptionPane.showMessageDialog(employeeFrame, "Login Successful!");
                    // Proceed to Employee Portal
                    employeeFrame.dispose();
                    new EmployeePortal(employeeFrame, username); // Pass the logged-in username here
                } else {
                    JOptionPane.showMessageDialog(employeeFrame, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private boolean isValidPassword(String password) {
            boolean hasUppercase = !password.equals(password.toLowerCase());
            boolean hasLowercase = !password.equals(password.toUpperCase());
            boolean hasSpecialChar = !password.matches("[A-Za-z0-9 ]*");
            boolean hasCorrectLength = password.length() >= 8;
            return hasUppercase && hasLowercase && hasSpecialChar && hasCorrectLength;
        }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	employeeFrame.dispose(); // Close the Admin Login frame
                parentFrame.setVisible(true); // Show the Home Page again
            }
        });

        employeeFrame.add(leftPanel);
        employeeFrame.add(rightPanel);

        // Set frame visibility
        employeeFrame.setLocationRelativeTo(null);
        employeeFrame.setVisible(true);
        parentFrame.setVisible(false); // Hide the Home Page
    }
}
