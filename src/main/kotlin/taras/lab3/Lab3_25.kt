package taras.lab3


fun main(args: Array<String>) {
    val results = Elector(candidates, cities).elect()

    println("Results:")
    results.printResults()
    println()

    println("Looser is: ${results.findMinCandidate().name}")
}

