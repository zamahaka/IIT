package taras.lab2

fun main(args: Array<String>) {
    val list = (-5..5).map(function)
    println("List: ${(list.indices).zip(list).toMap()}")


    val result = list.filter { it >= 0.0 }?.run {
        max()?.let { this - it } ?: this
    }

    println("result: $result")
}