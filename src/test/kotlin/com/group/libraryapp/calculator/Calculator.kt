package com.group.libraryapp.calculator

class Calculator(
    var number: Int
) {

    fun add(operand: Int) {
        this.number += operand
    }

    fun sub(operand: Int) {
        this.number -= operand
    }

    fun mul(operand: Int) {
        this.number *= operand
    }

    fun div(operand: Int) {
        if (operand == 0) throw IllegalArgumentException("divided by zero")
        this.number /= operand
    }
}