package lab2

@Suppress("DestructuringWrongName")
fun main(args: Array<String>) {
    val (max, third) = (1..14)
        .map(taras.lab2.function)
        .let { (it.max() ?: 0.0).coerceAtLeast(0.0) to it[2] }

    println("result: ${third * max}")
}