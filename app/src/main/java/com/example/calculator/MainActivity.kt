package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                val viewModel = viewModel<CalculatorViewModel>()
                val state = viewModel.state
                CalculatorScreen(
                    state = state,
                    onAction = viewModel::onAction
                )
            }
        }
    }
}

@Composable
fun CalculatorScreen(
    state: CalculatorState,
    onAction: (CalculatorAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                fontSize = 80.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton(symbol = "AC", modifier = Modifier.aspectRatio(2f).weight(2f)) { onAction(CalculatorAction.Clear) }
                CalculatorButton(symbol = "Del", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Delete) }
                CalculatorButton(symbol = "/", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Operation(CalculatorOperation.Divide)) }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton(symbol = "7", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Number(7)) }
                CalculatorButton(symbol = "8", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Number(8)) }
                CalculatorButton(symbol = "9", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Number(9)) }
                CalculatorButton(symbol = "x", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Operation(CalculatorOperation.Multiply)) }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton(symbol = "4", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Number(4)) }
                CalculatorButton(symbol = "5", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Number(5)) }
                CalculatorButton(symbol = "6", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Number(6)) }
                CalculatorButton(symbol = "-", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Operation(CalculatorOperation.Subtract)) }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton(symbol = "1", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Number(1)) }
                CalculatorButton(symbol = "2", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Number(2)) }
                CalculatorButton(symbol = "3", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Number(3)) }
                CalculatorButton(symbol = "+", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Operation(CalculatorOperation.Add)) }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton(symbol = "0", modifier = Modifier.aspectRatio(2f).weight(2f)) { onAction(CalculatorAction.Number(0)) }
                CalculatorButton(symbol = ".", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Decimal) }
                CalculatorButton(symbol = "=", modifier = Modifier.aspectRatio(1f).weight(1f)) { onAction(CalculatorAction.Calculate) }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    symbol: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = symbol, fontSize = 36.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTheme {
        CalculatorScreen(
            state = CalculatorState(),
            onAction = {}
        )
    }
}