package org.example.consumer;

import jakarta.annotation.PostConstruct;
import org.example.model.CalculatorRequest;
import org.example.model.CalculatorResponse;
import org.example.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculatorConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorConsumer.class);

    @Autowired
    private CalculatorService calculatorService;

    @PostConstruct
    public void init() {
        logger.info("CalculatorConsumer initialized and ready to receive messages");
    }


    @Autowired
    private KafkaTemplate<String, CalculatorResponse> responseTemplate;

    @KafkaListener(topics = "calculation-requests", groupId = "calculator-group")
    public void handleCalculationRequest(CalculatorRequest request) {

        MDC.put("requestId", request.getRequestId());

        try {
            logger.info("Received calculation request: {} {} {} [RequestId: {}]",
                    request.getOperandA(), request.getOperation(), request.getOperandB(), request.getRequestId());

            BigDecimal a = new BigDecimal(request.getOperandA());
            BigDecimal b = new BigDecimal(request.getOperandB());

            BigDecimal result = switch (request.getOperation().toLowerCase()) {
                case "sum", "add" -> calculatorService.addition(a, b);
                case "subtract", "sub" -> calculatorService.subtraction(a, b);
                case "multiply", "mul" -> calculatorService.multiplication(a, b);
                case "divide", "div" -> calculatorService.division(a, b);
                default -> throw new IllegalArgumentException("Unsupported operation: " + request.getOperation());
            };

            CalculatorResponse response = new CalculatorResponse(result, request.getRequestId());
            responseTemplate.send("calculation-responses", response);

            logger.info("Calculation completed successfully [RequestId: {}]", request.getRequestId());

        } catch (Exception e) {
            logger.error("Error processing calculation request [RequestId: {}]: {}",
                    request.getRequestId(), e.getMessage(), e);

            CalculatorResponse errorResponse = new CalculatorResponse(e.getMessage(), request.getRequestId());
            responseTemplate.send("calculation-responses", errorResponse);
        } finally {
            MDC.remove("requestId");
        }
    }
}

