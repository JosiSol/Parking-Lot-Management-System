package App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class HomePage {

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Parking Management System - Home Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600); // Set frame size
        frame.setLayout(new BorderLayout()); // Use BorderLayout
        // Set the icon
        ImageIcon icon = new ImageIcon(HomePage.class.getResource("/img/Icon.jpg"));
        frame.setIconImage(icon.getImage());

        // Create a custom panel with background image
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        frame.setContentPane(backgroundPanel); // Set custom panel as the content pane

        // Load custom font
        // Adjust the path and size as needed
        Font customFont;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, HomePage.class.getResourceAsStream("/Fonts/Winter Minie.ttf")).deriveFont(38f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace(); // Log the error for debugging
            customFont = new Font("SansSerif", Font.PLAIN, 38); // Fallback font
        }


        
        // Create a panel to hold title and buttons
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Vertical box layout
        contentPanel.setOpaque(false); // Make the content panel transparent
        
        // Create a margin above the title (this adds the margin before the title)
        contentPanel.add(Box.createVerticalStrut(100)); // Adjust the vertical space as needed


        // Create a label for the title
        JLabel titleLabel = new JLabel("Welcome to Parking Management System", JLabel.CENTER);
        titleLabel.setFont(customFont);  // Set the custom font
        titleLabel.setForeground(new Color(255,204,31)); // Set text color to white for better visibility
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title
        contentPanel.add(Box.createVerticalStrut(80)); // Adds space before the title
        contentPanel.add(titleLabel); // Add label to the content panel

        // Create a panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Vertical box layout
        buttonPanel.setOpaque(false); // Make the button panel transparent

        // Create buttons without background colors
        JButton employeeButton = new JButton("Employee");
        JButton adminButton = new JButton("Admin");
        JButton exitButton = new JButton("Exit");

        // Set the custom font for buttons
        employeeButton.setFont(customFont);
        adminButton.setFont(customFont);
        exitButton.setFont(customFont);

        // Remove background color
        employeeButton.setBackground(new Color(40,40,40));
        adminButton.setBackground(new Color(40,40,40));
        exitButton.setBackground(new Color(40,40,40));

        // Set button text color
        employeeButton.setForeground(new Color(205,154,51));
        adminButton.setForeground(new Color(205,154,51));
        exitButton.setForeground(new Color(205,154,51));

        // Add buttons to the button panel
        buttonPanel.add(Box.createVerticalStrut(10)); // Adds space between buttons
        buttonPanel.add(adminButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(employeeButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Adds space between buttons
        buttonPanel.add(exitButton);

        // Add button panel to the content panel
        contentPanel.add(buttonPanel);

        // Add content panel to the center of the background panel
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmployeeLogin(frame); // Open the Employee Login page
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLogin(frame); // Pass the current frame to AdminLogin
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // Exit the program
                }
            }
        });

        // Set frame visibility
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }
    
    // Custom method to load a font
    public static Font loadCustomFont(String fontPath, float size) {
        try {
            // Load font from file
            InputStream fontStream = new FileInputStream(new File(fontPath));
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            customFont = customFont.deriveFont(size); // Set the font size
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont); // Register the font
            return customFont;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 18); // Fallback font if loading fails
        }
    }

    // Custom JPanel class for background image
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                // Load your background image (adjust path as necessary)
                backgroundImage = ImageIO.read(HomePage.class.getResourceAsStream("/img/HomePageBG.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Scale the image to fill the panel
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
