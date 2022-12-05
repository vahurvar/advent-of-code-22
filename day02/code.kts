import java.io.File

val input = """A Y
B X
C Z"""

enum class Shape(val symbol: Char, val encryptedSymbol: Char, val score: Int) {
    ROCK('A', 'X', 1) {
        override fun beats(): Shape = SCISSORS
        override fun loses(): Shape = PAPER
    },
    PAPER('B', 'Y', 2) {
        override fun beats(): Shape = ROCK
        override fun loses(): Shape = SCISSORS
    },
    SCISSORS('C', 'Z', 3) {
        override fun beats(): Shape = PAPER
        override fun loses(): Shape = ROCK
    };

    abstract fun beats(): Shape
    abstract fun loses(): Shape

    companion object {
        fun fromSymbol(symbol: Char): Shape {
            return Shape.values().find { it.symbol == symbol } ?: throw RuntimeException()
        }

        fun fromEncryptedSymbol(encryptedSymbol: Char): Shape {
            return Shape.values().find { it.encryptedSymbol == encryptedSymbol } ?: throw RuntimeException()
        }
    }
}

enum class Result(val score: Int, val symbol: Char) {
    WIN(6, 'Z'), LOSS(0, 'X'), DRAW(3, 'Y');

    companion object {
        fun getFromSymbol(symbol: Char): Result {
            return Result.values().find { it.symbol == symbol } ?: throw RuntimeException()
        }
    }
}

fun determineResult(opponent: Shape, us: Shape): Result {
    if (opponent == us) {
        return Result.DRAW
    }
    return if (opponent.loses() == us) Result.WIN else Result.LOSS
}

fun getRoundScore(opening: Char, response: Char): Int {
    val opponent = Shape.fromSymbol(opening)
    val us = Shape.fromEncryptedSymbol(response)
    val result = determineResult(opponent, us)
    return us.score + result.score
}

fun solveFirst(input: List<String>): Int {
    return input.sumOf { getRoundScore(it[0], it[2]) }
}

fun determineShapeBasedOnResult(opponent: Shape, result: Result): Shape {
    return when (result) {
        Result.WIN -> opponent.loses()
        Result.LOSS -> opponent.beats()
        Result.DRAW -> opponent
    }
}

fun getRoundScore2(opening: Char, result: Char): Int {
    val opponent = Shape.fromSymbol(opening)
    val res = Result.getFromSymbol(result)
    val ourShape = determineShapeBasedOnResult(opponent, res)
    return ourShape.score + res.score
}

fun solveSecond(input: List<String>): Int {
    return input.sumOf { getRoundScore2(it[0], it[2]) }
}

fun main() {
    println(solveFirst(input.split("\n"))) // 15
    val lines = File("input.txt").readLines()
    println(solveFirst(lines)) // 13446
    println(solveSecond(lines)) // 13509
}

main()
