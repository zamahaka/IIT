package lab7

import java.io.FileReader

//false < true => true
fun main() {
    val sentenceSequence = getFile()?.let { file ->
        FileReader(file)
            .buffered()
            .lineSequence()
            .sortedBy(String::beginsWithMultiLetterWord)
    }

    sentenceSequence?.forEach { sentence ->
        println(sentence)
    } ?: println("No sentences found!")
}

