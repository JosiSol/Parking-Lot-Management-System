package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveEmployeePage {

    public RemoveEmployeePage(JFrame parentFrame) {
        // Create the frame for removing an employee
        JFrame removeEmployeeFrame = new JFrame("Remove Employee");
        removeEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removeEmployeeFrame.setSize(400, 200);
        removeEmployeeFrame.setLayout(new BorderLayout());

        // Create a label for the title
        JLabel titleLabel = new JLabel("Remove Employee", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        removeEmployeeFrame.add(titleLabel, BorderLayout.NORTH);

        // Create a panel for the form input
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns layout

        // Create label and text field for the username
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        // Add components to the form panel
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        // Add the form panel to the center of the frame
        removeEmployeeFrame.add(formPanel, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create Delete button, Add button and Back button
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        // Add buttons to the button panel
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the frame
        removeEmployeeFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for Delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();

                // Check if username field is empty
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(removeEmployeeFrame, "Username is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Check if employee exists
                    boolean employeeExists = Employee.checkEmployeeExists(username);
                    
                    if (employeeExists) {
                        // Remove employee by username
                        Employee.removeEmployeeByUsername(username);
                        JOptionPane.showMessageDialog(removeEmployeeFrame, "Employee removed successfully.");
                        removeEmployeeFrame.dispose(); // Close the Remove Employee page
                        parentFrame.setVisible(true); // Show the Admin Portal again
                    } else {
                        // Show error message if employee is not found
                        JOptionPane.showMessageDialog(removeEmployeeFrame, "Employee with username '" + username + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeEmployeeFrame.dispose(); // Close the Remove Employee page
                parentFrame.setVisible(true); // Show the Admin Portal again
            }
        });

        // Set frame visibility
        removeEmployeeFrame.setLocationRelativeTo(null); // Center the frame on the screen
        removeEmployeeFrame.setVisible(true);
        parentFrame.setVisible(false); // Hide the Admin Portal when Remove Employee page is open
    }
}
