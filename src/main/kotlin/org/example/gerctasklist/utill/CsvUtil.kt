package org.example.gerctasklist.utill

import com.opencsv.CSVWriter
import java.io.FileWriter

object CsvUtil {
    fun writeCsv(filePath: String, headers: Array<String>, data: List<Array<String>>) {
        val writer = CSVWriter(FileWriter(filePath))
        writer.writeNext(headers)
        data.forEach { writer.writeNext(it) }
        writer.close()
    }
}
