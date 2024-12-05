import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ArrayStructureGUI extends JFrame {
    private JTextField sizeField;
    private JComboBox<String> structureTypeCombo;
    private JComboBox<String> operationCombo;
    private JTextField valueField;
    private JTextArea displayArea;
    private ArrayList<Integer> dataStructure;
    private boolean isStack;

    public ArrayStructureGUI() {
        // Set up the main frame
        setTitle("Data Structure Simulator");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 247));

        // Create main control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBackground(new Color(245, 245, 247));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Size input with modern styling
        JPanel sizePanel = createStyledPanel("Size");
        sizeField = new JTextField(5);
        styleTextField(sizeField);
        sizePanel.add(sizeField);
        controlPanel.add(sizePanel);

        // Structure type selection
        JPanel typePanel = createStyledPanel("Structure");
        structureTypeCombo = new JComboBox<>(new String[]{"Queue", "Stack"});
        styleComboBox(structureTypeCombo);
        typePanel.add(structureTypeCombo);
        controlPanel.add(typePanel);

        // Initialize button
        JButton initButton = new JButton("Initialize");
        styleButton(initButton, new Color(79, 70, 229));
        initButton.addActionListener(e -> initializeDataStructure());
        controlPanel.add(initButton);

        // Operation selection
        JPanel opPanel = createStyledPanel("Operation");
        operationCombo = new JComboBox<>(new String[]{"Push", "Pop"});
        styleComboBox(operationCombo);
        operationCombo.addActionListener(e -> updateValueFieldVisibility());
        opPanel.add(operationCombo);
        controlPanel.add(opPanel);

        // Value input
        JPanel valuePanel = createStyledPanel("Value");
        valueField = new JTextField(5);
        styleTextField(valueField);
        valuePanel.add(valueField);
        controlPanel.add(valuePanel);

        // Execute button
        JButton executeButton = new JButton("Execute");
        styleButton(executeButton, new Color(16, 185, 129));
        executeButton.addActionListener(e -> executeOperation());
        controlPanel.add(executeButton);

        // Create display panel
        JPanel displayPanel = new JPanel(new BorderLayout(10, 10));
        displayPanel.setBackground(Color.WHITE);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(31, 41, 55));
        titlePanel.add(titleLabel);
        displayPanel.add(titlePanel, BorderLayout.NORTH);

        // Display area with modern styling
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        displayArea.setBackground(Color.WHITE);
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235)));
        scrollPane.setBackground(Color.WHITE);
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        // Add components to frame
        add(controlPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

        // Initial visibility update
        updateValueFieldVisibility();
    }

    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(new Color(245, 245, 247));
        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(75, 85, 99));
        panel.add(label);
        return panel;
    }

    private void styleTextField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        field.setFont(new Font("Arial", Font.PLAIN, 13));
    }

    private void styleComboBox(JComboBox<?> combo) {
        combo.setBackground(Color.WHITE);
        combo.setFont(new Font("Arial", Font.PLAIN, 13));
        ((JComponent) combo.getRenderer()).setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void initializeDataStructure() {
        try {
            int size = Integer.parseInt(sizeField.getText());
            if (size <= 0) {
                JOptionPane.showMessageDialog(this, "Size must be a positive integer");
                return;
            }

            // Initialize data structure
            dataStructure = new ArrayList<>(size);
            isStack = structureTypeCombo.getSelectedItem().equals("Stack");

            displayArea.setText("Data structure initialized with max size: " + size + 
                                "\nType: " + (isStack ? "Stack" : "Queue"));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer for size");
        }
    }

    private void executeOperation() {
        if (dataStructure == null) {
            JOptionPane.showMessageDialog(this, "Please initialize data structure first");
            return;
        }

        String operation = (String) operationCombo.getSelectedItem();

        if (operation.equals("Push")) {
            try {
                int value = Integer.parseInt(valueField.getText());
                
                if (dataStructure.size() >= Integer.parseInt(sizeField.getText())) {
                    JOptionPane.showMessageDialog(this, "Data structure is full");
                    return;
                }

                if (isStack) {
                    // Stack push (add to end)
                    dataStructure.add(value);
                } else {
                    // Queue push (add to end)
                    dataStructure.add(value);
                }

                updateDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer value");
            }
        } else if (operation.equals("Pop")) {
            if (dataStructure.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Data structure is empty");
                return;
            }

            if (isStack) {
                // Stack pop (remove from end)
                dataStructure.remove(dataStructure.size() - 1);
            } else {
                // Queue pop (remove from beginning)
                dataStructure.remove(0);
            }

            updateDisplay();
        }
    }

    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        String structureType = isStack ? "Stack" : "Queue";
        
        // Header with structure type and size
        sb.append(String.format("Current %s Contents (%d elements)\n\n", 
                              structureType, dataStructure.size()));
        
        if (dataStructure.isEmpty()) {
            sb.append("(empty)\n");
        } else {
            // Draw top border
            sb.append("┌");
            for (int i = 0; i < dataStructure.size(); i++) {
                sb.append("─────");
                if (i < dataStructure.size() - 1) sb.append("┬");
            }
            sb.append("┐\n");

            // Draw indices
            sb.append("│");
            for (int i = 0; i < dataStructure.size(); i++) {
                sb.append(String.format(" %-3d ", i));
                sb.append("│");
            }
            sb.append("\n");

            // Draw middle border
            sb.append("├");
            for (int i = 0; i < dataStructure.size(); i++) {
                sb.append("─────");
                if (i < dataStructure.size() - 1) sb.append("┼");
            }
            sb.append("┤\n");

            // Draw values
            sb.append("│");
            for (int i = 0; i < dataStructure.size(); i++) {
                sb.append(String.format(" %-3d ", dataStructure.get(i)));
                sb.append("│");
            }
            sb.append("\n");

            // Draw bottom border
            sb.append("└");
            for (int i = 0; i < dataStructure.size(); i++) {
                sb.append("─────");
                if (i < dataStructure.size() - 1) sb.append("┴");
            }
            sb.append("┘\n");

            // Add operation indicators
            sb.append("\n");
            if (isStack) {
                sb.append("Push/Pop ").append("↑").append("\n");
            } else {
                sb.append("Push →      ← Pop\n");
            }
        }

        displayArea.setText(sb.toString());
    }

    private void updateValueFieldVisibility() {
        boolean isPush = operationCombo.getSelectedItem().equals("Push");
        valueField.setVisible(isPush);
        valueField.getParent().setVisible(isPush);
    }

    public static void main(String[] args) {
        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            ArrayStructureGUI gui = new ArrayStructureGUI();
            gui.setVisible(true);
        });
    }
}