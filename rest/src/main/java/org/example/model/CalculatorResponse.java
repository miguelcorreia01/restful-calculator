package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CalculatorResponse {

    @JsonProperty("result")
    private String result;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("error")
    private String error;


    public CalculatorResponse() {}


    public CalculatorResponse(BigDecimal result, String requestId) {
        this.result = result.toPlainString();
        this.requestId = requestId;
        this.success = true;
    }

    public CalculatorResponse(String error, String requestId) {
        this.error = error;
        this.requestId = requestId;
        this.success = false;
    }

    public CalculatorResponse(String result, String requestId, boolean success) {
        this.result = result;
        this.requestId = requestId;
        this.success = success;
    }

    public static CalculatorResponse success(String result, String requestId) {
        return new CalculatorResponse(result, requestId, true);
    }

    public static CalculatorResponse error(String error, String requestId) {
        CalculatorResponse response = new CalculatorResponse(null, requestId, false);
        response.setError(error);
        return response;
    }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}