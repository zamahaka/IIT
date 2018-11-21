package taras.lab3

import kotlin.random.Random


const val k = 5

val cities = (0 until k).map(Int::inc).map { "V$it" }.map(::City)
val candidates = (0 until k - 1).map(Int::inc).map { "C$it" }.map(::Candidate)

interface HasName {
    val name: String
}

data class City(override val name: String) : HasName

data class Candidate(override val name: String) : HasName


class Elector(
    private val candidates: List<Candidate>,
    private val cities: List<City>
) {

    private val random = Random(System.currentTimeMillis())


    fun elect(): ElectorResults = ElectorResults(
        candidates.associateWith { candidate ->
            cities.associateWith { random.nextInt(10 * candidates.indexOf(candidate) + 50) }
        }
    )

}

data class ElectorResults(
    private val results: Map<Candidate, Map<City, Int>>
) {

    private val candidates = results.keys.toList()
    private val cities = results.flatMap { (_, cities) -> cities.keys }.distinct()


    fun printResults() {
        println(candidates.joinToString(separator = "\t", prefix = "\t") { it.name })

        cities.forEach { city ->
            print("${city.name}\t")

            candidates.forEach { candidate -> print("${results[candidate]!![city]!!}\t") }

            println()
        }
    }

    fun printMaxFor(candidate: Candidate) {
        val cityCandyRes = mutableMapOf<City, MutableMap<Candidate, Int>>()
        results.forEach { candy, cityRes ->
            cityRes.forEach { city, result ->
                cityCandyRes.getOrPut(city) { mutableMapOf() }
                    .also { it[candy] = result }
            }
        }


        val maxCandyPerCity = cityCandyRes.mapValues { (_, candyRes) ->
            val max = candyRes.values.max()

            val maxCandy = (max?.let { candyRes.filterKeys { candidate -> candyRes[candidate] == max } } ?: candyRes)
                .keys.first()

            maxCandy
        }

        print(maxCandyPerCity.filterValues { it == candidate }.keys.map(City::name))
    }

    fun findMinCandidate(): Candidate {
        val sumPerCandy = results.mapValues { (candy, cityRes) ->
            val sum = cityRes.values.sum()
            println("Sum for candidate ${candy.name} is $sum")

            sum
        }

        val minRes = sumPerCandy.values.min()

        return (minRes?.let { sumPerCandy.filterValues { it == minRes } } ?: sumPerCandy)
            .keys.first()
    }

}