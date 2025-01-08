package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowEmployeePage {

    public ShowEmployeePage(JFrame parentFrame) {
        
        // Create the frame for showing employee details
        JFrame showEmployeeFrame = new JFrame("Show Employees");
        showEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showEmployeeFrame.setSize(500, 400);
        showEmployeeFrame.setLayout(new BorderLayout());
        
        ImageIcon icon = new ImageIcon(HomePage.class.getResource("/img/Icon.jpg"));
        showEmployeeFrame.setIconImage(icon.getImage());

        // Create a label for the title
        JLabel titleLabel = new JLabel("All Employees", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        showEmployeeFrame.add(titleLabel, BorderLayout.NORTH);

        // Create a panel to display employee details in a scrollable area
        JPanel employeesPanel = new JPanel();
        employeesPanel.setLayout(new BoxLayout(employeesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(employeesPanel);
        
        // Get all employees and display them
        List<Employee> employees = Employee.getAllEmployees();
        if (employees.isEmpty()) {
            JLabel noEmployeesLabel = new JLabel("No employees found.", JLabel.CENTER);
            employeesPanel.add(noEmployeesLabel);
        } else {
            for (Employee employee : employees) {
                JPanel employeePanel = new JPanel();
                employeePanel.setLayout(new GridLayout(4, 2));
                employeePanel.add(new JLabel("Name:"));
                employeePanel.add(new JLabel(employee.getName()));
                employeePanel.add(new JLabel("Gender:"));
                employeePanel.add(new JLabel(employee.getGender()));
                employeePanel.add(new JLabel("Username:"));
                employeePanel.add(new JLabel(employee.getUsername()));
                employeePanel.add(new JLabel("Password:"));
                employeePanel.add(new JLabel(employee.getPassword()));
                
                // Add this employee panel to the main panel
                employeesPanel.add(employeePanel);
                employeesPanel.add(Box.createVerticalStrut(10));
            }
        }

        showEmployeeFrame.add(scrollPane, BorderLayout.CENTER);

        // Create a back button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);

        showEmployeeFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEmployeeFrame.dispose();
                parentFrame.setVisible(true); 
            }
        });

        // Set frame visibility
        showEmployeeFrame.setLocationRelativeTo(null);
        showEmployeeFrame.setVisible(true);
        parentFrame.setVisible(false); // Hide the Admin Portal when Show Employee page is open
    }
}

