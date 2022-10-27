package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
                TipCalculatorScreen()
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    //EditNumberField()에서 관리하던 State 를 여기서 관리
    //이제 EditNumberField()는 Stateless 하다
    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    //반올림 여부를 저장하는 State 를 관리
    var roundUp by remember { mutableStateOf(false) }
    val tip = calculateTip(amount, tipPercent, roundUp)

    //LocalFocusManager 인터페이스는 Compose 에서 포커스를 제어(이동 또는 삭제)하는데 사용
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(32.dp), //border 와 content(Column?) 사이의 간격
        verticalArrangement = Arrangement.spacedBy(8.dp) //하위 요소에 고정된 dp 공백 추가
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        EditNumberField(
            label = R.string.bill_amount,
            value = amountInput,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, //숫자 좌판이 뜨고 고정
                imeAction = ImeAction.Next //작업 버튼 설정 (다음 텍스트필드로)
            ),
            keyboardActions = KeyboardActions(
                //Next 버튼을 누르면 focusManager 를 통해서 포커스를 바로 아래에 있는 컴포저블로 이동
                //Spacer 나 Text 같은 포커스를 가질 수 없는 컴포저블엔 포커스가 가지 않을듯?
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            onValueChange = { amountInput = it },
        )
        EditNumberField(
            label = R.string.how_was_the_service,
            value = tipInput,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, //숫자 좌판이 뜨고 고정
                imeAction = ImeAction.Done //작업 버튼 설정 (입력 마침 -> 기능은 별도 추가)
            ),
            keyboardActions = KeyboardActions(
                //Done 버튼을 누르면 focusManager 를 통해서 포커스가 있는 구성요소에서 포커스를 삭제
                //포커스가 삭제되면 키패드가 자동으로 닫힐 것이다
                onDone = { focusManager.clearFocus() }
            ),
            onValueChange = { tipInput = it },
        )
        RoundTheTipRow(roundUp = roundUp, onRoundUpChanged = { roundUp = it })
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.tip_amount, tip), //"Tip amount: %s"
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

//+ 재사용 가능하도록 수정
@Composable
fun EditNumberField(
    //@StringRes : 문자열 리소스 참조(id)여야 함을 나타냄
    @StringRes label: Int,
    value: String,
    keyboardOptions: KeyboardOptions, //재사용 시 키보드 옵션
    keyboardActions: KeyboardActions, //어떤 종류의 버튼을 누르면 무슨 행동을 취할지 정의
    onValueChange: (String) -> Unit,
    //modifier: Modifier = Modifier
) {
    //amountInput 은 remember 에게 getter 와 setter 를 위임
    //MutableState 의 value 속성을 참조하지 않고도 값 변경 가능 (amount.value)
    //객체를 메모리에 저장하여 리컴포지션시에도 초기값으로 초기화되지 않고 저장된 값을 불러옴
    //var amountInput by remember { mutableStateOf("") } <- Stateless 하도록 상위 함수에서 관리

    //tip 이라는 State 를 관리하고 있으므로 EditNumberField 컴포저블은 Stateful 하다
    //(시간이 지남에 따라 변할 수 있는 상태를 소유하는 컴포저블)
    //하지만, 다른 컴포저블이 amount 나 tip 이라는 State 에 엑세스 하고 싶은 경우에는
    //EditNumberField()에서 이 State 들을 호이스팅(Hoisting) 하거나 추출해야 한다.
    //앱에서 재사용 할 수 있는 Stateless 컴포저블을 만들거나,
    //State 를 여러 컴포저블과 공유하기 위해서는 Stat e를 호이스팅 해야 한다
    //상태 호이스팅은 구성요소를 Stateless 로 만들기 위해 State 를 다른 함수 위로 옮기는 패턴
    //현재 EditNumberField()의 인수로 받아온 value 와 onValueChange 가 패턴을 적용한 것이다
    /*
    val amount = amountInput.toDoubleOrNull() ?: 0.0 //문자열을 Double 로 변환,, 유효하지 않으면 0.0
    val tip = calculateTip(amount)
    */

    //mutableStateOf로 저장된 변수는 Compose 에 의해 변경사항이 추적되며
    //변경사항이 발생하면 리컴포지션이 발생한다
    //리컴포지션은 동일한 컴포저블을 다시 실행하여 데이터가 변경될 때 트리를 업데이트하는 프로세스임.
    //mutableStateOf를 이렇게 단독으로 사용하면 변경은 가능하지만 리컴포지션시 계속 0으로 초기화됨
    //var amountInput: MutableState<String> = mutableStateOf("0")
    TextField(
        modifier = Modifier.fillMaxWidth(), //가능한만큼 너비 늘리기
        //value = amountInput,
        value = value,
        label = { Text(text = stringResource(label)) }, //제목표시 (입력 전 표시 및 입력할 때 참고 가능)
        singleLine = true, //텍스트를 단일 줄로만 입력 (엔터 허용 X?)
        keyboardOptions = keyboardOptions,
        /*
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number, //숫자 좌판이 뜨고 고정
            imeAction = ImeAction.Next //작업 버튼 설정 (다음 텍스트필드로)
        ),
        */
        //onValueChange = { amountInput = it } //it : 람다 표현식의 기본 매개변수명
        keyboardActions = keyboardActions,
        onValueChange = onValueChange //Stateless 하도록 변경
    )
}

//test 코드에서 접근할 수 있도록 internal 로 선언하고
//테스트 목적으로만 공개된다고 annotation 을 선언한다
@VisibleForTesting
internal fun calculateTip (
    amount: Double,
    tipPercent: Double = 15.0,
    roundUp: Boolean
) : String {
    var tip = tipPercent / 100 * amount
    if(roundUp) tip = kotlin.math.ceil(tip) //반올림

    //amount = 10000일 때..
    return NumberFormat.getCurrencyInstance().format(tip) // -> $1500
    //return tip.toString() // -> 1500.0
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth() //하위 요소의 너비를 화면에 최대로 설정
            .size(48.dp), //하위 요소의 크기를 48.dp로
        verticalAlignment = Alignment.CenterVertically //하위 요소의 정렬을 가운데로 설정
    ) {
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray //Thumb 는 스위치의 동그라미 핸들(?)을 지칭하는것
                //켜진지 꺼진지 나타내는 슬라이드 같은건 트랙이라고 함
                //단, 이것처럼 컴포저블의 색상을 하드코딩 하는 것은 다크모드 등에서
                //명확하게 표시되지 않을 수 있어 좋지 않은 방법임
            ),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End) //화면 끝에 맞춘다
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}