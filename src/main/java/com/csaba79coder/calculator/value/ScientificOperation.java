package com.csaba79coder.calculator.value;

/**
 * Enumeration of scientific mathematical operations.
 * These operations extend the basic calculator with advanced mathematical functions.
 *
 * Features:
 * - Trigonometric functions (sin, cos, tan)
 * - Power and root functions (square, sqrt, power)
 * - Logarithmic functions (log, ln)
 * - Mathematical constants (pi, e)
 *
 * @author Csaba79coder
 * @version 1.0
 */
public enum ScientificOperation {
    // Trigonometric operations
    SIN("sin", "Sine - calculates the sine of an angle in radians"),
    COS("cos", "Cosine - calculates the cosine of an angle in radians"),
    TAN("tan", "Tangent - calculates the tangent of an angle in radians"),

    // Power and root operations
    SQUARE("x²", "Square - multiplies the number by itself"),
    SQRT("√", "Square root - finds the number that when squared equals the input"),
    POWER("xʸ", "Power - raises x to the power of y"),

    // Logarithmic operations
    LOG("log", "Logarithm base 10"),
    LN("ln", "Natural logarithm (base e)"),

    // Mathematical constants
    PI("π", "Pi constant (3.14159...)"),
    E("e", "Euler's number (2.71828...)");

    private final String symbol;
    private final String description;

    /**
     * Constructor for ScientificOperation enum.
     * @param symbol The display symbol for the operation
     * @param description Description of what the operation does
     */
    ScientificOperation(String symbol, String description) {
        this.symbol = symbol;
        this.description = description;
    }

    /**
     * Gets the symbol of the operation.
     * @return The operation symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the description of the operation.
     * @return The operation description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets operation from symbol.
     * @param symbol The symbol to look up
     * @return The matching operation or null if not found
     */
    public static ScientificOperation fromSymbol(String symbol) {
        for (ScientificOperation op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
