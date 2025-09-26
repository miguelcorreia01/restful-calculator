package org.example.service;

import org.example.model.CalculatorRequest;
import org.example.model.CalculatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class CalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    @Autowired
    private KafkaTemplate<String, CalculatorRequest> requestTemplate;

    private final ConcurrentHashMap<String, CompletableFuture<CalculatorResponse>> pendingRequests = new ConcurrentHashMap<>();

    public CalculatorResponse processCalculation(CalculatorRequest request) {
        String requestId = request.getRequestId();
        MDC.put("requestId", requestId);

        try {
            logger.info(" Sending calculation request to calculator module [RequestId: {}]", requestId);

            CompletableFuture<CalculatorResponse> future = new CompletableFuture<>();
            pendingRequests.put(requestId, future);

            requestTemplate.send("calculation-requests", request);


            CalculatorResponse response = future.get(10, TimeUnit.SECONDS);

            logger.info("Received calculation response [RequestId: {}]", requestId);
            return response;

        } catch (Exception e) {
            pendingRequests.remove(requestId);
            String errorMsg = (e.getMessage() != null && !e.getMessage().isEmpty()) ? e.getMessage() : "Timeout or connection error (" + e.getClass().getSimpleName() + ")";
            logger.error("Error processing calculation request [RequestId: {}]: {}", requestId, errorMsg, e);
            return new CalculatorResponse("Internal server error: " + errorMsg, requestId);
        } finally {
            MDC.remove("requestId");
        }
    }

    public void handleCalculationResponse(CalculatorResponse response) {
        String requestId = response.getRequestId();
        MDC.put("requestId", requestId);

        try {
            logger.info("Received calculation response from calculator module [RequestId: {}]", requestId);

            CompletableFuture<CalculatorResponse> future = pendingRequests.remove(requestId);
            if (future != null) {
                future.complete(response);
                logger.info("Response delivered to waiting request [RequestId: {}]", requestId);
            } else {
                logger.warn("No pending request found for response [RequestId: {}]", requestId);
            }

        } finally {
            MDC.remove("requestId");
        }
    }
}