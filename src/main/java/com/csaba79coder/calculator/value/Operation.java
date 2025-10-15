package com.csaba79coder.calculator.value;

/**
 * Enumeration of basic mathematical operations supported by the calculator.
 * These are the four fundamental arithmetic operations.
 *
 * @author Csaba79coder
 * @version 1.0
 */
public enum Operation {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("×"),
    DIVIDE("÷");

    private final String symbol;

    /**
     * Constructor for Operation enum.
     * @param symbol The display symbol for the operation
     */
    Operation(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the symbol of the operation.
     * @return The operation symbol (+, -, ×, ÷)
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets operation from symbol.
     * @param symbol The operation symbol to look up
     * @return The matching Operation or null if not found
     */
    public static Operation fromSymbol(String symbol) {
        for (Operation op : values()) {
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
