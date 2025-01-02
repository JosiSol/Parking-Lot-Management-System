package App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AddEmployeePage {

    public AddEmployeePage(JFrame parentFrame) {
        // Create the frame for adding an employee
        JFrame addEmployeeFrame = new JFrame("Add Employee");
        addEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addEmployeeFrame.setSize(400, 300);
        addEmployeeFrame.setLayout(new BorderLayout());

        // Create a label for the title
        JLabel titleLabel = new JLabel("Add New Employee", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addEmployeeFrame.add(titleLabel, BorderLayout.NORTH);

        // Create a panel for the form inputs
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10)); // 5 rows, 2 columns layout

        // Create labels and text fields for employee details
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        String[] genderOptions = {"Male", "Female"};
        JComboBox<String> genderField = new JComboBox<>(genderOptions);
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        //Create a checkbox to toggle password visibility
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(e -> {
            passwordField.setEchoChar(showPassword.isSelected()? '\u0000' : '\u2022');
        });

        // Add components to the form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(genderLabel);
        formPanel.add(genderField);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(showPassword);

        // Add the form panel to the center of the frame
        addEmployeeFrame.add(formPanel, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create Add button and back button
        JButton addButton = new JButton("Add");
        JButton backButton = new JButton("Back");

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the frame
        addEmployeeFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String gender = (String) genderField.getSelectedItem();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (name.isEmpty() || gender.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(addEmployeeFrame, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else if (!isValidPassword(password)) {
                    JOptionPane.showMessageDialog(addEmployeeFrame, "Password must contain at least 8 characters with a mix of uppercase, lowercase, and a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }else {
                    // Create a new Employee object and add it to the file
                    Employee newEmployee = new Employee(name, gender, username, password);
                    Employee.addEmployeeToFile(newEmployee);

                    // Show success message
                    JOptionPane.showMessageDialog(addEmployeeFrame, "Employee added successfully.");
                    addEmployeeFrame.dispose(); // Close the Add Employee page
                    parentFrame.setVisible(true); // Show the Admin Portal again
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
        });

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployeeFrame.dispose(); // Close the Add Employee page
                parentFrame.setVisible(true); // Show the Admin Portal again
            }
        });

        // Set frame visibility
        addEmployeeFrame.setLocationRelativeTo(null); // Center the frame on the screen
        addEmployeeFrame.setVisible(true);
        parentFrame.setVisible(false); // Hide the Admin Portal when Add Employee page is open
    }
}
