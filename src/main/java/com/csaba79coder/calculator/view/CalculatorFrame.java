package com.csaba79coder.calculator.view;

import javax.swing.*;
import java.awt.*;

/**
 * Main calculator frame with modern UI design.
 *
 * Features:
 * - Modern dark theme with orange accents
 * - Responsive button layout
 * - Hover effects on buttons
 * - Clean grid-based design
 * - Display field for calculations
 *
 * This class is responsible for:
 * - Creating all UI components
 * - Layouting components
 * - Providing getters for controller access
 * - Updating display
 * - Showing error messages
 *
 * @author Csaba79coder
 * @version 1.0
 */
public class CalculatorFrame extends JFrame {

    // Display field
    private JTextField displayField;

    // Button panel
    private JPanel buttonPanel;

    // Button references for controller access
    private JButton[] numberButtons;
    private JButton[] operationButtons;
    private JButton equalsButton;
    private JButton clearButton;
    private JButton deleteButton;
    private JButton decimalButton;
    private JButton signButton;
    private JButton percentButton;

    /**
     * Constructor - Initializes the calculator frame.
     * Sets up all UI components and layout.
     */
    public CalculatorFrame() {
        initializeFrame();
        initializeComponents();
        layoutComponents();
    }

    /**
     * Initializes the main frame properties.
     * Sets title, size, location, and theme.
     */
    private void initializeFrame() {
        setTitle("Modern Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 550);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);

        // Modern dark theme background
        getContentPane().setBackground(new Color(30, 30, 30));
    }

    /**
     * Initializes all UI components (buttons, display field).
     * Creates styled buttons with modern design.
     */
    private void initializeComponents() {
        // Display field with modern styling
        displayField = new JTextField("0");
        displayField.setEditable(false);
        displayField.setFont(new Font("Segoe UI", Font.BOLD, 36));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(new Color(45, 45, 45));
        displayField.setForeground(Color.WHITE);
        displayField.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        displayField.setCaretColor(Color.WHITE);

        // Initialize number buttons (0-9)
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createStyledButton(String.valueOf(i), new Color(60, 60, 60));
        }

        // Initialize operation buttons (+, -, ×, ÷)
        operationButtons = new JButton[4];
        operationButtons[0] = createStyledButton("+", new Color(255, 149, 0));
        operationButtons[1] = createStyledButton("-", new Color(255, 149, 0));
        operationButtons[2] = createStyledButton("×", new Color(255, 149, 0));
        operationButtons[3] = createStyledButton("÷", new Color(255, 149, 0));

        // Special buttons
        equalsButton = createStyledButton("=", new Color(255, 149, 0));
        clearButton = createStyledButton("C", new Color(165, 165, 165));
        deleteButton = createStyledButton("←", new Color(165, 165, 165));
        decimalButton = createStyledButton(".", new Color(60, 60, 60));
        signButton = createStyledButton("+/-", new Color(165, 165, 165));
        percentButton = createStyledButton("%", new Color(165, 165, 165));
    }

    /**
     * Creates a styled button with modern design.
     *
     * @param text The button text
     * @param backgroundColor The button background color
     * @return Styled JButton
     */
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect - button gets brighter on mouse hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    /**
     * Layouts all components in the frame.
     * Uses BorderLayout for main frame and GridLayout for buttons.
     *
     * Button layout:
     * Row 1: C, +/-, %, ÷
     * Row 2: 7, 8, 9, ×
     * Row 3: 4, 5, 6, -
     * Row 4: 1, 2, 3, +
     * Row 5: 0, ., ⌫, =
     */
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));

        // Add display at top
        add(displayField, BorderLayout.NORTH);

        // Button panel with grid layout (5 rows, 4 columns)
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Row 1: C, +/-, %, ÷
        buttonPanel.add(clearButton);
        buttonPanel.add(signButton);
        buttonPanel.add(percentButton);
        buttonPanel.add(operationButtons[3]); // ÷

        // Row 2: 7, 8, 9, ×
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(operationButtons[2]); // ×

        // Row 3: 4, 5, 6, -
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(operationButtons[1]); // -

        // Row 4: 1, 2, 3, +
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(operationButtons[0]); // +

        // Row 5: 0, ., ⌫, =
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(decimalButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(equalsButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    // ========== GETTERS FOR CONTROLLER ACCESS ==========

    /**
     * Gets the display field.
     * @return The display text field
     */
    public JTextField getDisplayField() {
        return displayField;
    }

    /**
     * Gets all number buttons (0-9).
     * @return Array of number buttons
     */
    public JButton[] getNumberButtons() {
        return numberButtons;
    }

    /**
     * Gets all operation buttons (+, -, ×, ÷).
     * @return Array of operation buttons
     */
    public JButton[] getOperationButtons() {
        return operationButtons;
    }

    /**
     * Gets the equals button (=).
     * @return The equals button
     */
    public JButton getEqualsButton() {
        return equalsButton;
    }

    /**
     * Gets the clear button (C).
     * @return The clear button
     */
    public JButton getClearButton() {
        return clearButton;
    }

    /**
     * Gets the delete button (⌫).
     * @return The delete button
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * Gets the decimal button (.).
     * @return The decimal button
     */
    public JButton getDecimalButton() {
        return decimalButton;
    }

    /**
     * Gets the sign toggle button (+/-).
     * @return The sign button
     */
    public JButton getSignButton() {
        return signButton;
    }

    /**
     * Gets the percent button (%).
     * @return The percent button
     */
    public JButton getPercentButton() {
        return percentButton;
    }

    // ========== PUBLIC METHODS FOR CONTROLLER ==========

    /**
     * Updates the display with the given value.
     * Called by controller after each calculation.
     *
     * @param value The value to display
     */
    public void updateDisplay(String value) {
        displayField.setText(value);
    }

    /**
     * Shows an error message dialog.
     * Used for errors like division by zero.
     *
     * @param message The error message to display
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Calculator Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
