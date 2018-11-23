package lab4

import taras.lab4.readProperty
import kotlin.random.Random
import kotlin.random.nextInt


fun main() {

    println("Enter first polinom power:")
    val firstPolinom = Polinom(readLine()?.toInt() ?: 0)
    firstPolinom.print()

    println("Enter second polinom power:")
    val secondPolinom = Polinom(readLine()?.toInt() ?: 0)
    secondPolinom.print()

    var methodId = 0
    readProperty(
        "Choose method to run: " +
                "1 - plus " +
                "2 - minus " +
                "3 - multiplication " +
                "4 - calculate for argument "
    ) { enteredString ->
        methodId = enteredString.toIntOrNull() ?: 0
    }

    when (methodId) {
        1 -> {
            println("Result = ")
            firstPolinom.plus(secondPolinom)
        }

        2 -> {
            println("Result = ")
            firstPolinom.minus(secondPolinom)
        }

        3 -> {
            println("Result = ")
            firstPolinom.times(secondPolinom)
        }

        4 -> {
            println("Enter argument value")
            val argument = readLine()?.toInt() ?: 0
            println("Firs polinom value = ${firstPolinom.calculateForArgument(argument)}")
            println("Second polinom value = ${secondPolinom.calculateForArgument(argument)}")
        }

        else -> return
    }

}


data class Polinom(var power: Int = 0) {

    val coefsList: List<Int>

    init {
        val list = mutableListOf<Int>()
        for (i in 0..power) {
            list.add(Random.nextInt(-2..2))
        }
        coefsList = list
    }

    fun print(list: List<Int> = coefsList) {
        list.forEachIndexed { index, item ->
            if (item != 0) {

                if (item > 0) print("+")

                if (index == 0) print("$item")
                else print("${item}x^$index")
            }
        }
        println()
    }

    fun calculateForArgument(x: Int) {
        var sum = 0
        coefsList.forEachIndexed { index, item ->
            sum += item * (Math.pow(x.toDouble(), index.toDouble())).toInt()
        }
        println(sum)
    }

    fun plus(other: Polinom) {
        val largerPolinomCoefs = coefsList.takeIf { it.size >= other.coefsList.size } ?: other.coefsList

        val newCoefsList = mutableListOf<Int>()
        for (i in 0 until largerPolinomCoefs.size) {
            val firs = coefsList.getOrNull(i) ?: 0
            val second = other.coefsList.getOrNull(i) ?: 0
            newCoefsList.add(firs + second)
        }
        print(newCoefsList)
    }

    fun minus(other: Polinom) {
        val largerPolinomCoefs = coefsList.takeIf { it.size >= other.coefsList.size } ?: other.coefsList

        val newCoefsList = mutableListOf<Int>()
        for (i in 0 until largerPolinomCoefs.size) {
            val firs = coefsList.getOrNull(i) ?: 0
            val second = other.coefsList.getOrNull(i) ?: 0
            newCoefsList.add(firs - second)
        }
        print(newCoefsList)
    }

    fun times(other: Polinom) {
        val largerPolinomCoefs: List<Int>
        val smalerPolinomCoefs: List<Int>
        val argumentsList: MutableList<Argument> = mutableListOf()

        if (coefsList.size >= other.coefsList.size) {
            largerPolinomCoefs = coefsList
            smalerPolinomCoefs = other.coefsList
        } else {
            smalerPolinomCoefs = coefsList
            largerPolinomCoefs = other.coefsList
        }

        largerPolinomCoefs.forEachIndexed { index, item ->
            smalerPolinomCoefs.forEachIndexed { sIndex, sItem ->
                val value = item * sItem
                val power = index + sIndex
                argumentsList.add(Argument(value, power))
            }
        }

        argumentsList.simplify().print()
    }

}

data class Argument(val number: Int, val power: Int)

fun List<Argument>.simplify(): List<Argument> {
    val simplifiedList = mutableListOf<Argument>()
    val maxPower = this.maxBy { it.power }?.power ?: 0
    for (i in 0..maxPower) {
        var numberSum = 0
        this.forEach { if (it.power == i) numberSum += it.number }
        simplifiedList.add(Argument(numberSum, i))
    }
    return simplifiedList
}

fun List<Argument>.print() {
    this.forEach {

        if (it.number != 0) {

            if (it.number > 0) print("+")

            if (it.power == 0) print("${it.number}")
            else print("${it.number}x^${it.power}")
        }
    }
}