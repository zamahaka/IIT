package lab6

import kotlin.math.absoluteValue
import kotlin.math.max


private data class Flat(
    val roomCount: Int,
    val area: Float,
    val floor: Int,
    val region: String
) {
    fun canBeExchanged(with: Flat): Boolean = when {
        roomCount != with.roomCount -> false
        floor != with.floor -> false
        (area - with.area).absoluteValue !in
                0f..(max(area, with.area) * 0.10f) -> false

        else -> true
    }
}

private data class Person(
    val firstName: String,
    val lastName: String
)

private data class Order(
    val person: Person,
    val searchedFlat: Flat,
    val proposedFlat: Flat
) {
    fun isMatching(`for`: Order): Boolean =
        searchedFlat.canBeExchanged(with = `for`.proposedFlat) && proposedFlat.canBeExchanged(with = `for`.searchedFlat)
}

private object Command {
    const val EXIT = "exit"

    val ADD_ORDER = listOf("add", "add order")
    val FIND_ORDER = listOf("find", "find order", "order")
    val PRINT_ALL = listOf("all", "all orders", "orders")
}


fun main() {
    val orders = mutableListOf<Order>(
        Order(
            person = Person(firstName = "Y", lastName = "S"),
            searchedFlat = Flat(
                roomCount = 2,
                area = 100f,
                floor = 2,
                region = "L"
            ),
            proposedFlat = Flat(
                roomCount = 3,
                area = 95f,
                floor = 3,
                region = "K"
            )
        ),
        Order(
            person = Person(firstName = "T", lastName = "S"),
            searchedFlat = Flat(
                roomCount = 3,
                area = 94f,
                floor = 3,
                region = "K"
            ),
            proposedFlat = Flat(
                roomCount = 2,
                area = 101f,
                floor = 2,
                region = "L"
            )
        )
    )

    do {
        println(
            "\nEnter command. One of: " +
                    "${Command.ADD_ORDER.joinToString()}, " +
                    "${Command.FIND_ORDER.joinToString()}, " +
                    "${Command.PRINT_ALL.joinToString()}. " +
                    "Or print ${Command.EXIT} to exit the program."
        )

        val command = readLine()

        when (command) {
            in Command.ADD_ORDER -> readOrder()?.let { enteredOrder ->
                orders += enteredOrder

                considerMatch(orders, enteredOrder)
            }

            in Command.FIND_ORDER -> readPerson()?.let { person ->
                orders.findForPerson(person)?.let { personOrder ->
                    considerMatch(orders, personOrder)
                } ?: run { println("No order found for $person.") }
            }

            in Command.PRINT_ALL -> println("All orders: $orders.")
        }

    } while (command != Command.EXIT)

}


private fun readOrder(): Order? {
    val person = readPerson() ?: return null

    println("Enter flat to search for:")
    val flatToSearch = readFlat() ?: return null

    println("Enter flat to exchange:")
    val flatToExchange = readFlat() ?: return null

    return Order(person, flatToSearch, flatToExchange)
}

private fun readPerson(): Person? {
    print("Enter your name: ")

    return readLine()
        ?.split(Regex("\\s+"))
        ?.takeIf { it.isNotEmpty() }
        ?.let {
            if (it.size < 2) it + listOf("", "") else it
        }
        ?.let { Person(firstName = it[0], lastName = it[1]) }
        ?: run { println("Invalid name entered!"); null }
}

private fun readFlat(): Flat? {
    print("Enter flat room count: ")
    val roomCount = readLine()?.toIntOrNull() ?: run {
        println("Invalid room count entered!")
        return null
    }

    print("Enter flat area: ")
    val area = readLine()?.toFloatOrNull() ?: run {
        println("Invalid flat area entered!")
        return null
    }

    print("Enter flat floor: ")
    val floor = readLine()?.toIntOrNull() ?: run {
        println("Invalid flat floor entered!")
        return null
    }

    print("Enter flat region: ")
    val region = readLine() ?: run {
        println("Invalid flat region entered!")
        return null
    }

    return Flat(roomCount, area, floor, region)
}


private fun List<Order>.findForPerson(person: Person) = find { it.person == person }

private fun List<Order>.findMatchingOrder(`for`: Order): Order? = find { it.isMatching(`for` = `for`) }


private fun considerMatch(orders: MutableList<Order>, personOrder: Order) {
    orders.findMatchingOrder(`for` = personOrder)?.let { matchingOrder ->
        println("We found an order for You! $matchingOrder")

        orders -= listOf(matchingOrder, personOrder)
    } ?: run { println("No matching orders found.") }
}
