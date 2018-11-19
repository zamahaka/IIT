package taras.lab2

@Suppress("DestructuringWrongName")
fun main(args: Array<String>) {
    val list = (-5..5).map(function)
    println("List: ${(list.indices).zip(list).toMap()}")


    val prelastPositive = list.filter { it >= 0.0 }.takeIf { it.size > 2 }?.dropLast(1)?.last()

    val index = prelastPositive?.let { list.indexOf(it) }

    println("result: $index")
}