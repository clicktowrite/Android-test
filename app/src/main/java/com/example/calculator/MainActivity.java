package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private String currentNumber = "";
    private String leftOperand = "";
    private String rightOperand = "";
    private String operator = "";
    private double result = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);

        // Number buttons
        findViewById(R.id.button_0).setOnClickListener(this::onNumberClick);
        findViewById(R.id.button_1).setOnClickListener(this::onNumberClick);
        findViewById(R.id.button_2).setOnClickListener(this::onNumberClick);
        findViewById(R.id.button_3).setOnClickListener(this::onNumberClick);
        findViewById(R.id.button_4).setOnClickListener(this::onNumberClick);
        findViewById(R.id.button_5).setOnClickListener(this::onNumberClick);
        findViewById(R.id.button_6).setOnClickListener(this::onNumberClick);
        findViewById(R.id.button_7).setOnClickListener(this::onNumberClick);
        findViewById(R.id.button_8).setOnClickListener(this::onNumberClick);
        findViewById(R.id.button_9).setOnClickListener(this::onNumberClick);
        findViewById(R.id.button_dot).setOnClickListener(this::onNumberClick);

        // Operator buttons
        findViewById(R.id.button_add).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.button_subtract).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.button_multiply).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.button_divide).setOnClickListener(this::onOperatorClick);

        // Equals button
        findViewById(R.id.button_equals).setOnClickListener(this::onEqualsClick);

        // Clear button
        findViewById(R.id.button_clear).setOnClickListener(this::onClearClick);
    }

    private void onNumberClick(View view) {
        Button button = (Button) view;
        currentNumber += button.getText().toString();
        updateResultTextView();
    }

    private void onOperatorClick(View view) {
        Button button = (Button) view;
        if (!currentNumber.isEmpty()) {
            if (!leftOperand.isEmpty()) {
                rightOperand = currentNumber;
                calculate();
                leftOperand = String.valueOf(result);
                rightOperand = "";
            } else {
                leftOperand = currentNumber;
            }
            currentNumber = "";
            operator = button.getText().toString();
        }
    }

    private void onEqualsClick(View view) {
        if (!leftOperand.isEmpty() && !currentNumber.isEmpty()) {
            rightOperand = currentNumber;
            calculate();
            leftOperand = String.valueOf(result);
            currentNumber = leftOperand; // Keep result for next calculation
            rightOperand = "";
            operator = "";
        }
    }

    private void onClearClick(View view) {
        currentNumber = "";
        leftOperand = "";
        rightOperand = "";
        operator = "";
        result = 0.0;
        resultTextView.setText("0");
    }

    private void calculate() {
        double left = Double.parseDouble(leftOperand);
        double right = Double.parseDouble(rightOperand);

        switch (operator) {
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "/":
                if (right != 0) {
                    result = left / right;
                } else {
                    // Handle division by zero
                    resultTextView.setText("Error");
                    return;
                }
                break;
        }
        currentNumber = String.valueOf(result);
        updateResultTextView();
    }

    private void updateResultTextView() {
        if (currentNumber.isEmpty()) {
            resultTextView.setText("0");
        } else {
            resultTextView.setText(currentNumber);
        }
    }
}