package taras.lab4

import java.awt.Point
import kotlin.math.cos
import kotlin.math.sin
import javax.swing.Spring.height
import kotlin.math.roundToInt


fun main() {

    var shapeId = 0
    readProperty(
        "Choose shape to create: " +
                "1 - Circle " +
                "2 - Square " +
                "3 - Rectangle "
    ) { enteredString ->
        shapeId = enteredString.toIntOrNull() ?: 0
    }

    val shape = when (shapeId) {
        1 -> Circle()
        2 -> Square()
        3 -> Rectangle()
        else -> return
    }

    shape.create()

    var methodId = 0
    readProperty(
        "Choose method to run: " +
                "1 - rotate " +
                "2 - translate " +
                "3 - scale "
    ) { enteredString ->
        methodId = enteredString.toIntOrNull() ?: 0
    }

    when (methodId) {
        1 -> readProperty("Enter degrees: ") { enteredString ->
            shape.rotate(enteredString.toIntOrNull() ?: 0)
        }

        2 -> shape.translate(readPoint())

        3 -> readProperty("Enter percentage: ") { enteredString ->
            shape.scale(enteredString.toIntOrNull() ?: 0)
        }

        else -> return
    }


}

interface Shape {
    fun create()
    fun rotate(degrees: Int)
    fun translate(point: Point)
    fun scale(percentage: Int)
}

class Circle : Shape {

    private var center = Point(0, 0)
    private var radius: Int = 0

    override fun create() {
        center = readPoint()
        readProperty("Enter radius: ") { enteredString ->
            radius = enteredString.toIntOrNull() ?: 0
        }

        println(
            "Center $center), " +
                    "radius $radius"
        )
    }

    override fun rotate(degrees: Int) {
        println(
            "Center after rotation: $center, " +
                    "radius after rotation $radius"
        )
    }

    override fun translate(point: Point) {
        val newCenter = Point(center.x + point.x, center.y + point.y)
        println(
            "Center after translate is: $newCenter, " +
                    "radius is $radius"
        )
    }

    override fun scale(percentage: Int) {
        if (percentage <= 0) {
            println("Wrong scale percentage!")
            return
        }

        val newRadius = radius * (percentage.toFloat() / 100)
        println(
            "Center after scale: $center), " +
                    "radius after scale $newRadius"
        )
    }

}

abstract class SquareShape : Shape {

    private var startPoint = Point(0, 0)
    protected var height = 0
    protected var width = 0

    override fun create() {
        startPoint = readPoint()
    }

    override fun rotate(degrees: Int) {
        if (degrees <= 0) {
            println("Wrong degrees!")
            return
        }
        val centerX = startPoint.x + (width / 2)
        val centerY = startPoint.y + (height / 2)

        val angle = Math.toRadians(degrees.toDouble())

        calculatePoints().map {
            Point(
                ((it.x - centerX) * Math.cos(angle) - (it.y - centerY) * Math.sin(angle) + centerX).roundToInt(),
                ((it.y - centerY) * Math.cos(angle) + (it.x - centerX) * Math.sin(angle) + centerY).roundToInt()
            )
        }.printPoints()


    }

    override fun translate(point: Point) {
        calculatePoints().map { Point(it.x + point.x, it.y + point.y) }.printPoints()
    }

    override fun scale(percentage: Int) {
        if (percentage <= 0) {
            println("Wrong scale percentage!")
            return
        }

        val newHeight = height * (percentage.toFloat() / 100)
        val newWidth = width * (percentage.toFloat() / 100)

        calculatePoints(startPoint, newWidth.toInt(), newHeight.toInt())
    }

    protected fun calculatePoints(
        start: Point = startPoint,
        newWidth: Int = width,
        newHeight: Int = height

    ): List<Point> =
        mutableListOf<Point>().apply {
            add(start)
            add(Point(start.x, start.y + newHeight))
            add(Point(start.x + newWidth, start.y + newHeight))
            add(Point(start.x + newWidth, start.y))
        }.toList()

}

class Square : SquareShape() {

    override fun create() {
        super.create()
        readProperty("Enter side length: ") { enteredString ->
            val length = enteredString.toIntOrNull() ?: 0
            height = length
            width = length
        }

        calculatePoints().printPoints()
    }
}

class Rectangle : SquareShape() {

    override fun create() {
        super.create()
        readProperty("Enter width: ") { enteredString ->
            width = enteredString.toIntOrNull() ?: 0
        }

        readProperty("Enter height: ") { enteredString ->
            height = enteredString.toIntOrNull() ?: 0
        }

        calculatePoints().printPoints()
    }
}


fun readProperty(message: String, set: (String) -> Unit) {
    print(message)
    set(readLine() ?: "")
    println()
}

fun readPoint(): Point {
    var x = 0
    var y = 0
    readProperty("Enter point x: ") { enteredString ->
        x = enteredString.toIntOrNull() ?: 0
    }
    readProperty("Enter point y: ") { enteredString ->
        y = enteredString.toIntOrNull() ?: 0
    }
    return Point(x, y)
}


fun List<Point>.printPoints() {
    this.forEachIndexed { index, point ->
        println("Point_$index - $point")
    }
}