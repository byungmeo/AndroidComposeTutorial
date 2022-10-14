package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greetingcard.ui.theme.GreetingCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Byungmeo")
                }
            }
        }
    }
}

// setContent 또는 다른 Composable 함수에서 호출 가능하다는 것을 알림
@Composable
// @Composable 함수 이름은 대문자로 표기
// @Composable 함수는 아무것도 반환할 수 없음
fun Greeting(name: String) {
    // 배경 색상을 다르게 설정하려면 Text를 Container로 둘러싸야함
    // (Alt + Enter) -> Surround with widget -> Surround with Container
    // 기본 제공 컨테이너는 Box 이지만, 다른 유형으로 변경 가능 -> Surface()
    // color 매개변수를 주면 텍스트 배경색 변경 가능
    Surface(color = Color.Green) {
        // modifier 매개변수를 추가하여 요소 주위에 공백 추가 가능
        // Modifier는 Composable을 강화하거나 장식하는데 사용
        Text(text = "Hi, my name is $name!", modifier = Modifier.padding(24.dp))
    }
}

@Preview(showBackground = true) //미리보기 배경 사용 유무
@Composable
//전체 앱을 빌드하지 않고도 앱이 어떻게 표시되는지 확인할 수 있음
fun DefaultPreview() {
    GreetingCardTheme {
        Greeting("Byungmeo")
    }
}