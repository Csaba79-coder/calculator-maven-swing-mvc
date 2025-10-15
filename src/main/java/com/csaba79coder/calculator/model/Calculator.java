package com.csaba79coder.calculator.model;

import com.csaba79coder.calculator.value.Operation;
import com.csaba79coder.calculator.value.ScientificOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Advanced Calculator model class with comprehensive calculation logic.
 *
 * This class demonstrates professional software design:
 * ✅ BigDecimal for precise decimal calculations (10 decimal places)
 * ✅ Memory functions for storing and recalling values
 * ✅ Calculation history tracking (up to 50 entries)
 * ✅ Basic operations (add, subtract, multiply, divide)
 * ✅ Scientific operations (sin, cos, tan, sqrt, power, log)
 * ✅ Error handling and validation
 * ✅ Clean code with comprehensive documentation
 *
 * @author Csaba79coder
 * @version 1.0
 */
public class Calculator {

    // ========== CALCULATION STATE ==========
    /**
     * Current value being displayed (as string for proper decimal handling).
     * Keeps the decimal point visible even before entering digits after it.
     */
    private String displayString;

    /**
     * Parsed BigDecimal value for calculations.
     * Updated from displayString when needed for operations.
     */
    private BigDecimal currentValue;

    /**
     * Value stored when an operation is selected (e.g., after pressing +).
     */
    private BigDecimal storedValue;

    /**
     * The current operation to be performed (null if no operation selected).
     */
    private Operation currentOperation;

    /**
     * Flag indicating whether to start a new number on next digit input.
     */
    private boolean startNewNumber;

    // ========== MEMORY FUNCTIONS ==========
    /**
     * Memory storage for M+, M-, MR, MC calculator functions.
     * Allows users to store a value and recall it later.
     */
    private BigDecimal memoryValue;

    // ========== CALCULATION HISTORY ==========
    /**
     * List storing previous calculations for user reference.
     * Format: "5 + 3 = 8"
     */
    private List<String> calculationHistory;
    private static final int MAX_HISTORY_SIZE = 50;

    // ========== CONSTANTS ==========
    /**
     * Precision for all BigDecimal operations.
     */
    private static final int PRECISION = 10;

    /**
     * Rounding mode for division operations.
     */
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * Constructor - Initializes calculator with default values.
     * Sets all values to zero and prepares for first input.
     */
    public Calculator() {
        this.memoryValue = BigDecimal.ZERO;
        this.calculationHistory = new ArrayList<>();
        reset();
    }

    // ========== RESET & INITIALIZATION ==========

    /**
     * Resets the calculator to initial state.
     * Clears current calculation but preserves memory and history.
     * Called when user presses C (Clear) button.
     */
    public void reset() {
        this.displayString = "0";
        this.currentValue = BigDecimal.ZERO;
        this.storedValue = BigDecimal.ZERO;
        this.currentOperation = null;
        this.startNewNumber = true;
    }

    /**
     * Complete reset including memory and history.
     * Called for full calculator reset.
     */
    public void fullReset() {
        reset();
        this.memoryValue = BigDecimal.ZERO;
        this.calculationHistory.clear();
    }

    // ========== DIGIT INPUT ==========

    /**
     * Appends a digit to the current number.
     * If starting a new number, replaces current value.
     * Otherwise, concatenates the digit to existing value.
     *
     * Example:
     * - Current: "5", append "3" → "53"
     * - After operation, append "3" → "3" (new number)
     *
     * @param digit The digit to append (0-9)
     */
    public void appendDigit(String digit) {
        if (startNewNumber) {
            displayString = digit;
            startNewNumber = false;
        } else {
            displayString += digit;
        }
        updateCurrentValue();
    }

    /**
     * Adds a decimal point to the current number.
     * Only adds if decimal point doesn't already exist.
     * If starting new number, begins with "0."
     *
     * Example:
     * - Current: "5", add decimal → "5."
     * - Current: "5.2", add decimal → "5.2" (no change)
     */
    public void appendDecimalPoint() {
        if (startNewNumber) {
            displayString = "0.";
            startNewNumber = false;
        } else if (!displayString.contains(".")) {
            displayString += ".";
        }
        // Don't update currentValue yet - keep the decimal point in string
    }

    /**
     * Updates currentValue from displayString.
     * Parses the string to BigDecimal, handling trailing decimal points.
     */
    private void updateCurrentValue() {
        try {
            // Remove trailing decimal point for parsing
            String parseString = displayString.endsWith(".") ?
                    displayString.substring(0, displayString.length() - 1) : displayString;
            currentValue = new BigDecimal(parseString);
        } catch (NumberFormatException e) {
            currentValue = BigDecimal.ZERO;
        }
    }

    // ========== BASIC OPERATIONS ==========

    /**
     * Sets the operation to be performed.
     * If there's a pending operation, calculates it first (chaining).
     * Stores current value for the operation.
     *
     * Example chaining:
     * - Input: 5 + 3 + → Calculates 5+3=8, stores 8, waits for next number
     *
     * @param operation The operation to perform (ADD, SUBTRACT, etc.)
     */
    public void setOperation(Operation operation) {
        // Update current value before storing
        updateCurrentValue();

        // If there's already an operation and user entered a new number, calculate first
        if (currentOperation != null && !startNewNumber) {
            calculateResult();
        }

        storedValue = currentValue;
        currentOperation = operation;
        startNewNumber = true;
    }

    /**
     * Calculates and returns the result of the current operation.
     * Performs the operation between storedValue and currentValue.
     * Adds result to calculation history.
     *
     * Operations:
     * - ADD: storedValue + currentValue
     * - SUBTRACT: storedValue - currentValue
     * - MULTIPLY: storedValue × currentValue
     * - DIVIDE: storedValue ÷ currentValue (with zero check)
     *
     * @return The calculation result
     * @throws ArithmeticException if dividing by zero
     */
    public BigDecimal calculateResult() {
        // Make sure current value is updated
        updateCurrentValue();

        if (currentOperation == null) {
            return currentValue;
        }

        BigDecimal result;
        String calculation;

        try {
            switch (currentOperation) {
                case ADD:
                    result = storedValue.add(currentValue);
                    calculation = formatHistory(storedValue, "+", currentValue, result);
                    break;

                case SUBTRACT:
                    result = storedValue.subtract(currentValue);
                    calculation = formatHistory(storedValue, "-", currentValue, result);
                    break;

                case MULTIPLY:
                    result = storedValue.multiply(currentValue);
                    calculation = formatHistory(storedValue, "×", currentValue, result);
                    break;

                case DIVIDE:
                    // Prevent division by zero
                    if (currentValue.compareTo(BigDecimal.ZERO) == 0) {
                        throw new ArithmeticException("Cannot divide by zero");
                    }
                    result = storedValue.divide(currentValue, PRECISION, ROUNDING_MODE);
                    calculation = formatHistory(storedValue, "÷", currentValue, result);
                    break;

                default:
                    result = currentValue;
                    calculation = null;
            }

            // Add to history if valid calculation
            if (calculation != null) {
                addToHistory(calculation);
            }

            // Update state for next operation
            currentValue = result;
            displayString = formatValue(result);
            storedValue = BigDecimal.ZERO;
            currentOperation = null;
            startNewNumber = true;

            return result;

        } catch (ArithmeticException e) {
            reset();
            throw e;
        }
    }

    // ========== SCIENTIFIC OPERATIONS ==========

    /**
     * Performs scientific operation on the current value.
     * Uses Math library for trigonometric and logarithmic functions.
     *
     * @param operation The scientific operation to perform
     * @return The result of the operation
     * @throws ArithmeticException if operation is invalid (e.g., sqrt of negative)
     */
    public BigDecimal performScientificOperation(ScientificOperation operation) {
        updateCurrentValue();
        double value = currentValue.doubleValue();
        double result;
        String calculation;

        try {
            switch (operation) {
                case SIN:
                    result = Math.sin(value);
                    calculation = "sin(" + formatValue(currentValue) + ") = " + result;
                    break;
                case COS:
                    result = Math.cos(value);
                    calculation = "cos(" + formatValue(currentValue) + ") = " + result;
                    break;
                case TAN:
                    result = Math.tan(value);
                    calculation = "tan(" + formatValue(currentValue) + ") = " + result;
                    break;
                case SQUARE:
                    result = Math.pow(value, 2);
                    calculation = formatValue(currentValue) + "² = " + result;
                    break;
                case SQRT:
                    if (value < 0) {
                        throw new ArithmeticException("Cannot calculate square root of negative number");
                    }
                    result = Math.sqrt(value);
                    calculation = "√" + formatValue(currentValue) + " = " + result;
                    break;
                case LOG:
                    if (value <= 0) {
                        throw new ArithmeticException("Cannot calculate logarithm of non-positive number");
                    }
                    result = Math.log10(value);
                    calculation = "log(" + formatValue(currentValue) + ") = " + result;
                    break;
                case LN:
                    if (value <= 0) {
                        throw new ArithmeticException("Cannot calculate natural logarithm of non-positive number");
                    }
                    result = Math.log(value);
                    calculation = "ln(" + formatValue(currentValue) + ") = " + result;
                    break;
                case PI:
                    result = Math.PI;
                    calculation = "π = " + result;
                    break;
                case E:
                    result = Math.E;
                    calculation = "e = " + result;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown scientific operation");
            }

            currentValue = BigDecimal.valueOf(result);
            displayString = formatValue(currentValue);
            addToHistory(calculation);
            startNewNumber = true;

            return currentValue;

        } catch (ArithmeticException e) {
            reset();
            throw e;
        }
    }

    /**
     * Calculates power: currentValue ^ exponent.
     *
     * @param exponent The power to raise to
     * @return Result of the power operation
     */
    public BigDecimal calculatePower(BigDecimal exponent) {
        updateCurrentValue();
        double base = currentValue.doubleValue();
        double exp = exponent.doubleValue();
        double result = Math.pow(base, exp);

        String calculation = formatValue(currentValue) + "^" + formatValue(exponent) + " = " + result;
        addToHistory(calculation);

        currentValue = BigDecimal.valueOf(result);
        displayString = formatValue(currentValue);
        startNewNumber = true;
        return currentValue;
    }

    // ========== SPECIAL OPERATIONS ==========

    /**
     * Changes the sign of the current number (positive ↔ negative).
     * Example: 5 → -5, -3.14 → 3.14
     */
    public void toggleSign() {
        updateCurrentValue();
        currentValue = currentValue.negate();
        displayString = formatValue(currentValue);
    }

    /**
     * Converts current number to percentage (divides by 100).
     * Example: 50 → 0.5, 125 → 1.25
     */
    public void calculatePercentage() {
        updateCurrentValue();
        currentValue = currentValue.divide(new BigDecimal("100"), PRECISION, ROUNDING_MODE);
        displayString = formatValue(currentValue);
        String calculation = formatValue(currentValue.multiply(new BigDecimal("100"))) + "% = " + formatValue(currentValue);
        addToHistory(calculation);
    }

    // ========== MEMORY FUNCTIONS ==========

    /**
     * Stores current value in memory (Memory Store).
     * Equivalent to pressing MS button on physical calculator.
     */
    public void memoryStore() {
        updateCurrentValue();
        memoryValue = currentValue;
    }

    /**
     * Recalls value from memory (Memory Recall).
     * Equivalent to pressing MR button.
     *
     * @return The value stored in memory
     */
    public BigDecimal memoryRecall() {
        currentValue = memoryValue;
        displayString = formatValue(memoryValue);
        startNewNumber = true;
        return memoryValue;
    }

    /**
     * Adds current value to memory (Memory Add).
     * Equivalent to pressing M+ button.
     */
    public void memoryAdd() {
        updateCurrentValue();
        memoryValue = memoryValue.add(currentValue);
    }

    /**
     * Subtracts current value from memory (Memory Subtract).
     * Equivalent to pressing M- button.
     */
    public void memorySubtract() {
        updateCurrentValue();
        memoryValue = memoryValue.subtract(currentValue);
    }

    /**
     * Clears memory (Memory Clear).
     * Equivalent to pressing MC button.
     */
    public void memoryClear() {
        memoryValue = BigDecimal.ZERO;
    }

    /**
     * Checks if memory contains a non-zero value.
     * Used to display memory indicator in UI.
     *
     * @return true if memory is not empty
     */
    public boolean hasMemory() {
        return memoryValue.compareTo(BigDecimal.ZERO) != 0;
    }

    // ========== HISTORY MANAGEMENT ==========

    /**
     * Adds a calculation to history.
     * Maintains maximum history size by removing oldest entries.
     *
     * @param calculation The calculation string to add
     */
    private void addToHistory(String calculation) {
        calculationHistory.add(0, calculation); // Add to beginning (most recent first)

        // Remove oldest entry if exceeding max size
        if (calculationHistory.size() > MAX_HISTORY_SIZE) {
            calculationHistory.remove(calculationHistory.size() - 1);
        }
    }

    /**
     * Gets the calculation history.
     *
     * @return List of previous calculations, most recent first
     */
    public List<String> getHistory() {
        return new ArrayList<>(calculationHistory); // Return copy for safety
    }

    /**
     * Clears all calculation history.
     */
    public void clearHistory() {
        calculationHistory.clear();
    }

    // ========== DISPLAY & FORMATTING ==========

    /**
     * Gets the current display value as a formatted string.
     * Returns the display string which preserves decimal points during input.
     *
     * @return Formatted string for display
     */
    public String getDisplayValue() {
        return displayString;
    }

    /**
     * Formats a history entry for display.
     *
     * @param operand1 First operand
     * @param operator Operation symbol
     * @param operand2 Second operand
     * @param result Result of operation
     * @return Formatted string like "5 + 3 = 8"
     */
    private String formatHistory(BigDecimal operand1, String operator, BigDecimal operand2, BigDecimal result) {
        return formatValue(operand1) + " " + operator + " " + formatValue(operand2) + " = " + formatValue(result);
    }

    /*
    Test
    private String formatValue(BigDecimal value) {
        System.out.println("=== FORMATVALUE CALLED ===");
        System.out.println("Input: [" + value + "]");
        System.out.println("toPlainString: [" + value.toPlainString() + "]");

        String str = value.toPlainString();

        // Remove trailing zeros after decimal point
        if (str.contains(".")) {
            System.out.println("Contains dot, before regex: [" + str + "]");
            str = str.replaceAll("0+$", "");  // Remove trailing zeros
            System.out.println("After 0+ regex: [" + str + "]");
            str = str.replaceAll("\\.$", ""); // Remove trailing decimal point
            System.out.println("After \\. regex: [" + str + "]");
        }

        System.out.println("Output: [" + str + "]");
        System.out.println("======================");
        return str;
    }*/

    /**
     * Formats a BigDecimal value for display.
     * Removes unnecessary trailing zeros and decimal points.
     *
     * @param value The value to format
     * @return Formatted string
     */
    /**
     * Formats a BigDecimal value for display.
     * Removes unnecessary trailing zeros and decimal points.
     *
     * @param value The value to format
     * @return Formatted string
     */
    private String formatValue(BigDecimal value) {
        String str = value.toPlainString();

        // Remove trailing zeros after decimal point
        if (str.contains(".")) {
            str = str.replaceAll("0+$", "");  // Remove trailing zeros
            str = str.replaceAll("\\.$", ""); // Remove trailing decimal point
        }

        return str;
    }

    // ========== GETTERS & SETTERS ==========

    /**
     * Gets the current value as BigDecimal.
     * Updates from display string before returning.
     *
     * @return Current calculator value
     */
    public BigDecimal getCurrentValue() {
        updateCurrentValue();
        return currentValue;
    }

    /**
     * Sets the current value directly.
     * Used for special operations or direct value input.
     *
     * @param value The value to set
     */
    public void setCurrentValue(BigDecimal value) {
        this.currentValue = value;
        this.displayString = formatValue(value);
        this.startNewNumber = true;
    }

    /**
     * Checks if calculator is ready to start a new number.
     *
     * @return true if next digit starts a new number
     */
    public boolean isStartNewNumber() {
        return startNewNumber;
    }

    /**
     * Gets the current operation.
     *
     * @return Current operation or null if none
     */
    public Operation getCurrentOperation() {
        return currentOperation;
    }

    /**
     * Gets the memory value.
     *
     * @return Value stored in memory
     */
    public BigDecimal getMemoryValue() {
        return memoryValue;
    }
}
