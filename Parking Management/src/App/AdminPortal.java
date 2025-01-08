package App;

import javax.swing.*;
import java.awt.*;

public class AdminPortal {

    public AdminPortal(JFrame parentFrame) {
        // Create a new frame for the Admin Portal
        JFrame adminPortalFrame = new JFrame("Admin Portal");
        adminPortalFrame.setSize(900, 600);
        adminPortalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Set the icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Icon.jpg"));
        adminPortalFrame.setIconImage(icon.getImage());
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
        backgroundPanel.setLayout(null); // Use absolute layout for placing components
        adminPortalFrame.setContentPane(backgroundPanel);

        // Create a panel to center buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 15, 15)); // 6 rows, 1 column, spacing
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.setBounds(300, 150, 300, 300); // Center in the frame
        backgroundPanel.add(buttonPanel);

        // Create and style buttons
        JButton addEmployeeButton = createGradientButton("Add Employee");
        JButton removeEmployeeButton = createGradientButton("Remove Employee");
        JButton showEmployeeButton = createGradientButton("Show Employee");
        JButton statusButton = createGradientButton("Status");
        JButton reportButton = createGradientButton("Report");
        JButton backButton = createGradientButton("Logout");

        // Add buttons to the panel
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(removeEmployeeButton);
        buttonPanel.add(showEmployeeButton);
        buttonPanel.add(statusButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(backButton);

        // Add action listeners to buttons (no changes here)
        addEmployeeButton.addActionListener(e -> new AddEmployeePage(adminPortalFrame));
        removeEmployeeButton.addActionListener(e -> new RemoveEmployeePage(adminPortalFrame));
        showEmployeeButton.addActionListener(e -> new ShowEmployeePage(adminPortalFrame));
        statusButton.addActionListener(e -> new ShowStatusPage(adminPortalFrame));
        reportButton.addActionListener(e -> new ShowReportPage(adminPortalFrame));
        backButton.addActionListener(e -> {
            adminPortalFrame.dispose();
            parentFrame.setVisible(true);
        });

        // Make the frame visible
        adminPortalFrame.setLocationRelativeTo(null); // Center the window
        adminPortalFrame.setVisible(true);

        parentFrame.setVisible(false); // Hide the parent frame (HomePage)
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
