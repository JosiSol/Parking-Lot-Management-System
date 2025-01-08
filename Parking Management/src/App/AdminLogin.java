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
        adminFrame.setSize(900, 600);
        adminFrame.setLayout(new GridLayout(1, 2));
        
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Icon.jpg"));
        adminFrame.setIconImage(icon.getImage());

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
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setOpaque(false);
        JTextField usernameField = new JTextField();
        usernameField.setOpaque(false);
        usernameField.setBackground(new Color(255, 255, 255, 50)); 
        usernameField.setForeground(Color.WHITE); 
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create password label and password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setOpaque(false);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setOpaque(false);
        passwordField.setBackground(new Color(255, 255, 255, 50));
        passwordField.setForeground(Color.WHITE);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setFont(new Font("Arial", Font.PLAIN, 15));
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

        // Add action listeners to buttons
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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose(); // Close the Admin Login frame
                parentFrame.setVisible(true); // Show the Home Page again
            }
        });

        adminFrame.add(leftPanel);
        adminFrame.add(rightPanel);

        adminFrame.setLocationRelativeTo(null); // Center the frame on the screen
        adminFrame.setVisible(true);
        parentFrame.setVisible(false); // Hide the Home Page
    }
}
