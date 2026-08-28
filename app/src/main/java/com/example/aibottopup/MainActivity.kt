package com.example.aibottopup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DiamondPackage(
    val diamonds: Int,
    val price: Int
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AIBotTopUpApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIBotTopUpApp() {

    var uid by remember { mutableStateOf("") }
    var selectedPackage by remember { mutableStateOf<DiamondPackage?>(null) }
    var message by remember { mutableStateOf("") }

    val packages = listOf(
        DiamondPackage(25, 20),
        DiamondPackage(50, 35),
        DiamondPackage(100, 70),
        DiamondPackage(115, 80),
        DiamondPackage(310, 210),
        DiamondPackage(520, 350),
        DiamondPackage(1060, 690),
        DiamondPackage(2180, 1390)
    )

    MaterialTheme {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "AI Bot Top Up",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                Text(
                    text = "Free Fire Diamond Top Up",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Enter your Player UID and select a package.",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = uid,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            uid = it
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Free Fire UID")
                    },
                    placeholder = {
                        Text("Example: 123456789")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Select Diamond Package",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(packages) { item ->

                        DiamondCard(
                            item = item,
                            selected = selectedPackage == item,
                            onClick = {
                                selectedPackage = item
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {

                        when {
                            uid.isBlank() -> {
                                message = "Please enter your Free Fire UID."
                            }

                            selectedPackage == null -> {
                                message = "Please select a diamond package."
                            }

                            else -> {
                                message =
                                    "UID: $uid\n" +
                                    "Package: ${selectedPackage!!.diamonds} Diamonds\n" +
                                    "Price: ৳${selectedPackage!!.price}"
                            }
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {

                    Text(
                        text = "Continue",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (message.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = message,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
fun DiamondCard(
    item: DiamondPackage,
    selected: Boolean,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                if (selected)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Default.Diamond,
                contentDescription = "Diamonds",
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "${item.diamonds} Diamonds",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "৳${item.price}",
                fontSize = 15.sp
            )
        }
    }
}

