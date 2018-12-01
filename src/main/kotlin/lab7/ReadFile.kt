package lab7

import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

fun getFile(): File? = JFileChooser(FileSystemView.getFileSystemView().defaultDirectory).run {
    if (showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        selectedFile
    } else null
} ?: run {
    println("No or invalid file selected!")
    null
}