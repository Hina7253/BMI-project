/**
 * BMI Calculator - JavaScript
 * Author: Your Name
 * Description: Handles all frontend logic for BMI calculation
 */

// ===== Configuration =====
const API_BASE_URL = 'http://localhost:8080/api/bmi';

// ===== Global Variables =====
let selectedUnit = 'metric';

// ===== DOM Elements =====
const elements = {
    unitBtns: document.querySelectorAll('.unit-btn'),
    weightInput: document.getElementById('weight'),
    heightInput: document.getElementById('height'),
    weightHint: document.getElementById('weightHint'),
    heightHint: document.getElementById('heightHint'),
    bmiForm: document.getElementById('bmiForm'),
    loading: document.getElementById('loading'),
    errorMessage: document.getElementById('errorMessage'),
    resultCard: document.getElementById('resultCard'),
    calculateBtn: document.getElementById('calculateBtn'),
    bmiValue: document.getElementById('bmiValue'),
    category: document.getElementById('category'),
    healthMessage: document.getElementById('healthMessage'),
    healthAdvice: document.getElementById('healthAdvice')
};

// ===== Event Listeners =====

/**
 * Initialize all event listeners
 */
function initializeEventListeners() {
    // Unit toggle buttons
    elements.unitBtns.forEach(btn => {
        btn.addEventListener('click', handleUnitToggle);
    });

    // Form submission
    elements.bmiForm.addEventListener('submit', handleFormSubmit);

    // Auto-focus on weight input when page loads
    window.addEventListener('load', () => {
        elements.weightInput.focus();
    });
}

/**
 * Handle unit toggle between Metric and Imperial
 */
function handleUnitToggle(event) {
    // Remove active class from all buttons
    elements.unitBtns.forEach(btn => btn.classList.remove('active'));
    
    // Add active class to clicked button
    event.target.classList.add('active');
    
    // Update selected unit
    selectedUnit = event.target.dataset.unit;
    
    // Update placeholders and hints
    updatePlaceholders();
}

/**
 * Update input placeholders based on selected unit
 */
function updatePlaceholders() {
    if (selectedUnit === 'metric') {
        elements.weightInput.placeholder = 'e.g., 70';
        elements.heightInput.placeholder = 'e.g., 1.75';
        elements.weightHint.textContent = 'in kilograms (kg)';
        elements.heightHint.textContent = 'in meters (m)';
    } else {
        elements.weightInput.placeholder = 'e.g., 154';
        elements.heightInput.placeholder = 'e.g., 68';
        elements.weightHint.textContent = 'in pounds (lbs)';
        elements.heightHint.textContent = 'in inches (in)';
    }
}

/**
 * Handle form submission
 */
async function handleFormSubmit(event) {
    event.preventDefault();

    // Get input values
    const weight = parseFloat(elements.weightInput.value);
    const height = parseFloat(elements.heightInput.value);

    // Frontend validation
    if (!validateInputs(weight, height)) {
        return;
    }

    // Prepare UI for API call
    prepareUIForCalculation();

    try {
        // Make API call
        const data = await calculateBMI(weight, height, selectedUnit);
        
        // Display result
        displayResult(data);
    } catch (error) {
        // Handle errors
        handleError(error);
    } finally {
        // Reset UI state
        resetUIState();
    }
}

/**
 * Validate user inputs
 */
function validateInputs(weight, height) {
    if (!weight || !height) {
        showError('Please enter both weight and height!');
        return false;
    }

    if (weight <= 0 || height <= 0) {
        showError('Please enter valid positive numbers!');
        return false;
    }

    return true;
}

/**
 * Prepare UI for calculation (show loading, hide results)
 */
function prepareUIForCalculation() {
    elements.resultCard.classList.remove('show');
    elements.errorMessage.classList.remove('show');
    elements.loading.classList.add('show');
    elements.calculateBtn.disabled = true;
}

/**
 * Reset UI state after calculation
 */
function resetUIState() {
    elements.loading.classList.remove('show');
    elements.calculateBtn.disabled = false;
}

/**
 * Make API call to calculate BMI
 */
async function calculateBMI(weight, height, unit) {
    const response = await fetch(`${API_BASE_URL}/calculate`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            weight: weight,
            height: height,
            unit: unit
        })
    });

    const data = await response.json();

    if (!response.ok) {
        // Handle validation errors from backend
        throw new Error(getValidationError(data));
    }

    return data;
}

/**
 * Get validation error message from backend response
 */
function getValidationError(data) {
    if (data.weight) return data.weight;
    if (data.height) return data.height;
    if (data.message) return data.message;
    return 'Error calculating BMI';
}

/**
 * Display calculation result
 */
function displayResult(data) {
    // Set BMI value
    elements.bmiValue.textContent = data.bmi.toFixed(2);
    
    // Set category
    elements.category.textContent = data.category;
    
    // Set health message
    elements.healthMessage.textContent = data.healthMessage;
    
    // Format and set health advice
    formatHealthAdvice(data.healthAdvice);
    
    // Set background color based on category
    elements.resultCard.style.background = data.colorCode;
    
    // Show result card with animation
    elements.resultCard.classList.add('show');
    
    // Scroll to result
    scrollToResult();
    
    // Log success
    console.log('BMI Calculation Result:', data);
}

/**
 * Format health advice into bullet points
 */
function formatHealthAdvice(advice) {
    const adviceLines = advice.split('\n').filter(line => line.trim());
    const adviceHTML = adviceLines.map(line => `<div>${line}</div>`).join('');
    elements.healthAdvice.innerHTML = adviceHTML;
}

/**
 * Scroll to result card smoothly
 */
function scrollToResult() {
    elements.resultCard.scrollIntoView({ 
        behavior: 'smooth', 
        block: 'nearest' 
    });
}

/**
 * Handle errors
 */
function handleError(error) {
    console.error('Error:', error);
    
    let errorMsg = error.message || 'An error occurred';
    
    // Check if it's a network error
    if (error instanceof TypeError && error.message.includes('fetch')) {
        errorMsg = 'Cannot connect to server. Make sure backend is running on http://localhost:8080';
    }
    
    showError(errorMsg);
}

/**
 * Show error message to user
 */
function showError(message) {
    elements.errorMessage.textContent = '❌ ' + message;
    elements.errorMessage.classList.add('show');

    // Auto-hide after 5 seconds
    setTimeout(() => {
        elements.errorMessage.classList.remove('show');
    }, 5000);
}

/**
 * Reset form (called by recalculate button)
 */
function resetForm() {
    // Hide result card
    elements.resultCard.classList.remove('show');
    
    // Clear input fields
    elements.weightInput.value = '';
    elements.heightInput.value = '';
    
    // Focus on weight input
    elements.weightInput.focus();
    
    // Hide any error messages
    elements.errorMessage.classList.remove('show');
}

/**
 * Test backend connection
 */
async function testBackendConnection() {
    try {
        const response = await fetch(`${API_BASE_URL}/health`);
        const data = await response.json();
        console.log('✅ Backend Status:', data.message);
        return true;
    } catch (error) {
        console.error('❌ Backend not reachable:', error.message);
        console.log('💡 Make sure to start backend: mvn spring-boot:run');
        return false;
    }
}

/**
 * Get BMI category info
 */
function getBMICategoryInfo(bmi) {
    if (bmi < 18.5) {
        return { category: 'Underweight', color: '#3498db', icon: '🔵' };
    } else if (bmi >= 18.5 && bmi < 25) {
        return { category: 'Normal weight', color: '#27ae60', icon: '🟢' };
    } else if (bmi >= 25 && bmi < 30) {
        return { category: 'Overweight', color: '#f39c12', icon: '🟠' };
    } else {
        return { category: 'Obese', color: '#e74c3c', icon: '🔴' };
    }
}

/**
 * Format number to 2 decimal places
 */
function formatNumber(num) {
    return Math.round(num * 100) / 100;
}

/**
 * Log app info to console
 */
function logAppInfo() {
    console.log('%c💪 BMI Calculator App', 'color: #667eea; font-size: 20px; font-weight: bold');
    console.log('%cBackend API: ' + API_BASE_URL, 'color: #764ba2');
    console.log('%cTesting backend connection...', 'color: #999');
    testBackendConnection();
}

// ===== Initialize App =====

/**
 * Initialize the application
 */
function initializeApp() {
    // Set up event listeners
    initializeEventListeners();
    
    // Log app info
    logAppInfo();
    
    // Test backend connection
    testBackendConnection();
}

// Run initialization when DOM is ready
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initializeApp);
} else {
    initializeApp();
}

// ===== Utility Functions =====

/**
 * Convert kg to lbs
 */
function kgToLbs(kg) {
    return kg * 2.20462;
}

/**
 * Convert lbs to kg
 */
function lbsToKg(lbs) {
    return lbs * 0.453592;
}

/**
 * Convert meters to inches
 */
function metersToInches(meters) {
    return meters * 39.3701;
}

/**
 * Convert inches to meters
 */
function inchesToMeters(inches) {
    return inches * 0.0254;
}

// ===== Export functions (if using modules) =====
// Uncomment if using ES6 modules
/*
export {
    calculateBMI,
    resetForm,
    getBMICategoryInfo
};
*/