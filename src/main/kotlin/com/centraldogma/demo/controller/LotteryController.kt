package com.centraldogma.demo.controller

import com.centraldogma.demo.service.LotteryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LotteryController(private val lotteryService: LotteryService) {
    companion object {
        private const val WIN_MESSAGE = "You win!"
        private const val LOSE_MESSAGE = "You lose..."
    }

    @GetMapping("/draw")
    fun draw(): String {
        return if (lotteryService.draw()) {
            WIN_MESSAGE
        } else {
            LOSE_MESSAGE
        }
    }
}