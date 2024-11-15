import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ArrayStructureGUI extends JFrame {
    private JPanel contentPane;
    private JTextField arraySizeField;
    private ButtonGroup structureGroup;
    private JRadioButton queueRadio;
    private JRadioButton stackRadio;
    private JLabel resultLabel;

    // Custom colors
    private static final Color PRIMARY_COLOR = new Color(75, 0, 130);  // Indigo
    private static final Color ACCENT_COLOR = new Color(147, 112, 219);  // Medium Purple
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 255);  // Light lavender
    private static final Color SUCCESS_COLOR = new Color(46, 139, 87);  // Sea Green
    private static final Color ERROR_COLOR = new Color(220, 20, 60);  // Crimson

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ArrayStructureGUI frame = new ArrayStructureGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ArrayStructureGUI() {
        setTitle("DSA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        
        // Main panel setup
        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(BACKGROUND_COLOR);
        contentPane.setBorder(new CompoundBorder(
            new EmptyBorder(20, 20, 20, 20),
            new CompoundBorder(
                new LineBorder(ACCENT_COLOR, 2, true),
                new EmptyBorder(20, 20, 20, 20)
            )
        ));
        setContentPane(contentPane);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel titleLabel = new JLabel("DSA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        contentPane.add(titleLabel, gbc);

        JLabel lblArraySize = new JLabel("Enter Array Size:");
        lblArraySize.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblArraySize.setForeground(PRIMARY_COLOR);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(20, 5, 5, 5);
        contentPane.add(lblArraySize, gbc);
        
        arraySizeField = new JTextField();
        arraySizeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        arraySizeField.setPreferredSize(new Dimension(150, 35));
        arraySizeField.setBorder(new CompoundBorder(
            new LineBorder(ACCENT_COLOR, 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridy = 2;
        contentPane.add(arraySizeField, gbc);
        
        JLabel lblStructure = new JLabel("Select Which:");
        lblStructure.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStructure.setForeground(PRIMARY_COLOR);
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 5, 5, 5);
        contentPane.add(lblStructure, gbc);
        
        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(BACKGROUND_COLOR);
        structureGroup = new ButtonGroup();
        
        queueRadio = new JRadioButton("Queue");
        stackRadio = new JRadioButton("Stack");
        
        // Style radio buttons
        for (JRadioButton radio : new JRadioButton[]{queueRadio, stackRadio}) {
            radio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            radio.setBackground(BACKGROUND_COLOR);
            radio.setForeground(PRIMARY_COLOR);
            radio.setFocusPainted(false);
            structureGroup.add(radio);
            radioPanel.add(radio);
        }
        
        gbc.gridy = 4;
        contentPane.add(radioPanel, gbc);
        
        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitButton.setBackground(ACCENT_COLOR);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(new EmptyBorder(10, 20, 10, 20));
        submitButton.setPreferredSize(new Dimension(120, 40));
        
        // Add hover effect
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(PRIMARY_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(ACCENT_COLOR);
            }
        });
        
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 5, 5, 5);
        contentPane.add(submitButton, gbc);
        
        // Result label
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 6;
        contentPane.add(resultLabel, gbc);
        
        // Add action listener to submit button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int arraySize = Integer.parseInt(arraySizeField.getText());
                    
                    if (arraySize <= 0) {
                        resultLabel.setForeground(ERROR_COLOR);
                        resultLabel.setText("Please enter a positive number for array size");
                        return;
                    }
                    
                    if (!queueRadio.isSelected() && !stackRadio.isSelected()) {
                        resultLabel.setForeground(ERROR_COLOR);
                        resultLabel.setText("Please select a data structure");
                        return;
                    }
                    
                    String structure = queueRadio.isSelected() ? "Queue" : "Stack";
                    resultLabel.setForeground(SUCCESS_COLOR);
                    resultLabel.setText("Array size: " + arraySize + ", Structure: " + structure);
                    
                } catch (NumberFormatException ex) {
                    resultLabel.setForeground(ERROR_COLOR);
                    resultLabel.setText("Please enter a valid number for array size");
                }
            }
        });
    }
}
