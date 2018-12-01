package lab7

import java.io.FileReader

//false < true => true
fun main(args: Array<String>) {
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

