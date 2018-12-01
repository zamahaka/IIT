package lab6

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.FileReader
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

private data class Train(
    @SerializedName("number") val number: Int,
    @SerializedName("station") val station: String,
    @SerializedName("departure_time") val departureTime: String
) {

    companion object {
        fun fromConsole(): Train {
            print("Enter train number: ")
            val number = readLine()?.toIntOrNull() ?: run {
                println("Illegal train number supplied")
                throw IllegalArgumentException("Illegal train number")
            }

            print("Enter train station: ")
            val station = readLine() ?: run {
                println("Illegal train station supplied")
                throw IllegalArgumentException("Illegal train station")
            }

            print("Enter train departure time: ")
            val departureTime = readLine() ?: run {
                println("Illegal train departure time supplied")
                throw IllegalArgumentException("Illegal train departure time")
            }

            return Train(number, station, departureTime)
        }
    }

}


private object TrainReader {

    fun readFromConsole(): Collection<Train> {
        val trains = mutableSetOf<Train>()

        do {
            trains.add(Train.fromConsole())

            println("To enter another train enter yes, no otherwise.")
        } while (readLine() == "yes")

        return trains
    }

    fun readFromFile(): Collection<Train> {
        val file = JFileChooser(FileSystemView.getFileSystemView().defaultDirectory).run {
            if (showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile
            } else null
        } ?: run {
            println("No or invalid file selected!")
            return emptySet()
        }


        val reader = BufferedReader(
            FileReader(file)
        )


        return Gson().fromJson(reader, object : TypeToken<Set<Train>>() {}.type)
    }

}


private object Commands {
    const val EXIT = "exit"

    val ALL = listOf("print all trains", "all", "all trains", "print all")
    val BY_NUMBER = listOf("number", "by number", "print by number")
    val FOR_STATION = listOf("station", "for station")
}


fun main(args: Array<String>) {

    print("Enter source of trains (file, console): ")
    val trains = when (readLine()) {
        "file" -> TrainReader.readFromFile()
        "console" -> TrainReader.readFromConsole()

        else -> {
            println("Invalid option selected. Program will exit.")
            return
        }
    }

    do {
        println(
            "\nEnter command. One of: " +
                    "${Commands.ALL.joinToString()}, " +
                    "${Commands.BY_NUMBER.joinToString()}, " +
                    "${Commands.FOR_STATION.joinToString()}. " +
                    "Or print ${Commands.EXIT} to exit the program."
        )

        val command = readLine()

        when (command) {
            in Commands.ALL -> printAllTrains(from = trains)
            in Commands.BY_NUMBER -> printTrainForNumber(from = trains)
            in Commands.FOR_STATION -> printTrainsForStation(from = trains)
        }

    } while (command != Commands.EXIT)

}

private fun printAllTrains(from: Collection<Train>) = println("All trains: $from")

private fun printTrainForNumber(from: Collection<Train>) {
    print("Enter train number: ")
    val trainNumber = readLine()?.toIntOrNull() ?: run {
        println("Invalid train number supplied!")
        return
    }

    println(from.find { it.number == trainNumber } ?: "No train found for number $trainNumber")
}

private fun printTrainsForStation(from: Collection<Train>) {
    print("Enter station name: ")
    val stationName = readLine() ?: run {
        println("Invalid station name supplied!")
        return
    }

    println(
        from.filter { it.station == stationName }.takeIf(List<Train>::isNotEmpty)
            ?: "No trains found for station $stationName"
    )
}