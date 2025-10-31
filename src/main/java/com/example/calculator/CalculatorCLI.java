package com.example.calculator;

/**
 * Entry point for the calculator command line interface.
 */
public final class CalculatorCLI {

    private static final String USAGE = String.join(System.lineSeparator(),
            "Usage: java -jar calculator-cli.jar <operation> <operand1> <operand2>",
            "Available operations: add, subtract, multiply, divide",
            "Example: java -jar calculator-cli.jar add 2 3");

    private CalculatorCLI() {
    }

    public static void main(String[] args) {
        if (args.length == 0 || wantsHelp(args)) {
            System.out.println(USAGE);
            return;
        }

        if (args.length != 3) {
            System.err.println("Error: expected exactly 3 arguments.");
            System.out.println(USAGE);
            System.exit(1);
        }

        String operation = args[0].toLowerCase();
        double left;
        double right;

        try {
            left = Double.parseDouble(args[1]);
            right = Double.parseDouble(args[2]);
        } catch (NumberFormatException ex) {
            System.err.println("Error: operands must be valid numbers.");
            System.exit(2);
            return;
        }

        Calculator calculator = new Calculator();
        double result;

        try {
            result = compute(operation, left, right, calculator);
        } catch (IllegalArgumentException ex) {
            System.err.println("Error: " + ex.getMessage());
            System.exit(3);
            return;
        }

        System.out.println("Result: " + result);
    }

    private static boolean wantsHelp(String[] args) {
        String first = args[0].toLowerCase();
        return "-h".equals(first) || "--help".equals(first) || "help".equals(first);
    }

    private static double compute(String op, double left, double right, Calculator calculator) {
        switch (op) {
            case "add":
                return calculator.add(left, right);
            case "subtract":
                return calculator.subtract(left, right);
            case "multiply":
                return calculator.multiply(left, right);
            case "divide":
                return calculator.divide(left, right);
            default:
                throw new IllegalArgumentException("Unknown operation '" + op + "'");
        }
    }
}
