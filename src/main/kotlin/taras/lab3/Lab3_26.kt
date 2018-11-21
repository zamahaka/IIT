package taras.lab3


fun main() {
    val results = Elector(candidates, cities).elect()

    println("Results:")
    results.printResults()
    println()

    val thirdCandidate = candidates[2]
    println("Max cities for candidate ${thirdCandidate.name}:")
    results.printMaxFor(thirdCandidate)
}
