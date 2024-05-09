
package com.example.counterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.counterapp.ui.theme.CounterAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterAppTheme {
                val counter = remember { mutableStateOf(0) }
                val coroutineScope = rememberCoroutineScope()
                var countingState = remember { mutableStateOf(false) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "${counter.value}",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 35.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                        Spacer(modifier = Modifier.height(130.dp))
                        CreateCircle(
                            counter = counter.value,
                            countUp = {
                                    newValue -> counter.value = newValue
                            },
                            counting = countingState.value,
                            function = {
                                countingState.value = !countingState.value
                                coroutineScope.launch {
                                    increaseCounter(counter, countingState)
                                }

                            }
                        )
                    }
                }
            }
        }
    }

    private suspend fun increaseCounter(counter: MutableState<Int>, countingState: MutableState<Boolean>) {
        while (countingState.value) {
            delay(100)
            counter.value++
        }
    }
}

@Composable
fun CreateCircle(counter: Int = 0, countUp: (Int) -> Unit, counting: Boolean, function: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(3.dp)
            .size(105.dp)
            .clickable { function() },
        elevation = CardDefaults.cardElevation(pressedElevation = 4.dp),
        shape = CircleShape
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Tap",
                textAlign = TextAlign.Center
            )
        }
        // Hiển thị biểu tượng "⏸" nếu đang đếm và biểu tượng "▶️" nếu không đếm

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CounterAppTheme {
        CreateCircle(counter = 0, countUp = {}, counting = false) {}
    }
}
