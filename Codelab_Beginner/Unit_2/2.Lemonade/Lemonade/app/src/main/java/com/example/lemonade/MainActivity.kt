package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    MakeLemonade(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}

@Preview(showBackground = true)
@Composable
fun MakeLemonade(modifier: Modifier = Modifier) {
    var step by remember { mutableStateOf(1) }
    val text = when(step) {
        1 -> stringResource(R.string.tree)
        2 -> stringResource(R.string.squeeze)
        3 -> stringResource(R.string.drink)
        else -> stringResource(R.string.restart)
    }
    val imageName = when(step) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressedColor = if(isPressed) Color.LightGray else Color.White

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { if(++step > 4) step = 1 },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(backgroundColor = pressedColor),
            border = BorderStroke(width = 1.dp, color = Color(105, 205, 216)),
        ) {
            Image(painter = painterResource(id = imageName), contentDescription = text)
        }
    }
}