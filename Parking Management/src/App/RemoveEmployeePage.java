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
        removeEmployeeFrame.setSize(420, 200);
        removeEmployeeFrame.setLayout(new BorderLayout());
     // Set the icon
        ImageIcon icon = new ImageIcon(HomePage.class.getResource("/img/Icon.jpg"));
        removeEmployeeFrame.setIconImage(icon.getImage());

        // Create a custom JPanel for the background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image and scale it to fit the panel
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/img/PortalBG.jpg")); // Replace with your image path
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // Draw the image scaled to panel size
            }
        };

        backgroundPanel.setLayout(new BorderLayout()); // Set layout for background panel

        // Create a label for the title
        JLabel titleLabel = new JLabel("Remove Employee", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Add the title label to the background panel
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Create a panel for the form input
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // FlowLayout to place the label and text field next to each other
        formPanel.setOpaque(false);
        
        // Create label and text field for the username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Increase label font size

        JTextField usernameField = new JTextField(25); // Set a wider width for the text field
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font size for the text field
        customizeTextField(usernameField);
        
        // Add components to the form panel
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        // Add the form panel to the center of the background panel
        backgroundPanel.add(formPanel, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());

        // Create Delete button, Add button and Back button
        JButton deleteButton = createGradientButton("Delete");
        JButton backButton = createGradientButton("Back");

        // Add buttons to the button panel
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the background panel
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

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
        removeEmployeeFrame.setContentPane(backgroundPanel); // Set the background panel as the content pane
        removeEmployeeFrame.setVisible(true);
    }
    
 // Helper method to customize text fields
    private void customizeTextField(JTextField textField) {
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        textField.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
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
