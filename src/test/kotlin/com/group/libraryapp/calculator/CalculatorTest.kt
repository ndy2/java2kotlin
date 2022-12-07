package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class CalculatorTest {

    companion object{

        @BeforeAll
        @JvmStatic
        fun beforeAll(){
            println("before all")
        }

        @AfterAll
        @JvmStatic
        fun afterAll(){
            println("after all")
        }
    }

    @Test
    fun add() {
        //given
        val calculator = Calculator(5)

        //when
        calculator.add(3)

        //then
        assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun div() {
        //given
        val calculator = Calculator(5)

        //when
        calculator.div(2)

        //then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun `div by zero`() {
        //given
        val calculator = Calculator(5)

        //when then
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy { calculator.div(0) }
    }
}