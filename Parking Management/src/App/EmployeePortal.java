package App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EmployeePortal {

    private String loggedInUsername;  // Store the logged-in username

    public EmployeePortal(JFrame parentFrame, String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;

        // Create the frame for the employee portal
        JFrame employeePortalFrame = new JFrame("Employee Portal");
        employeePortalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        employeePortalFrame.setSize(900, 600);
        employeePortalFrame.setLayout(new BorderLayout());
        
        ImageIcon icon = new ImageIcon(HomePage.class.getResource("/img/Icon.jpg"));
        employeePortalFrame.setIconImage(icon.getImage());
        
        // Create a custom panel to draw the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/img/PortalBG.jpg"));
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        employeePortalFrame.setContentPane(backgroundPanel);
        
        // Create a panel to center buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 15, 15)); // 6 rows, 1 column, spacing
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(300, 150, 300, 300);
        backgroundPanel.add(buttonPanel);

     
        // Create buttons for the portal
        JButton checkInButton = createGradientButton("Check In");
        JButton checkOutButton = createGradientButton("Check Out");
        JButton statusButton = createGradientButton("Status");
        JButton settingsButton = createGradientButton("Change Password");
        JButton backButton = createGradientButton("Logout");

        buttonPanel.add(checkInButton);
        buttonPanel.add(checkOutButton);
        buttonPanel.add(statusButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(backButton);


        // Action listener for the Check In button
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open CheckIn page when Check In button is clicked
                new CheckIn(employeePortalFrame);
            }
        });

        // Action listener for Check Out button
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Check Out page
                new CheckOut(employeePortalFrame);
            }
        });

        // Action listener for Status button
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ShowStatusPage when Status button is clicked
                new ShowStatusPage(employeePortalFrame);
            }
        });

        // Action listener for Settings button
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Settings page when Settings button is clicked
                new SettingsPage(employeePortalFrame, loggedInUsername); 
            }
        });

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePortalFrame.dispose(); 
                parentFrame.setVisible(true); 
            }
        });

        // Set frame visibility
        employeePortalFrame.setLocationRelativeTo(null);
        employeePortalFrame.setVisible(true);
        parentFrame.setVisible(false); 
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
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
}