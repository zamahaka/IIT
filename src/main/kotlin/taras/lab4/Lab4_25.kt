package taras.lab4

import kotlin.random.Random
import kotlin.random.nextInt

fun main() {

    println("Enter first array size:")
    val firstList = SimpleArray(readLine()?.toIntOrNull() ?: 0)
    firstList.print("First array: ")


    println("Enter second array size:")
    val secondList = SimpleArray(readLine()?.toIntOrNull() ?: 0)
    secondList.print("First array: ")


    var methodId = 0
    readProperty(
        "Choose method to run: " +
                "1 - plus " +
                "2 - minus " +
                "3 - multiplication " +
                "4 - divide " +
                "5 - get by index "
    ) { enteredString ->
        methodId = enteredString.toIntOrNull() ?: 0
    }

    when (methodId) {

        1 -> firstList.plus(secondList)?.let { println("New list: $it") }
            ?: println("Lists have different size!")

        2 -> firstList.minus(secondList)?.let { println("New list: $it") }
            ?: println("Lists have different size!")

        3 -> {
            println("Enter value for multiplication: ")
            val value = readLine()?.toInt() ?: 0
            println("New first array: ${firstList.times(value)}")
            println()
            println("New second array: ${secondList.times(value)}")
            println()
        }

        4 -> {
            println("Enter value for divide: ")
            val value = readLine()?.toInt() ?: 0
            println("New first array: ${firstList.div(value)}")
            println()
            println("New second array: ${secondList.div(value)}")
            println()
        }

        5 -> {
            println("Enter index: ")
            val index = readLine()?.toInt() ?: 0

            firstList.get(index)?.let { println("In first list: $it") } ?: println("Not found in firs list!")
            println()
            secondList.get(index)?.let { println("In second list: $it") } ?: println("Not found in second list!")

        }


        else -> return
    }

}


class SimpleArray(size: Int) {

    var list: MutableList<Int> = mutableListOf()

    init {
        for (i in 0 until size) {
            list.add(Random.nextInt(0..100))
        }
    }

    operator fun plus(other: SimpleArray): List<Int>? {
        if (list.size != other.list.size) return null

        val newList = mutableListOf<Int>()
        list.forEachIndexed { index, item ->
            newList.add(item + other.list[index])
        }
        return newList
    }

    operator fun minus(other: SimpleArray): List<Int>? {
        if (list.size != other.list.size) return null

        val newList = mutableListOf<Int>()
        list.forEachIndexed { index, item ->
            newList.add(item - other.list[index])
        }
        return newList
    }

    operator fun times(number: Int) = list.map { it * number }

    operator fun div(number: Int) = list.map { it / number }


    fun get(index: Int): Int? = list.getOrNull(index)

    fun print(message: String = "") {
        println("$message $list")
    }

}