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
        addEmployeeFrame.setSize(500, 400);
        addEmployeeFrame.setLayout(new BorderLayout());
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Icon.jpg"));
        addEmployeeFrame.setIconImage(icon.getImage());


        // Create a label for the title
        JLabel titleLabel = new JLabel("Add New Employee", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        addEmployeeFrame.add(titleLabel, BorderLayout.NORTH);

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
        formPanel.setLayout(new GridLayout(6, 2, 10, 10)); // 6 rows, 2 columns layout

        // Create labels and text fields for employee details
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JTextField nameField = new JTextField();
        customizeTextField(nameField);
        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        String[] genderOptions = {"Male", "Female"};
        JComboBox<String> genderField = new JComboBox<>(genderOptions);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JTextField usernameField = new JTextField();
        customizeTextField(usernameField);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JPasswordField passwordField = new JPasswordField();
        customizePasswordField(passwordField);

        // Create a checkbox to toggle password visibility
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setFont(new Font("Arial", Font.PLAIN, 15));
        showPassword.setOpaque(false); // Make the checkbox transparent
        showPassword.setHorizontalAlignment(SwingConstants.CENTER); // Align the checkbox to the center
        showPassword.addActionListener(e -> {
            passwordField.setEchoChar(showPassword.isSelected() ? '\u0000' : '\u2022');
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
        JButton addButton = createGradientButton("Add");
        
        JButton backButton = createGradientButton("Back");
        

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
                } else {
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
        
    }

    // Helper method to customize text fields
    private void customizeTextField(JTextField textField) {
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        textField.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
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
