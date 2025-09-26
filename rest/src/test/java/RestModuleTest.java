import org.example.model.CalculatorRequest;
import org.example.model.CalculatorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestModuleTest {

    @BeforeEach
    void setUp() {
        MDC.put("requestId", "test-request-id");
    }

    @Test
    void testCalculatorRequestModel() {
        // Test the request model
        CalculatorRequest request = new CalculatorRequest("sum", "5.5", "3.2", "test-id");

        assertEquals("sum", request.getOperation());
        assertEquals("5.5", request.getOperandA());
        assertEquals("3.2", request.getOperandB());
        assertEquals("test-id", request.getRequestId());
    }

    @Test
    void testCalculatorResponseModel() {
        CalculatorResponse successResponse = CalculatorResponse.success("8.7", "test-id");

        assertEquals("8.7", successResponse.getResult());
        assertEquals("test-id", successResponse.getRequestId());
        assertTrue(successResponse.isSuccess());
        assertNull(successResponse.getError());

        // Test error response
        CalculatorResponse errorResponse = CalculatorResponse.error("Error message", "test-id");
        assertEquals("Error message", errorResponse.getError());
        assertEquals("test-id", errorResponse.getRequestId());
        assertFalse(errorResponse.isSuccess());
    }

    // Test that request IDs are properly generated
    @Test
    void testUUIDGeneration() {

        String requestId1 = UUID.randomUUID().toString();
        String requestId2 = UUID.randomUUID().toString();

        assertNotNull(requestId1);
        assertNotNull(requestId2);
        assertNotEquals(requestId1, requestId2);
        assertFalse(requestId1.isEmpty());
        assertFalse(requestId2.isEmpty());
    }

    // Simple integration test
    @Test
    void testModuleBasicIntegration() {
        CalculatorRequest request = new CalculatorRequest("sum", "5.5", "3.2", UUID.randomUUID().toString());
        assertNotNull(request.getRequestId());

        CalculatorResponse response = CalculatorResponse.success("8.7", request.getRequestId());
        assertEquals(request.getRequestId(), response.getRequestId());
        assertEquals("8.7", response.getResult());
        assertTrue(response.isSuccess());
    }
}