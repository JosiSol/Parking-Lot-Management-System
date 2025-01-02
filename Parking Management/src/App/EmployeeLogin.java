package App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.*;

class PasswordFilter extends DocumentFilter {
    private final int maxLength;
    public PasswordFilter(int maxLength) {
        this.maxLength = maxLength;
    }
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string == null){
            return;
        }
        if ((fb.getDocument().getLength() + string.length()) <= maxLength && isValidPassword(string)) {
            super.insertString(fb, offset, string, attr);
        }
}
    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
        if (string == null){
            return;
        }
        if ((fb.getDocument().getLength() + string.length() - length) <= maxLength && isValidPassword(string)) {
            super.replace(fb, offset, length, string, attr);
        }
    }
    private boolean isValidPassword(String password) {
        // This is a very basic validation, you can add more rules as per your requirement
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasSpecialChar = !password.matches("[A-Za-z0-9 ]*");
        boolean hasCorrectLength = password.length() >= 8;
        return hasUppercase && hasLowercase && hasSpecialChar && hasCorrectLength;
    }
}

public class EmployeeLogin {

    public EmployeeLogin(JFrame parentFrame) {
        // Create the frame for employee login
        JFrame employeeLoginFrame = new JFrame("Employee Login");
        employeeLoginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        employeeLoginFrame.setSize(400, 300);
        employeeLoginFrame.setLayout(new BorderLayout());

        // Create a label for the title
        JLabel titleLabel = new JLabel("Employee Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        employeeLoginFrame.add(titleLabel, BorderLayout.NORTH);

        // Create a panel for the login form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns layout

        // Create labels and text fields for username and password
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        // Create a checkbox to toggle password visibility
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(e -> {
            passwordField.setEchoChar(showPassword.isSelected()? '\u0000' : '\u2022');
        });

        // Add components to the form panel
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(showPassword);

        // Add the form panel to the center of the frame
        employeeLoginFrame.add(formPanel, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create login and back buttons
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        // Add buttons to the button panel
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the frame
        employeeLoginFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(employeeLoginFrame, "Both username and password are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(employeeLoginFrame, "Invalid password format. Password must contain at least 8 characters, including uppercase, lowercase letters, and a number.", "Password Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Validate the employee credentials
                boolean loginSuccess = Employee.validateEmployeeLogin(username, password);

                if (loginSuccess) {
                    JOptionPane.showMessageDialog(employeeLoginFrame, "Login Successful!");
                    // Proceed to Employee Portal
                    employeeLoginFrame.dispose(); // Close the login page
                    new EmployeePortal(employeeLoginFrame, username); // Pass the logged-in username here
                } else {
                    JOptionPane.showMessageDialog(employeeLoginFrame, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
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

        // Action listener for back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeeLoginFrame.dispose(); // Close the employee login page
                parentFrame.setVisible(true); // Show the HomePage again (Admin/Employee selection)
            }
        });

        // Set frame visibility
        employeeLoginFrame.setLocationRelativeTo(null); // Center the frame on the screen
        employeeLoginFrame.setVisible(true);
        parentFrame.setVisible(false); // Hide the HomePage when Employee Login page is open
    }
}