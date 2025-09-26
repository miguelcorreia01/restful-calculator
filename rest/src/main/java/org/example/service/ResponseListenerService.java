package org.example.service;

import org.example.model.CalculatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ResponseListenerService {

    private static final Logger logger = LoggerFactory.getLogger(ResponseListenerService.class);

    @Autowired
    private CalculatorService calculatorService;

    @KafkaListener(topics = "calculation-responses", groupId = "rest-group")
    public void handleCalculationResponse(CalculatorResponse response) {
        String requestId = response.getRequestId();

        logger.debug("Detailed response received: requestId={}, success={}, result={}, error={}",
                requestId,
                response.isSuccess(),
                response.getResult(),
                response.getError()
        );


        if (requestId == null) {
            logger.error("CRITICAL: Received CalculatorResponse with NULL requestId - skipping processing");
            return;
        }

        MDC.put("requestId", requestId);

        try {
            logger.info("Received calculation response from calculator module [RequestId: {}]", requestId);

            calculatorService.handleCalculationResponse(response);

        } finally {
            MDC.remove("requestId");
        }
    }
}