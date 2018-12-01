package lab7

import java.io.FileReader


private val vowels = listOf<Char>(
    'a', 'e', 'i', 'o', 'u', 'y', //English
    'а', 'о', 'у', 'і', 'и', 'е', 'я', 'ю', 'є', 'ї' //Ukrainian
)


fun main(args: Array<String>) {
    val capitalizedSentences = getFile()?.let { file ->
        FileReader(file)
            .buffered()
            .lineSequence()
            .toList()
            .reversed()
            .asSequence()
            .map(String::capitalizeVowel)
    }

    capitalizedSentences?.forEach { sentence ->
        println(sentence)
    } ?: println("No sentences found!")

}

private fun String.capitalizeVowel() = asSequence()
    .map { if (it.isVowel()) it.toUpperCase() else it }
    .joinToString(separator = "")

private fun Char.isVowel(): Boolean = toLowerCase() in vowels