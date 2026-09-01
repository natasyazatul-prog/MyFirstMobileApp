package com.example.eventeligibilityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                EligibilityApp()
            }
        }
    }
}

@Composable
fun EligibilityApp() {

    // Simpan umur yang user taip
    var ageInput by remember {
        mutableStateOf("")
    }

    // Simpan keputusan
    var result by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.event),
            contentDescription = "To-Do List Icon",
            modifier = Modifier.size(100.dp)
        )

            Text(
                text = "Event Eligibility Checker",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = ageInput,

                onValueChange = {
                    ageInput = it
                },

                label = {
                    Text("Enter your age")
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {

                    // Tukar input kepada nombor
                    val age = ageInput.toIntOrNull()

                    // Check umur
                    if (age == null) {

                        result = "Please enter a valid age."

                    } else if (age >= 18) {

                        result = "CONGRATS ! You are eligible to join the event ^^ "

                    } else {

                        result = "SORRY . You are not eligible to join the event :("
                    }
                }
            ) {

                Text("CHECK")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = result
            )
        }
    }


