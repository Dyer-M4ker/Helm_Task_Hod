package com.example.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void addsTwoNumbers() {
        assertEquals(7.0, calculator.add(3.0, 4.0));
    }

    @Test
    void subtractsTwoNumbers() {
        assertEquals(1.5, calculator.subtract(3.0, 1.5));
    }

    @Test
    void multipliesTwoNumbers() {
        assertEquals(12.0, calculator.multiply(3.0, 4.0));
    }

    @Test
    void dividesTwoNumbers() {
        assertEquals(2.5, calculator.divide(5.0, 2.0));
    }

    @Test
    void divisionByZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(1.0, 0.0));
    }
}
