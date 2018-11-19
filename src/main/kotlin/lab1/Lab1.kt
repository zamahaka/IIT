package lab1

private const val sentence = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
        "Pellentesque a tempor tellus. " +
        "Pellentesque in ligula tortor. " +
        "Vestibulum ut tortor dignissim, eleifend neque vel, ornare augue."


fun main(args: Array<String>) {
    val slittedWithoutDelimiters = sentence.removeDelimiters().splitByWhitespace()

    val originalCount = slittedWithoutDelimiters.countMap
    println("Original words count: $originalCount")

    val distinctResult = slittedWithoutDelimiters.distinct()
    println("Distinct words: $distinctResult")

    val distinctCount = distinctResult.countMap
    println("Distinct words count: $distinctCount")
}


private fun String.splitByWhitespace() = split(Regex("\\s+")).filter(String::isNotEmpty)

private fun String.removeDelimiters() = replace(Regex("[,.]"), "")

private val <T : Any> List<T>.countMap get() = groupingBy { it }.eachCount()