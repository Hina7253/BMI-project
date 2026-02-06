package com.bmi.service;

import com.bmi.model.BmiRequest;
import com.bmi.model.BmiResponse;
import org.springframework.stereotype.Service;

@Service
public class BmiService {
    
    public BmiResponse calculateBmi(BmiRequest request) {
        double weight = request.getWeight();
        double height = request.getHeight();
        String unit = request.getUnit();
        
        // Convert imperial to metric if needed
        if (unit != null && unit.equalsIgnoreCase("imperial")) {
            weight = weight * 0.453592;  // pounds to kg
            height = height * 0.0254;    // inches to meters
        }
        
        // Calculate BMI = weight (kg) / height (m)²
        double bmi = weight / (height * height);
        bmi = Math.round(bmi * 100.0) / 100.0; // Round to 2 decimals
        
        // Determine category
        String category = getCategory(bmi);
        String healthMessage = getHealthMessage(category);
        String healthAdvice = getHealthAdvice(category);
        String colorCode = getColorCode(category);
        
        return new BmiResponse(bmi, category, healthMessage, healthAdvice, 
                              weight, height, colorCode);
    }
    
    private String getCategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "Normal weight";
        } else if (bmi >= 25 && bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
    
    private String getHealthMessage(String category) {
        return switch (category) {
            case "Underweight" -> "You may need to gain weight. Consult with a healthcare provider.";
            case "Normal weight" -> "You have a healthy weight. Keep up the good work!";
            case "Overweight" -> "You may need to lose some weight. Consider a balanced diet and exercise.";
            case "Obese" -> "Your health may be at risk. Please consult with a healthcare provider.";
            default -> "BMI calculated successfully.";
        };
    }
    
    private String getHealthAdvice(String category) {
        return switch (category) {
            case "Underweight" -> 
                "• Eat protein-rich foods\n" +
                "• Eat 5-6 small meals daily\n" +
                "• Do strength training\n" +
                "• Consult a nutritionist";
            case "Normal weight" -> 
                "• Maintain your current routine\n" +
                "• Continue regular exercise\n" +
                "• Eat a balanced diet\n" +
                "• Get 7-8 hours of sleep";
            case "Overweight" -> 
                "• Reduce calorie intake\n" +
                "• Walk 30 minutes daily\n" +
                "• Avoid sugar and fried foods\n" +
                "• Drink 3-4 liters of water";
            case "Obese" -> 
                "• Consult a doctor immediately\n" +
                "• Follow a proper diet plan\n" +
                "• Get regular medical checkups\n" +
                "• Make lifestyle changes";
            default -> "Stay healthy and active!";
        };
    }
    
    private String getColorCode(String category) {
        return switch (category) {
            case "Underweight" -> "#3498db";  // Blue
            case "Normal weight" -> "#27ae60"; // Green
            case "Overweight" -> "#f39c12";   // Orange
            case "Obese" -> "#e74c3c";        // Red
            default -> "#95a5a6";             // Gray
        };
    }
}
