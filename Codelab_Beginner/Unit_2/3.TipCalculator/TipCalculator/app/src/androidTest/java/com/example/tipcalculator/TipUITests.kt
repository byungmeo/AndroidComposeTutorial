package com.example.tipcalculator

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import org.junit.Rule
import org.junit.Test

class TipUITests {
    //ComposeContentTestRule 객체를 통해 UI 구성요소에 접근할 수 있음
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            TipCalculatorTheme {
                TipCalculatorScreen()
            }
        }

        //"Bill Amount" 라는 텍스트가 포함된 노드에 엑세스하여 "10"이라는 텍스트를 Input
        composeTestRule.onNodeWithText("Bill Amount").performTextInput("10")

        //"Tip (%)" 라는 텍스트가 포함된 노드에 엑세스하여 "20"이라는 텍스트를 Input
        composeTestRule.onNodeWithText("Tip (%)").performTextInput("20")

        //"Tip amount: $2.00" 라는 텍스트가 포함된 노드가 존재하는지 테스트
        composeTestRule.onNodeWithText("Tip amount: $2.00").assertExists()
    }
}