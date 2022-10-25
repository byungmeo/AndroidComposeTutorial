package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    /*modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background*/
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    //EditNumberField()에서 관리하던 State를 여기서 관리
    //이제 EditNumberField()는 Stateless하다
    var amountInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount)

    Column(
        modifier = Modifier.padding(32.dp), //border와 content(Column?) 사이의 간격
        verticalArrangement = Arrangement.spacedBy(8.dp) //하위 요소에 고정된 dp 공백 추가
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        EditNumberField(value = amountInput, onValueChange = { amountInput = it })
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.tip_amount, tip), //"Tip amount: %s"
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EditNumberField(value: String, onValueChange: (String) -> Unit) {
    //amountInput은 remember에게 getter와 setter를 위임
    //MutableState의 value 속성을 참조하지 않고도 값 변경 가능 (amount.value)
    //객체를 메모리에 저장하여 리컴포지션시에도 초기값으로 초기화되지 않고 저장된 값을 불러옴
    //var amountInput by remember { mutableStateOf("") } <- Stateless 하도록 상위 함수에서 관리

    //tip 이라는 State를 관리하고 있으므로 EditNumberField 컴포저블은 Stateful하다
    //(시간이 지남에 따라 변할 수 있는 상태를 소유하는 컴포저블)
    //하지만, 다른 컴포저블이 amount나 tip이라는 State에 엑세스 하고 싶은 경우에는
    //EditNumberField()에서 이 State들을 호이스팅(Hoisting) 하거나 추출해야 한다.
    //앱에서 재사용 할 수 있는 Stateless 컴포저블을 만들거나,
    //State를 여러 컴포저블과 공유하기 위해서는 State를 호이스팅 해야 한다
    //상태 호이스팅은 구성요소를 Stateless로 만들기 위해 State를 다른 함수 위로 옮기는 패턴
    //현재 EditNumberField()의 인수로 받아온 value와 onValueChange가 패턴을 적용한 것이다
    /*
    val amount = amountInput.toDoubleOrNull() ?: 0.0 //문자열을 Double로 변환,, 유효하지 않으면 0.0
    val tip = calculateTip(amount)
    */

    //mutableStateOf로 저장된 변수는 Compose에 의해 변경사항이 추적되며
    //변경사항이 발생하면 리컴포지션이 발생한다
    //리컴포지션은 동일한 컴포저블을 다시 실행하여 데이터가 변경될 때 트리를 업데이트하는 프로세스임.
    //mutableStateOf를 이렇게 단독으로 사용하면 변경은 가능하지만 리컴포지션시 계속 0으로 초기화됨
    //var amountInput: MutableState<String> = mutableStateOf("0")
    TextField(
        modifier = Modifier.fillMaxWidth(), //가능한만큼 너비 늘리기
        //value = amountInput,
        value = value,
        label = { Text(text = stringResource(R.string.bill_amount)) }, //제목표시 (입력 전 표시 및 입력할 때 참고 가능)
        singleLine = true, //텍스트를 단일 줄로만 입력 (엔터 허용 X?)
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), //숫자 좌판이 뜨고 고정
        //onValueChange = { amountInput = it } //it : 람다 표현식의 기본 매개변수명
        onValueChange = onValueChange //Stateless 하도록 변경
    )
}

private fun calculateTip (amount: Double, tipPercent: Double = 15.0) : String {
    val tip = tipPercent / 100 * amount
    //amount = 10000
    return NumberFormat.getCurrencyInstance().format(tip) // -> $1500
    //return tip.toString() // -> 1500.0
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}