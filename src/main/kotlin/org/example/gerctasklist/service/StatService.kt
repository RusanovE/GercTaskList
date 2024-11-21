package org.example.gerctasklist.service

import org.springframework.http.ResponseEntity

interface StatService {

    fun getTaskStatistics(): Map<String, Any>

    fun exportStatisticsToCsv(): ResponseEntity<*>
}