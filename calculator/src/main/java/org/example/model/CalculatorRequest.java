package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculatorRequest {
    @JsonProperty("operation")
    private String operation;

    @JsonProperty("a")
    private String operandA;

    @JsonProperty("b")
    private String operandB;

    @JsonProperty("requestId")
    private String requestId;

    public CalculatorRequest() {}

    public CalculatorRequest(String operation, String operandA, String operandB, String requestId) {
        this.operation = operation;
        this.operandA = operandA;
        this.operandB = operandB;
        this.requestId = requestId;
    }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getOperandA() { return operandA; }
    public void setOperandA(String operandA) { this.operandA = operandA; }

    public String getOperandB() { return operandB; }
    public void setOperandB(String operandB) { this.operandB = operandB; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
}

