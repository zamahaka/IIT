package lab7

fun String.beginsWithMultiLetterWord() = !beginsWithSingleLetterWord()
fun String.beginsWithSingleLetterWord() = splitByWhitespace().firstOrNull()?.length == 1
fun String.splitByWhitespace() = split(Regex("\\s+")).filter(String::isNotEmpty)