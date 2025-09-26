package org.example.controller;

import org.example.model.CalculatorRequest;
import org.example.model.CalculatorResponse;
import org.example.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping(value = {"/sum", "/add"})
    public ResponseEntity<CalculatorResponse> sum(
            @RequestParam String a,
            @RequestParam String b) {

        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);

        try {
            logger.info("Received sum request: {} + {} [RequestId: {}]", a, b, requestId);

            CalculatorRequest request = new CalculatorRequest("sum", a, b, requestId);
            CalculatorResponse response = calculatorService.processCalculation(request);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Request-ID", requestId);

            logger.info("Sum request completed [RequestId: {}]", requestId);

            return ResponseEntity.ok().headers(headers).body(response);

        } finally {
            MDC.remove("requestId");
        }
    }

    @GetMapping(value = {"/subtract" , "/sub"})
    public ResponseEntity<CalculatorResponse> subtract(
            @RequestParam String a,
            @RequestParam String b) {

        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);

        try {
            logger.info("Received subtract request: {} - {} [RequestId: {}]", a, b, requestId);

            CalculatorRequest request = new CalculatorRequest("subtract", a, b, requestId);
            CalculatorResponse response = calculatorService.processCalculation(request);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Request-ID", requestId);

            logger.info("Subtract request completed [RequestId: {}]", requestId);

            return ResponseEntity.ok().headers(headers).body(response);

        } finally {
            MDC.remove("requestId");
        }
    }

    @GetMapping(value= {"/multiply" , "/mul"})
    public ResponseEntity<CalculatorResponse> multiply(
            @RequestParam String a,
            @RequestParam String b) {

        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);

        try {
            logger.info("Received multiply request: {} * {} [RequestId: {}]", a, b, requestId);

            CalculatorRequest request = new CalculatorRequest("multiply", a, b, requestId);
            CalculatorResponse response = calculatorService.processCalculation(request);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Request-ID", requestId);

            logger.info("Multiply request completed [RequestId: {}]", requestId);

            return ResponseEntity.ok().headers(headers).body(response);

        } finally {
            MDC.remove("requestId");
        }
    }

    @GetMapping(value = {"/divide", "/div"})
    public ResponseEntity<CalculatorResponse> divide(
            @RequestParam String a,
            @RequestParam String b) {

        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);

        try {
            logger.info("Received divide request: {} / {} [RequestId: {}]", a, b, requestId);

            CalculatorRequest request = new CalculatorRequest("divide", a, b, requestId);
            CalculatorResponse response = calculatorService.processCalculation(request);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Request-ID", requestId);

            logger.info("Divide request completed [RequestId: {}]", requestId);

            return ResponseEntity.ok().headers(headers).body(response);

        } finally {
            MDC.remove("requestId");
        }
    }
}