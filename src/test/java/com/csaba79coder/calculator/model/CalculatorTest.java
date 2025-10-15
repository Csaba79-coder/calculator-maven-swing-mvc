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
 * <p>Test Coverage:</p>
 * <ul>
 *     <li>Initial state verification</li>
 *     <li>Digit input handling</li>
 *     <li>Basic operations (add, subtract, multiply, divide)</li>
 *     <li>Division by zero protection</li>
 *     <li>Decimal point handling</li>
 *     <li>Sign toggle functionality</li>
 *     <li>Percentage calculation</li>
 *     <li>Reset functionality</li>
 *     <li>Chained operations</li>
 *     <li>Complex calculations</li>
 *     <li>Multiple decimal points prevention</li>
 *     <li>Start new number flag behavior</li>
 * </ul>
 *
 * @author Csaba79coder
 * @version 1.0
 * @since 2025
 */
class CalculatorTest {

    /**
     * Calculator instance to be tested.
     */
    private Calculator calculator;

    /**
     * Setup method that runs before each test.
     * Creates a fresh calculator instance for each test to ensure test isolation.
     */
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    /**
     * Tests the initial state of the calculator.
     * Verifies that the calculator starts with:
     * <ul>
     *     <li>Display showing "0"</li>
     *     <li>Current value equal to BigDecimal.ZERO</li>
     * </ul>
     */
    @Test
    void testInitialState() {
        assertEquals("0", calculator.getDisplayValue());
        assertEquals(BigDecimal.ZERO, calculator.getCurrentValue());
    }

    /**
     * Tests appending digits to form numbers.
     * <p>Test sequence:</p>
     * <ol>
     *     <li>Append "5" → expects "5"</li>
     *     <li>Append "3" → expects "53"</li>
     * </ol>
     */
    @Test
    void testAppendDigit() {
        calculator.appendDigit("5");
        assertEquals("5", calculator.getDisplayValue());

        calculator.appendDigit("3");
        assertEquals("53", calculator.getDisplayValue());
    }

    /**
     * Tests addition operation.
     * <p>Calculation: 5 + 3 = 8</p>
     * Verifies both the returned result and display value.
     */
    @Test
    void testAddition() {
        calculator.appendDigit("5");
        calculator.setOperation(Operation.ADD);
        calculator.appendDigit("3");
        BigDecimal result = calculator.calculateResult();

        assertEquals(new BigDecimal("8"), result);
        assertEquals("8", calculator.getDisplayValue());
    }

    /**
     * Tests subtraction operation.
     * <p>Calculation: 10 - 3 = 7</p>
     * Verifies the returned result equals 7.
     */
    @Test
    void testSubtraction() {
        calculator.appendDigit("1");
        calculator.appendDigit("0");
        calculator.setOperation(Operation.SUBTRACT);
        calculator.appendDigit("3");
        BigDecimal result = calculator.calculateResult();

        assertEquals(new BigDecimal("7"), result);
    }

    /**
     * Tests multiplication operation.
     * <p>Calculation: 4 × 5 = 20</p>
     * Verifies the returned result equals 20.
     */
    @Test
    void testMultiplication() {
        calculator.appendDigit("4");
        calculator.setOperation(Operation.MULTIPLY);
        calculator.appendDigit("5");
        BigDecimal result = calculator.calculateResult();

        assertEquals(new BigDecimal("20"), result);
    }

    /**
     * Tests division operation.
     * <p>Calculation: 20 ÷ 4 = 5</p>
     * Verifies the display value shows "5" without trailing zeros.
     */
    @Test
    void testDivision() {
        calculator.appendDigit("2");
        calculator.appendDigit("0");
        calculator.setOperation(Operation.DIVIDE);
        calculator.appendDigit("4");
        calculator.calculateResult();

        assertEquals("5", calculator.getDisplayValue());
    }

    /**
     * Tests division by zero protection.
     * <p>Calculation: 5 ÷ 0</p>
     * Verifies that attempting to divide by zero throws an ArithmeticException.
     */
    @Test
    void testDivisionByZero() {
        calculator.appendDigit("5");
        calculator.setOperation(Operation.DIVIDE);
        calculator.appendDigit("0");

        assertThrows(ArithmeticException.class, () -> calculator.calculateResult());
    }

    /**
     * Tests decimal point handling.
     * <p>Input sequence: 3, ., 1, 4</p>
     * Verifies the display shows "3.14" with proper decimal point placement.
     */
    @Test
    void testDecimalPoint() {
        calculator.appendDigit("3");
        calculator.appendDecimalPoint();
        calculator.appendDigit("1");
        calculator.appendDigit("4");

        assertEquals("3.14", calculator.getDisplayValue());
    }

    /**
     * Tests sign toggle functionality.
     * <p>Test sequence:</p>
     * <ol>
     *     <li>Input: 5 → Display: "5"</li>
     *     <li>Toggle sign → Display: "-5"</li>
     *     <li>Toggle sign again → Display: "5"</li>
     * </ol>
     * Verifies that sign can be toggled between positive and negative.
     */
    @Test
    void testToggleSign() {
        calculator.appendDigit("5");
        calculator.toggleSign();

        assertEquals("-5", calculator.getDisplayValue());

        calculator.toggleSign();
        assertEquals("5", calculator.getDisplayValue());
    }

    /**
     * Tests percentage calculation.
     * <p>Calculation: 50% = 0.5</p>
     * Verifies that percentage divides the number by 100.
     */
    @Test
    void testPercentage() {
        calculator.appendDigit("5");
        calculator.appendDigit("0");
        calculator.calculatePercentage();

        assertEquals("0.5", calculator.getDisplayValue());
    }

    /**
     * Tests reset functionality.
     * <p>Test sequence:</p>
     * <ol>
     *     <li>Input: 5 + 3</li>
     *     <li>Call reset()</li>
     *     <li>Verify display shows "0" and no operation is set</li>
     * </ol>
     */
    @Test
    void testReset() {
        calculator.appendDigit("5");
        calculator.setOperation(Operation.ADD);
        calculator.appendDigit("3");
        calculator.reset();

        assertEquals("0", calculator.getDisplayValue());
        assertNull(calculator.getCurrentOperation());
    }

    /**
     * Tests chained operations.
     * <p>Calculation: 5 + 3 = 8, then × 2 = 16</p>
     * <p>Verifies that operations can be chained: when a new operation is set
     * before pressing equals, the previous operation is calculated first.</p>
     */
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

    /**
     * Tests complex multi-step calculation.
     * <p>Calculation: (10 + 5) × 2 - 5 = 25</p>
     * <p>Step-by-step:</p>
     * <ol>
     *     <li>10 + 5 = 15</li>
     *     <li>15 × 2 = 30</li>
     *     <li>30 - 5 = 25</li>
     * </ol>
     * Verifies correct handling of multiple chained operations.
     */
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

    /**
     * Tests prevention of multiple decimal points.
     * <p>Input sequence: 5, ., 5, . (second decimal point should be ignored)</p>
     * Verifies that attempting to add a second decimal point has no effect,
     * resulting in "5.5" instead of "5.5."
     */
    @Test
    void testMultipleDecimalPoints() {
        calculator.appendDigit("5");
        calculator.appendDecimalPoint();
        calculator.appendDigit("5");
        calculator.appendDecimalPoint(); // Should be ignored

        assertEquals("5.5", calculator.getDisplayValue());
    }

    /**
     * Tests the startNewNumber flag behavior.
     * <p>Test sequence:</p>
     * <ol>
     *     <li>Input: 5</li>
     *     <li>Set operation: ADD</li>
     *     <li>Verify startNewNumber flag is true</li>
     *     <li>Input: 3</li>
     *     <li>Verify display shows "3" (new number started)</li>
     * </ol>
     * Ensures that after setting an operation, the next digit starts a new number.
     */
    @Test
    void testStartNewNumber() {
        calculator.appendDigit("5");
        calculator.setOperation(Operation.ADD);

        assertTrue(calculator.isStartNewNumber());

        calculator.appendDigit("3");
        assertEquals("3", calculator.getDisplayValue());
    }
}
