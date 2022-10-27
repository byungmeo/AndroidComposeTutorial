package com.example.tipcalculator

import org.junit.Test
import org.junit.Assert.*

class TipCalculatorTests {
    //컴파일러에게 테스트 메서드임을 알리고 적절하게 테스트가 실행됨
    @Test
    fun calculate_20_percent_tip_no_roundup() {
        val amount = 10.00
        val tipPercent = 20.00
        val expectedTip = "₩2" //기대하는 값
        val actualTip = calculateTip(amount = amount, tipPercent = tipPercent, false)
        assertEquals(expectedTip, actualTip)
    }
}