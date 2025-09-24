package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);
    private static final int DEFAULT_SCALE = 10;

    public BigDecimal addition(BigDecimal a, BigDecimal b){
        String requestId = MDC.get("requestId");
        logger.info("Processing addition: {} + {} [Request ID: {}]", a, b, requestId);

        BigDecimal result = a.add(b);
        logger.info("Addition result: {} [Request ID: {}]", result, requestId);

        return result;
    }

    public BigDecimal subtraction(BigDecimal a, BigDecimal b){
        String requestId = MDC.get("requestId");
        logger.info("Processing subtraction: {} - {} [Request ID: {}]", a, b, requestId);

        BigDecimal result = a.subtract(b);
        logger.info("Subtraction result: {} [Request ID: {}]", result, requestId);

        return result;
    }

    public BigDecimal multiplication(BigDecimal a, BigDecimal b){
        String requestId = MDC.get("requestId");
        logger.info("Processing multiplication: {} * {} [Request ID: {}]", a, b, requestId);

        BigDecimal result = a.multiply(b);
        logger.info("Multiplication result: {} [Request ID: {}]", result, requestId);

        return result;
    }

    public BigDecimal division(BigDecimal a, BigDecimal b) {
        String requestId = MDC.get("requestId");
        logger.info("Processing division: {} / {} [RequestId: {}]", a, b, requestId);

        if (b.compareTo(BigDecimal.ZERO) == 0) {
            logger.error("Division by zero attempted [RequestId: {}]", requestId);
            throw new ArithmeticException("Division by zero is not allowed");
        }

        BigDecimal result = a.divide(b, DEFAULT_SCALE, RoundingMode.HALF_UP);
        logger.info("Division result: {} [RequestId: {}]", result, requestId);

        return result;
    }
}
