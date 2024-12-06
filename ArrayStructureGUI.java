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
        setTitle("Data Structure Simulator");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 247));

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBackground(new Color(245, 245, 247));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel sizePanel = createStyledPanel("Size");
        sizeField = new JTextField(5);
        styleTextField(sizeField);
        sizePanel.add(sizeField);
        controlPanel.add(sizePanel);

        JPanel typePanel = createStyledPanel("Structure");
        structureTypeCombo = new JComboBox<>(new String[]{"Queue", "Stack"});
        styleComboBox(structureTypeCombo);
        structureTypeCombo.addActionListener(e -> {
            if (dataStructure != null) {
                isStack = structureTypeCombo.getSelectedItem().equals("Stack");
                updateOperations();
                updateDisplay();
            }
        });
        typePanel.add(structureTypeCombo);
        controlPanel.add(typePanel);

        JButton initButton = new JButton("Initialize");
        styleButton(initButton, new Color(79, 70, 229));
        initButton.addActionListener(e -> initializeDataStructure());
        controlPanel.add(initButton);

        JPanel opPanel = createStyledPanel("Operation");
        operationCombo = new JComboBox<>(new String[]{"Enqueue", "Dequeue", "isEmpty", "isFull", "Peek"});
        styleComboBox(operationCombo);
        operationCombo.addActionListener(e -> updateValueFieldVisibility());
        opPanel.add(operationCombo);
        controlPanel.add(opPanel);

        JPanel valuePanel = createStyledPanel("Value");
        valueField = new JTextField(5);
        styleTextField(valueField);
        valuePanel.add(valueField);
        controlPanel.add(valuePanel);

        JButton executeButton = new JButton("Execute");
        styleButton(executeButton, new Color(16, 185, 129));
        executeButton.addActionListener(e -> executeOperation());
        controlPanel.add(executeButton);

        JPanel displayPanel = new JPanel(new BorderLayout(10, 10));
        displayPanel.setBackground(Color.WHITE);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(31, 41, 55));
        titlePanel.add(titleLabel);
        displayPanel.add(titlePanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        displayArea.setBackground(Color.WHITE);
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235)));
        scrollPane.setBackground(Color.WHITE);
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        add(controlPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

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

            dataStructure = new ArrayList<>(size);
            isStack = structureTypeCombo.getSelectedItem().equals("Stack");
            updateOperations();

            displayArea.setText("Data structure initialized with max size: " + size + 
                                "\nType: " + (isStack ? "Stack" : "Queue"));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer for size");
        }
    }

    private void updateOperations() {
        String[] operations;
        if (isStack) {
            operations = new String[]{"Push", "Pop", "isEmpty", "isFull", "Peek"};
        } else {
            operations = new String[]{"Enqueue", "Dequeue", "isEmpty", "isFull", "Peek"};
        }
        operationCombo.setModel(new DefaultComboBoxModel<>(operations));
        updateValueFieldVisibility();
    }

    private void executeOperation() {
        if (dataStructure == null) {
            JOptionPane.showMessageDialog(this, "Please initialize data structure first");
            return;
        }

        String operation = (String) operationCombo.getSelectedItem();

        switch (operation) {
            case "Push":
            case "Enqueue":
                try {
                    int value = Integer.parseInt(valueField.getText());
                    
                    if (dataStructure.size() >= Integer.parseInt(sizeField.getText())) {
                        JOptionPane.showMessageDialog(this, 
                            isStack ? "Stack is full" : "Queue is full");
                        return;
                    }

                    dataStructure.add(value);
                    updateDisplay();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid integer value");
                }
                break;

            case "Pop":
            case "Dequeue":
                if (dataStructure.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        isStack ? "Stack is empty" : "Queue is empty");
                    return;
                }
                if (isStack) {
                    dataStructure.remove(dataStructure.size() - 1);
                } else {
                    dataStructure.remove(0);
                }
                updateDisplay();
                break;

            case "isEmpty":
                JOptionPane.showMessageDialog(this, 
                    dataStructure.isEmpty() ? 
                        (isStack ? "Stack is empty" : "Queue is empty") : 
                        (isStack ? "Stack is not empty" : "Queue is not empty"));
                break;

            case "isFull":
                boolean isFull = dataStructure.size() >= Integer.parseInt(sizeField.getText());
                JOptionPane.showMessageDialog(this, 
                    isFull ? 
                        (isStack ? "Stack is full" : "Queue is full") : 
                        (isStack ? "Stack is not full" : "Queue is not full"));
                break;

            case "Peek":
                if (dataStructure.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        isStack ? "Stack is empty" : "Queue is empty");
                    return;
                }
                int peekIndex = isStack ? dataStructure.size() - 1 : 0;
                String elementPosition = isStack ? "Top" : "Front";
                JOptionPane.showMessageDialog(this, 
                    elementPosition + " element: " + dataStructure.get(peekIndex));
                break;
        }
    }

    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        String structureType = isStack ? "Stack" : "Queue";
        int maxSize = Integer.parseInt(sizeField.getText());
        
        sb.append(String.format("%s Contents (%d/%d elements)\n\n", 
                              structureType, dataStructure.size(), maxSize));
        
        if (dataStructure.isEmpty()) {
            sb.append("Empty " + structureType + "\n");
        } else {
            // Drawing ng table
            if (isStack) {
                // Stack display (horizontal)
                for (int i = 0; i < dataStructure.size(); i++) {
                    sb.append("[ ").append(dataStructure.get(i)).append(" ]");
                    if (i < dataStructure.size() - 1) {
                        sb.append(" ");
                    }
                }
                sb.append(" ← Top\n");
            } else {
                // Queue display (horizontal)
                sb.append("Front → ");
                for (int i = 0; i < dataStructure.size(); i++) {
                    sb.append("[ ").append(dataStructure.get(i)).append(" ]");
                    if (i < dataStructure.size() - 1) {
                        sb.append(" ");
                    }
                }
                if (dataStructure.size() < maxSize) {
                    sb.append(" ← Rear\n");
                } else {
                    sb.append(" (Full)\n");
                }
            }
        }

        // Show empty slots if not full
        if (!dataStructure.isEmpty() && dataStructure.size() < maxSize) {
            sb.append("\nEmpty slots: ").append(maxSize - dataStructure.size());
        }

        displayArea.setText(sb.toString());
    }

    private void updateValueFieldVisibility() {
        String operation = (String) operationCombo.getSelectedItem();
        boolean showField = operation.equals("Push") || operation.equals("Enqueue");
        valueField.setVisible(showField);
        valueField.getParent().setVisible(showField);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArrayStructureGUI gui = new ArrayStructureGUI();
            gui.setVisible(true);
        });
    }
}
