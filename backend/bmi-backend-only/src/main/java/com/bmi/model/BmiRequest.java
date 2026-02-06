package com.bmi.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BmiRequest {
    
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;
    
    @NotNull(message = "Height is required")
    @Positive(message = "Height must be positive")
    private Double height;
    
    private String unit; // "metric" or "imperial"
    
    public BmiRequest() {
    }
    
    public BmiRequest(Double weight, Double height, String unit) {
        this.weight = weight;
        this.height = height;
        this.unit = unit;
    }
    
    // Getters and Setters
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public Double getHeight() {
        return height;
    }
    
    public void setHeight(Double height) {
        this.height = height;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
