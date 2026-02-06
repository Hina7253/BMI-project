package com.bmi.model;

public class BmiResponse {
    
    private Double bmi;
    private String category;
    private String healthMessage;
    private String healthAdvice;
    private Double weight;
    private Double height;
    private String colorCode;
    
    public BmiResponse() {
    }
    
    public BmiResponse(Double bmi, String category, String healthMessage, 
                       String healthAdvice, Double weight, Double height, String colorCode) {
        this.bmi = bmi;
        this.category = category;
        this.healthMessage = healthMessage;
        this.healthAdvice = healthAdvice;
        this.weight = weight;
        this.height = height;
        this.colorCode = colorCode;
    }
    
    // Getters and Setters
    public Double getBmi() {
        return bmi;
    }
    
    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getHealthMessage() {
        return healthMessage;
    }
    
    public void setHealthMessage(String healthMessage) {
        this.healthMessage = healthMessage;
    }
    
    public String getHealthAdvice() {
        return healthAdvice;
    }
    
    public void setHealthAdvice(String healthAdvice) {
        this.healthAdvice = healthAdvice;
    }
    
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
    
    public String getColorCode() {
        return colorCode;
    }
    
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
