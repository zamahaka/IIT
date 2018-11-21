package lab5

import java.util.*

class StackIntoQueueConverter<T> {
    fun convert(stack: Stack<T>): Queue<T> = ArrayDeque<T>(stack)
}

fun main() {
    print("Enter stack values, split by space ' ' : ")
    val values = readLine()?.split(Regex("\\s+"))?.takeIf { it.isNotEmpty() } ?: run {
        println("Invalid stack entered")
        return
    }

    val stack = Stack<Any>().apply { values.forEach { push(it) } }
    println("Entered stack: $stack")

    val queue = StackIntoQueueConverter<Any>().convert(stack)
    println("Converted queue: $queue")
}