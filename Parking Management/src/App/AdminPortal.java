package App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPortal {

    public AdminPortal(JFrame parentFrame) {
        // Create a new frame for the Admin Portal
        JFrame adminPortalFrame = new JFrame("Admin Portal");
        adminPortalFrame.setSize(400, 400);
        adminPortalFrame.setLayout(null);
        adminPortalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and add buttons
        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.setBounds(100, 50, 200, 30);
        adminPortalFrame.add(addEmployeeButton);

        JButton removeEmployeeButton = new JButton("Remove Employee");
        removeEmployeeButton.setBounds(100, 100, 200, 30);
        adminPortalFrame.add(removeEmployeeButton);

        JButton showEmployeeButton = new JButton("Show Employee");
        showEmployeeButton.setBounds(100, 150, 200, 30);
        adminPortalFrame.add(showEmployeeButton);

        JButton statusButton = new JButton("Status");
        statusButton.setBounds(100, 200, 200, 30);
        adminPortalFrame.add(statusButton);

        JButton reportButton = new JButton("Report");
        reportButton.setBounds(100, 250, 200, 30);
        adminPortalFrame.add(reportButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 300, 200, 30);
        adminPortalFrame.add(backButton);
        
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEmployeePage(adminPortalFrame); // Open the Add Employee page
            }
        });

        
        removeEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveEmployeePage(adminPortalFrame); // Open the Remove Employee page
            }
        });


        // Action listener for the Status button
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ShowStatusPage when Status button is clicked
                new ShowStatusPage(adminPortalFrame);
            }
        });
        
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ShowStatusPage when Status button is clicked
                new ShowReportPage(adminPortalFrame);
            }
        });
        showEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowEmployeePage(adminPortalFrame); // Open the Show Employee page
            }
        });


        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPortalFrame.dispose(); // Close Admin Portal
                parentFrame.setVisible(true); // Show the parent frame (HomePage)
            }
        });

        // Make the frame visible
        adminPortalFrame.setLocationRelativeTo(null); // Center the window
        adminPortalFrame.setVisible(true);

        parentFrame.setVisible(false); // Hide the parent frame (HomePage)
    }
}
