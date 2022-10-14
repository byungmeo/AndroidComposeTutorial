package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.ui.theme.HappyBirthdayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyBirthdayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BirthdayGreetingWithImage(
                        messages = getString(R.string.happy_birthday_text),
                        from = getString(R.string.signature_text)
                    )
                }
            }
        }
    }
}

@Composable
fun BirthdayGreetingWithText(messages: String, from: String) {
    Column {
        Text(
            text = messages,
            fontSize = 28.sp,

            // https://developer.android.com/static/codelabs/basic-android-kotlin-compose-add-images/img/470075ea26fdf4f8.png
            // https://developer.android.com/static/codelabs/basic-android-kotlin-compose-add-images/img/2e96e127f9f8c7.png
            modifier = Modifier
                .fillMaxWidth() // 텍스트를 사용 가능한 최대 너비로 설정
                .wrapContentWidth(Alignment.CenterHorizontally) // 화면의 시작 부분 또는 상위 요소에 맞춰 정렬
                .padding(start = 16.dp, top = 16.dp) // 여백
        )

        Text(
            text = from,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 16.dp, top = 16.dp)
        )
    }
}

@Composable
fun BirthdayGreetingWithImage(messages: String, from: String) {
    // R 클래스는 Android에서 자동으로 생성되는 클래스로, 프로젝트에 있는 모든 리소스의 ID를 포함함
    // painterResource 함수는 drawable 이미지 리소스를 로드함. 인수로 리소스 ID를 받음.
    val image = painterResource(id = R.drawable.androidparty)

    // Box를 사용하여 요소를 서로 위에 쌓는다.
    Box {

        Image(
            painter = image,

            // 장식 목적의 이미지는 콘텐츠 설명을 추가하면 음성 안내 지원과 함께 사용하기가 더 어려워짐.
            // null을 설정하며 TalkBack이 Image 컴포저블을 건너뛰도록 할 수 있음
            contentDescription = null,

            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),

            // 이미지의 너비와 높이가 화면의 크기와 같거나 크도록
            // 가로세로 비율을 유지하기 위해 이미지의 크기를 균일하게 조정
            contentScale = ContentScale.Crop
        )
        BirthdayGreetingWithText(messages = messages, from = from)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HappyBirthdayTheme {
        BirthdayGreetingWithImage(
            messages = stringResource(R.string.happy_birthday_text),
            from = stringResource(R.string.signature_text)
        )
    }
}