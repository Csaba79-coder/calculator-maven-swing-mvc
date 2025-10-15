package com.csaba79coder.calculator.controller;

import com.csaba79coder.calculator.value.Operation;
import com.csaba79coder.calculator.model.Calculator;
import com.csaba79coder.calculator.view.CalculatorFrame;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Controller class that handles user interactions and coordinates
 * between the model and view using MVC pattern.
 *
 * Responsibilities:
 * - Event handling for button clicks
 * - Keyboard input processing
 * - Coordinating Model and View updates
 * - User interaction logic
 *
 * @author Csaba79coder
 * @version 1.0
 */
public class CalculatorController {

    private final Calculator model;
    private final CalculatorFrame view;

    /**
     * Constructor - Initializes controller with model and view.
     * Sets up all event listeners.
     *
     * @param model The calculator model (business logic)
     * @param view The calculator view (UI)
     */
    public CalculatorController(Calculator model, CalculatorFrame view) {
        this.model = model;
        this.view = view;
        initializeListeners();
    }

    /**
     * Initializes all event listeners for buttons and keyboard.
     * This method sets up:
     * - Number button listeners (0-9)
     * - Operation button listeners (+, -, ×, ÷)
     * - Special button listeners (=, C, ⌫, ., +/-, %)
     * - Keyboard input listener
     */
    private void initializeListeners() {
        // Number button listeners (0-9)
        JButton[] numberButtons = view.getNumberButtons();
        for (int i = 0; i < numberButtons.length; i++) {
            final String digit = String.valueOf(i);
            numberButtons[i].addActionListener(e -> handleNumberInput(digit));
        }

        // Operation button listeners (+, -, ×, ÷)
        JButton[] operationButtons = view.getOperationButtons();
        String[] operations = {"+", "-", "×", "÷"};
        for (int i = 0; i < operationButtons.length; i++) {
            final String op = operations[i];
            operationButtons[i].addActionListener(e -> handleOperationInput(op));
        }

        // Special button listeners
        view.getEqualsButton().addActionListener(e -> handleEquals());
        view.getClearButton().addActionListener(e -> handleClear());
        view.getDeleteButton().addActionListener(e -> handleDelete());
        view.getDecimalButton().addActionListener(e -> handleDecimal());
        view.getSignButton().addActionListener(e -> handleSign());
        view.getPercentButton().addActionListener(e -> handlePercent());

        // Keyboard support
        view.getDisplayField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });

        // Make sure the display field has focus for keyboard input
        view.getDisplayField().setFocusable(true);
        view.getDisplayField().requestFocusInWindow();
    }

    /**
     * Handles number button input (0-9).
     * Appends digit to current number in the model.
     *
     * @param digit The digit to append (0-9)
     */
    private void handleNumberInput(String digit) {
        model.appendDigit(digit);
        updateDisplay();
    }

    /**
     * Handles operation button input (+, -, ×, ÷).
     * Sets the operation in the model.
     *
     * @param operationSymbol The operation symbol
     */
    private void handleOperationInput(String operationSymbol) {
        Operation operation = Operation.fromSymbol(operationSymbol);
        if (operation != null) {
            model.setOperation(operation);
            updateDisplay();
        }
    }

    /**
     * Handles equals button (=).
     * Calculates and displays the result.
     * Shows error message if calculation fails (e.g., division by zero).
     */
    private void handleEquals() {
        try {
            model.calculateResult();
            updateDisplay();
        } catch (ArithmeticException e) {
            view.showError(e.getMessage());
            handleClear();
        }
    }

    /**
     * Handles clear button (C).
     * Resets calculator to initial state.
     */
    private void handleClear() {
        model.reset();
        updateDisplay();
    }

    /**
     * Handles delete button (⌫).
     * Removes last digit from current number.
     * If only one digit remains, clears to zero.
     */
    private void handleDelete() {
        String currentDisplay = view.getDisplayField().getText();

        if (currentDisplay.length() > 1) {
            currentDisplay = currentDisplay.substring(0, currentDisplay.length() - 1);
            try {
                model.setCurrentValue(new java.math.BigDecimal(currentDisplay));
                updateDisplay();
            } catch (NumberFormatException e) {
                handleClear();
            }
        } else {
            handleClear();
        }
    }

    /**
     * Handles decimal point button (.).
     * Adds decimal point to current number if not already present.
     */
    private void handleDecimal() {
        model.appendDecimalPoint();
        updateDisplay();
    }

    /**
     * Handles sign toggle button (+/-).
     * Changes the sign of the current number (positive ↔ negative).
     */
    private void handleSign() {
        model.toggleSign();
        updateDisplay();
    }

    /**
     * Handles percent button (%).
     * Converts current number to percentage (divides by 100).
     */
    private void handlePercent() {
        model.calculatePercentage();
        updateDisplay();
    }

    /**
     * Handles keyboard input.
     * Supports:
     * - Number keys (0-9)
     * - Operation keys (+, -, *, /)
     * - Enter/= for equals
     * - Decimal point (. or ,)
     * - Backspace for delete
     * - Escape/C for clear
     * - % for percent
     *
     * @param e The keyboard event
     */
    private void handleKeyPress(KeyEvent e) {
        char keyChar = e.getKeyChar();
        int keyCode = e.getKeyCode();

        // Number keys (0-9)
        if (Character.isDigit(keyChar)) {
            handleNumberInput(String.valueOf(keyChar));
        }
        // Operation keys
        else if (keyChar == '+') {
            handleOperationInput("+");
        } else if (keyChar == '-') {
            handleOperationInput("-");
        } else if (keyChar == '*' || keyChar == 'x' || keyChar == 'X') {
            handleOperationInput("×");
        } else if (keyChar == '/') {
            handleOperationInput("÷");
        }
        // Special keys
        else if (keyChar == '=' || keyCode == KeyEvent.VK_ENTER) {
            handleEquals();
        } else if (keyChar == '.' || keyChar == ',') {
            handleDecimal();
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            handleDelete();
        } else if (keyCode == KeyEvent.VK_ESCAPE || keyChar == 'c' || keyChar == 'C') {
            handleClear();
        } else if (keyChar == '%') {
            handlePercent();
        }
    }

    /**
     * Updates the display with current value from model.
     * Called after every user action to keep UI synchronized.
     */
    private void updateDisplay() {
        view.updateDisplay(model.getDisplayValue());
    }
}
