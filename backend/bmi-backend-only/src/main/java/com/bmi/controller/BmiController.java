package com.bmi.controller;

import com.bmi.model.BmiRequest;
import com.bmi.model.BmiResponse;
import com.bmi.service.BmiService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bmi")
@CrossOrigin(origins = "*") // Frontend se connect karne ke liye
public class BmiController {
    
    private final BmiService bmiService;
    
    public BmiController(BmiService bmiService) {
        this.bmiService = bmiService;
    }
    
    /**
     * POST /api/bmi/calculate
     * Body mein JSON data bhejo
     */
    @PostMapping("/calculate")
    public ResponseEntity<BmiResponse> calculateBmi(@Valid @RequestBody BmiRequest request) {
        BmiResponse response = bmiService.calculateBmi(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/bmi/calculate?weight=70&height=1.75&unit=metric
     * URL mein parameters bhejo
     */
    @GetMapping("/calculate")
    public ResponseEntity<BmiResponse> calculateBmiGet(
            @RequestParam Double weight,
            @RequestParam Double height,
            @RequestParam(required = false, defaultValue = "metric") String unit) {
        
        BmiRequest request = new BmiRequest(weight, height, unit);
        BmiResponse response = bmiService.calculateBmi(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/bmi/health
     * Server check karne ke liye
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "BMI Calculator API is running! 🚀");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(response);
    }
    
    /**
     * Validation errors handle karta hai
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    
    /**
     * General errors handle karta hai
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericError(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Something went wrong!");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
