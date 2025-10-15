package com.csaba79coder.calculator.model;

import com.csaba79coder.calculator.value.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Calculator model.
 * Tests all basic operations, special functions, and error handling.
 *
 * Test Coverage:
 * - Initial state
 * - Digit input
 * - Basic operations (add, subtract, multiply, divide)
 * - Division by zero protection
 * - Decimal point handling
 * - Sign toggle
 * - Percentage calculation
 * - Reset functionality
 * - Chained operations
 * - Complex calculations
 *
 * @author Csaba79coder
 * @version 1.0
 */
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testInitialState() {
        assertEquals("0", calculator.getDisplayValue());
        assertEquals(BigDecimal.ZERO, calculator.getCurrentValue());
    }

    @Test
    void testAppendDigit() {
        calculator.appendDigit("5");
        assertEquals("5", calculator.getDisplayValue());

        calculator.appendDigit("3");
        assertEquals("53", calculator.getDisplayValue());
    }

    @Test
    void testAddition() {
        calculator.appendDigit("5");
        calculator.setOperation(Operation.ADD);
        calculator.appendDigit("3");
        BigDecimal result = calculator.calculateResult();

        assertEquals(new BigDecimal("8"), result);
        assertEquals("8", calculator.getDisplayValue());
    }

    @Test
    void testSubtraction() {
        calculator.appendDigit("1");
        calculator.appendDigit("0");
        calculator.setOperation(Operation.SUBTRACT);
        calculator.appendDigit("3");
        BigDecimal result = calculator.calculateResult();

        assertEquals(new BigDecimal("7"), result);
    }

    @Test
    void testMultiplication() {
        calculator.appendDigit("4");
        calculator.setOperation(Operation.MULTIPLY);
        calculator.appendDigit("5");
        BigDecimal result = calculator.calculateResult();

        assertEquals(new BigDecimal("20"), result);
    }

    @Test
    void testDivision() {
        calculator.appendDigit("2");
        calculator.appendDigit("0");
        calculator.setOperation(Operation.DIVIDE);
        calculator.appendDigit("4");
        BigDecimal result = calculator.calculateResult();

        assertEquals("5", calculator.getDisplayValue());
    }

    @Test
    void testDivisionByZero() {
        calculator.appendDigit("5");
        calculator.setOperation(Operation.DIVIDE);
        calculator.appendDigit("0"); // Fixed: Added the zero operand

        assertThrows(ArithmeticException.class, () -> calculator.calculateResult());
    }

    @Test
    void testDecimalPoint() {
        calculator.appendDigit("3");
        calculator.appendDecimalPoint();
        calculator.appendDigit("1");
        calculator.appendDigit("4");

        assertEquals("3.14", calculator.getDisplayValue());
    }

    @Test
    void testToggleSign() {
        calculator.appendDigit("5");
        calculator.toggleSign();

        assertEquals("-5", calculator.getDisplayValue());

        calculator.toggleSign();
        assertEquals("5", calculator.getDisplayValue());
    }

    @Test
    void testPercentage() {
        calculator.appendDigit("5");
        calculator.appendDigit("0");
        calculator.calculatePercentage();

        assertEquals("0.5", calculator.getDisplayValue());
    }

    @Test
    void testReset() {
        calculator.appendDigit("5");
        calculator.setOperation(Operation.ADD);
        calculator.appendDigit("3");
        calculator.reset();

        assertEquals("0", calculator.getDisplayValue());
        assertNull(calculator.getCurrentOperation());
    }

    @Test
    void testChainedOperations() {
        calculator.appendDigit("5");
        calculator.setOperation(Operation.ADD);
        calculator.appendDigit("3");
        calculator.setOperation(Operation.MULTIPLY);
        calculator.appendDigit("2");
        BigDecimal result = calculator.calculateResult();

        assertEquals(new BigDecimal("16"), result);
    }

    @Test
    void testComplexCalculation() {
        calculator.appendDigit("1");
        calculator.appendDigit("0");
        calculator.setOperation(Operation.ADD);
        calculator.appendDigit("5");
        calculator.setOperation(Operation.MULTIPLY);
        calculator.appendDigit("2");
        calculator.setOperation(Operation.SUBTRACT);
        calculator.appendDigit("5");
        BigDecimal result = calculator.calculateResult();

        assertEquals(new BigDecimal("25"), result);
    }

    @Test
    void testMultipleDecimalPoints() {
        calculator.appendDigit("5");
        calculator.appendDecimalPoint();
        calculator.appendDigit("5");
        calculator.appendDecimalPoint(); // Should be ignored

        assertEquals("5.5", calculator.getDisplayValue());
    }

    @Test
    void testStartNewNumber() {
        calculator.appendDigit("5");
        calculator.setOperation(Operation.ADD);

        assertTrue(calculator.isStartNewNumber());

        calculator.appendDigit("3");
        assertEquals("3", calculator.getDisplayValue());
    }
}
