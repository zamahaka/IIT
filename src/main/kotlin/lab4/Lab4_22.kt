package lab4

import taras.lab4.readProperty

fun main(args: Array<String>) {

    println("Enter first complex number:")
    println()
    val firsComplex = readComplex()

    println("Enter second complex number:")
    println()
    val secondComplex = readComplex()

    var methodId = 0
    readProperty(
        "Choose method to run: " +
                "1 - plus " +
                "2 - minus " +
                "3 - multiplication "
    ) { enteredString ->
        methodId = enteredString.toIntOrNull() ?: 0
    }

    when (methodId) {
        1 -> println("Result = ${firsComplex.plus(secondComplex)}")

        2 -> println("Result = ${firsComplex.minus(secondComplex)}")

        3 -> println("Result = ${firsComplex.times(secondComplex)}")

        else -> return
    }

}


data class Complex(val real: Double = 0.0, val imaginary: Double = 0.0) {

    operator fun plus(other: Complex): Complex =
        Complex(real + other.real, imaginary + other.imaginary)

    operator fun minus(other: Complex): Complex =
        Complex(real - other.real, imaginary - other.imaginary)

    operator fun times(other: Complex): Complex =
        Complex(
            (real * other.real) - (imaginary * other.imaginary),
            (real * other.imaginary) + (imaginary * other.real)
        )

}

fun readComplex(): Complex {
    var x = 0.0
    var y = 0.0
    readProperty("Enter real: ") { enteredString ->
        x = enteredString.toDoubleOrNull() ?: 0.0
    }
    readProperty("Enter imaginary: ") { enteredString ->
        y = enteredString.toDoubleOrNull() ?: 0.0
    }
    return Complex(x, y)
}