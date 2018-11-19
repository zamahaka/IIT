package lab2

fun main(args: Array<String>) {
    val (min, max, third) = (1..14)
        .map(taras.lab2.function)
        .let { Triple(it.min() ?: it.first(), it.max() ?: it.first(), it[2]) }

    println("result: ${third % (min + max)}")
}