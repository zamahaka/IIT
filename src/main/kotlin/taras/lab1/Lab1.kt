package taras.lab1

private const val sentence = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
        "Pellentesque a tempor tellus. " +
        "Pellentesque in ligula tortor. " +
        "Vestibulum ut tortor dignissim, eleifend neque vel, ornare augue."


fun main(args: Array<String>) {
    val result = sentence.splitByWhitespace()
        .groupBy { it.removeDelimiters().removeWhitespace() }
        .map { (_, value) -> value.first() }
        .joinToString(separator = "") { "$it " }

    println(result)
}


private fun String.splitByWhitespace() = split(Regex("\\s++")).filter(String::isNotEmpty)

private fun String.removeDelimiters() = replace(Regex("[,.]"), "")
private fun String.removeWhitespace() = replace(Regex("\\s++"), "")
