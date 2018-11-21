package lab5

private class MyLinearList<T> : MutableList<T> by ArrayList()

data class MatrixRow<T>(val values: List<T>)

class Matrix<T>(val rows: List<MatrixRow<T>>)


fun main() {
    print("Enter row count: ")
    val rowsCount = readLine()?.toIntOrNull() ?: run {
        println("Wrong number supplied")
        return
    }

    print("Enter matrix rows, split by space ' ' : ")
    val rows = (0 until rowsCount).map readRows@{ index ->
        print("\nEnter row at $index: ")
        val rowsString = readLine() ?: run {
            println("Invalid line supplied")
            return
        }

        val numbers = rowsString.split(Regex("\\s+")).map { numberString ->
            numberString.toIntOrNull() ?: run {
                println("Invalid number supplied $numberString")
                return
            }
        }

        return@readRows MatrixRow(numbers)
    }

    val matrix = Matrix(rows)

    print("Enter rows to save, split by space ' ' : ")
    val rowsToSave = readLine()?.split(Regex("\\s+"))?.mapNotNull(String::toIntOrNull) ?: run {
        println("Invalid indexes supplied")
        return
    }

    val savedList = rowsToSave.map { matrix.rows[it - 1] }.let { savedRows ->
        MyLinearList<MatrixRow<Int>>().apply { addAll(savedRows) }
    }

    println("Saved rows: ")

    savedList.forEachIndexed { index, row ->
        println("Row at $index = $row")
    }
}