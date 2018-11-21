package lab3

const val NO_DATA_SUPPLIED = "No data supplied"

open class Consumer {

    var name: String = NO_DATA_SUPPLIED
    var street: String = NO_DATA_SUPPLIED
    var city: String = NO_DATA_SUPPLIED
    var state: String = NO_DATA_SUPPLIED
    var zip: String = NO_DATA_SUPPLIED


    fun outputData() {
        writeProperty("Name: ", this::name.getter)
        writeProperty("Street: ", this::street.getter)
        writeProperty("City: ", this::city.getter)
        writeProperty("State: ", this::state.getter)
        writeProperty("Zip: ", this::zip.getter)
    }

    fun writeProperty(message: String, get: () -> Any) {
        print("$message${get()}")
        println()
    }


    fun inputData() {
        readProperty("Enter Consumer's Name: ", this::name.setter)
        readProperty("Enter Consumer's Street: ", this::street.setter)
        readProperty("Enter Consumer's City: ", this::city.setter)
        readProperty("Enter Consumer's State: ", this::state.setter)
        readProperty("Enter Consumer's Zip: ", this::zip.setter)
    }

    fun readProperty(message: String, set: (String) -> Unit) {
        print(message)
        set(readLine() ?: NO_DATA_SUPPLIED)
        println()
    }

}

class Airline : Consumer() {

    var airlineType: String = NO_DATA_SUPPLIED
    var accAirMiles: Float = 0f


    fun airlineConsumer() {
        inputData()

        readProperty("Enter AirLine Type: ", this::airlineType.setter)
        readProperty("Enter Air Mileage: ") { enteredString ->
            accAirMiles = enteredString.toFloatOrNull() ?: 0f
        }
    }

    fun displayAirMileage() {
        outputData()

        writeProperty("Airline Type: ", this::airlineType.getter)
        writeProperty("Air Mileage: ", this::accAirMiles.getter)
    }

}

class RentalCar : Consumer() {

    var carType: String = NO_DATA_SUPPLIED
    var roadMiles: Float = 0f


    fun rentalCarConsumer() {
        inputData()

        readProperty("Enter Rental car Type: ", this::carType.setter)
        readProperty("Enter Road mileage: ") { enteredString ->
            roadMiles = enteredString.toFloatOrNull() ?: 0f
        }
    }

    fun displayRoadMileage() {
        outputData()

        writeProperty("Rental Car Type: ", this::carType.getter)
        writeProperty("Accumulated Mileage: ", this::roadMiles.getter)
    }

}

fun main() {

    print("Enter Airline count: ")
    val airlineCount = readLine()?.toIntOrNull() ?: 1
    println()

    val airlines = (0 until airlineCount).map {
        println("Airline Consumer")

        Airline().apply { airlineConsumer() }
    }

    print("Enter Rental Car count: ")
    val carsCount = readLine()?.toIntOrNull() ?: 1
    println()

    val cars = (0 until carsCount).map {
        println("Rental Car Consumer")

        RentalCar().apply { rentalCarConsumer() }
    }

    println("Working with ${airlines.size + 1} airlines")
    airlines.forEachIndexed { index, airline ->
        println("Airline $index")
        airline.displayAirMileage()
    }
    println()

    println("Working with ${cars.size + 1} rental cars")
    cars.forEachIndexed { index, car ->
        println("Cars $index")
        car.displayRoadMileage()
    }
    println()

}