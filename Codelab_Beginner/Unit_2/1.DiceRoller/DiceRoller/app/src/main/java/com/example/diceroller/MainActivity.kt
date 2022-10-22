package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(modifier = Modifier
        //구성요소가 사용 가능한 공간을 채우도록 지정
        .fillMaxSize()

        //사용 가능한 공간이 최소한 내부에 있는 구성요소만큼 커야 한다 (wrapContentSize())
        //하지만, fillMaxSize() 메서드가 사용되므로 내부의 구성요소가 사용 가능한 공간보다 작으면
        //Alignment 객체를 wrapContentSize() 메서드에 전달하여
        //사용 가능한 공간 내에서 구성요소를 정렬할 수 있다
        //Alignment.Center는 구성요소가 세로와 가로로 모두 중앙에서 배치되도록 지정한다
        .wrapContentSize(Alignment.Center)
    )
}

@Preview(showBackground = true)
@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    //아직은 몰라도 되는 것들
    //remember : 컴포지션의 객체를 메모리에 저장
    //mutableStateOf() : UI를 새로고침하여 observable을 만듦
    var result by remember { mutableStateOf(1) }
    val imageResource = when(result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    Column(
        //modifier 인수는 Column 함수의 컴포저블이 modifier 인스턴스에서
        //호출된 제약 조건을 준수하도록 한다
        modifier = modifier,

        //열이 너비를 기준으로 기기 화면의 중앙에 배치된다
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = result.toString()
        )
        Spacer(modifier = Modifier.height(16.dp)) //주사위와 버튼 간의 여백 추가
        Button(onClick = { result = (1..6).random() }) {
            Text(stringResource(R.string.roll))
        }
    }
}