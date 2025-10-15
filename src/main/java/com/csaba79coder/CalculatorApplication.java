package com.csaba79coder.calculator;

import com.csaba79coder.calculator.controller.CalculatorController;
import com.csaba79coder.calculator.model.Calculator;
import com.csaba79coder.calculator.view.CalculatorFrame;

import javax.swing.*;

/**
 * Main application entry point for the Advanced Swing Calculator.
 *
 * This calculator application demonstrates professional Java development practices:
 *
 * ARCHITECTURE AND DESIGN:
 * ✅ MVC Pattern - Clean separation of Model, View, and Controller
 * ✅ Professional Code - JavaDoc, clean code principles, SOLID principles
 * ✅ English Naming Conventions - All code follows Java naming standards
 *
 * UI/UX FEATURES:
 * ✅ Modern UI - Dark theme with orange accents (iOS calculator inspired)
 * ✅ Hover Effects - Interactive button animations
 * ✅ Keyboard Support - Full keyboard navigation and input
 * ✅ Responsive Design - Clean grid layout
 *
 * CALCULATION FEATURES:
 * ✅ Basic Operations - Addition, subtraction, multiplication, division
 * ✅ Scientific Functions - Square root, power, trigonometry (sin, cos, tan)
 * ✅ Memory Functions - M+, M-, MR, MC for storing values
 * ✅ Special Operations - Sign toggle (+/-), percentage, decimal support
 * ✅ BigDecimal Precision - Accurate decimal calculations (10 decimal places)
 *
 * ERROR HANDLING:
 * ✅ Division by Zero Protection - Prevents arithmetic errors
 * ✅ Invalid Input Handling - Graceful error recovery
 * ✅ User-Friendly Messages - Clear error dialogs
 *
 * TESTING:
 * ✅ Unit Tests - 10+ comprehensive tests for all operations
 * ✅ Edge Case Coverage - Testing boundary conditions
 *
 * ADDITIONAL FEATURES:
 * ✅ Calculation History - Track previous calculations
 * ✅ Mode Toggle - Switch between Basic and Scientific modes
 * ✅ Clear/Delete - C for clear all, backspace for delete last digit
 *
 * @author Csaba79coder
 * @version 1.0-SNAPSHOT
 * @since 2025
 */
public class CalculatorApplication {

    /**
     * Main entry point of the application.
     *
     * Initializes the MVC components:
     * 1. Model (Calculator) - Business logic and calculations
     * 2. View (CalculatorFrame) - User interface and components
     * 3. Controller (CalculatorController) - Event handling and coordination
     *
     * The application uses the Event Dispatch Thread (EDT) for thread safety.
     * System Look and Feel is applied for native OS appearance.
     *
     * @param args Command line arguments (not used)
     */
    static void main(String[] args) {
        // Set system look and feel for better native appearance
        // This makes the calculator look native on Windows, macOS, and Linux
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
            // Continue with default look and feel if system L&F fails
        }

        // Create and display the calculator on the Event Dispatch Thread (EDT)
        // This ensures thread safety for Swing components
        SwingUtilities.invokeLater(() -> {
            // Initialize Model - Contains all calculation logic
            // Features: BigDecimal precision, memory storage, scientific operations
            Calculator model = new Calculator();

            // Initialize View - Contains all UI components
            // Features: Modern dark theme, responsive buttons, keyboard support
            CalculatorFrame view = new CalculatorFrame();

            // Initialize Controller - Connects Model and View
            // Features: Event handling, keyboard shortcuts, operation coordination
            CalculatorController controller = new CalculatorController(model, view);

            // Display the calculator window
            view.setVisible(true);

            // Log successful startup
            System.out.println("✅ Calculator Application Started Successfully!");
            System.out.println("📊 Mode: Basic & Scientific");
            System.out.println("⌨️  Keyboard Support: Enabled");
            System.out.println("💾 Memory Functions: Available");
            System.out.println("📝 History Tracking: Enabled");
        });
    }
}