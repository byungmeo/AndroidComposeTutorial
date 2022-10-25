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
    //by : Delegate Pattern 코드를 자동으로 구현해주는 키워드
    //새로고침(리컴포지션) 되어도 가장 최근의 result 값이 사용됨
    //observable 변수에 대해서 알아야 함 (값이 변경될 때 재구성(Recomposition)을 예약하는 변수)
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

    //remember mutableStateOf를 쓰지 않으면 버튼을 클릭 할 때마다 계속 랜덤값으로 초기화 시켜버림
    var squeezeCount by remember { mutableStateOf((2..4).random()) }
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
            onClick = {
                if(step == 2) {
                    if(--squeezeCount <= 0) {
                        squeezeCount = (2..4).random()
                        step++
                    }
                } else if(++step > 4) step = 1
            },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(backgroundColor = pressedColor),
            border = BorderStroke(width = 1.dp, color = Color(105, 205, 216)),
        ) {
            Image(painter = painterResource(id = imageName), contentDescription = text)
        }
    }
}