package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class CalculatorActivity extends AppCompatActivity {

    private TextView display;
    private StringBuilder currentInput = new StringBuilder();
    private double firstValue = 0;
    private double secondValue = 0;
    private char currentOperator = ' ';
    private boolean isSecondValue = false;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        display = findViewById(R.id.display);
        if (display == null) {
            Log.e("CalculatorActivity", "Display TextView is null.");
            return;
        }

        setButtonListeners();
    }

    private void setButtonListeners() {
        int[] buttonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,
                R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide,
                R.id.btn_equals, R.id.btn_clear
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String buttonText = button.getText().toString();
                handleInput(buttonText);
            }
        };

        for (int id : buttonIds) {
            Button button = findViewById(id);
            if (button == null) {
                Log.e("CalculatorActivity", "Button with ID " + id + " is null.");
            } else {
                button.setOnClickListener(listener);
            }
        }
    }

    private void handleInput(String input) {
        switch (input) {
            case "C":
                clear();
                break;
            case "=":
                evaluate();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (!isSecondValue) {
                    if (currentInput.length() > 0) {
                        // Save the current input and operator
                        firstValue = Double.parseDouble(currentInput.toString());
                        currentOperator = input.charAt(0);
                        currentInput.setLength(0);
                        isSecondValue = true;
                    }
                    appendOperator(input);
                }
                break;
            default:
                appendNumber(input);
                break;
        }
    }

    private void clear() {
        currentInput.setLength(0);
        display.setText("0");
        firstValue = 0;
        secondValue = 0;
        currentOperator = ' ';
        isSecondValue = false;
    }

    private void appendNumber(String input) {
        currentInput.append(input);
        display.setText(currentInput.toString());
    }

    private void appendOperator(String operator) {
        // Show the current operator in the display if there is an existing input
        if (currentInput.length() > 0) {
            display.setText(currentInput.toString() + " " + operator);
        }
    }

    private void evaluate() {
        if (isSecondValue && currentInput.length() > 0) {
            secondValue = Double.parseDouble(currentInput.toString());
            double result = calculate(firstValue, secondValue, currentOperator);
            display.setText(decimalFormat.format(firstValue) + " " + currentOperator + " " + decimalFormat.format(secondValue) + " = " + decimalFormat.format(result));

            // Reset for new input
            currentInput.setLength(0);
            firstValue = result;
            secondValue = 0;
            currentOperator = ' ';
            isSecondValue = false;
        }
    }

    private double calculate(double a, double b, char operator) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return b != 0 ? a / b : 0;
            default: return 0;
        }
    }
}
