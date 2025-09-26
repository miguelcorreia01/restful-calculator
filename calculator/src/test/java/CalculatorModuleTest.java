import org.example.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorModuleTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
        MDC.put("requestId", "test-request-id");
    }

    @Test
    void testAllCalculatorOperations() {

        BigDecimal a = new BigDecimal("10");
        BigDecimal b = new BigDecimal("3");

        // Test Addition
        BigDecimal sum = calculatorService.addition(a, b);
        assertEquals(new BigDecimal("13"), sum);

        // Test Subtraction
        BigDecimal diff = calculatorService.subtraction(a, b);
        assertEquals(new BigDecimal("7"), diff);

        // Test Multiplication
        BigDecimal product = calculatorService.multiplication(a, b);
        assertEquals(new BigDecimal("30"), product);

        // Test Division
        BigDecimal quotient = calculatorService.division(a, b);
        assertEquals(new BigDecimal("3.3333333333"), quotient);

        // Test Division by Zero
        assertThrows(ArithmeticException.class, () ->
                calculatorService.division(a, BigDecimal.ZERO)
        );
    }

    @Test
    void testArbitraryPrecision() {

        // Test with many decimal places
        BigDecimal longDecimal = new BigDecimal("3.141592653589793");
        BigDecimal two = new BigDecimal("2");

        BigDecimal multiplicationResult = calculatorService.multiplication(longDecimal, two);
        assertEquals(new BigDecimal("6.283185307179586"), multiplicationResult);

        // Test big precise numbers
        BigDecimal largeWithDecimals = new BigDecimal("1234567.890123456");
        BigDecimal smallPlus = new BigDecimal("0.000000012");

        BigDecimal additionResult = calculatorService.addition(largeWithDecimals, smallPlus);
        assertEquals(new BigDecimal("1234567.890123468"), additionResult);
    }

    @Test
    void testServiceHandlesBigNumbers() {

        BigDecimal large = new BigDecimal("1000000000000000000000");
        BigDecimal small = new BigDecimal("2");

        assertDoesNotThrow(() -> {
            BigDecimal result = calculatorService.multiplication(large, small);
            assertNotNull(result);
        });
    }
}