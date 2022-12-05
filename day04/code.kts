import java.io.File

val input = """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8""".split("\n")

fun convertPairToRanges(line: String): Pair<IntRange, IntRange> {
    val (first, second) = line.split(',')

    val (firstRangeStart, firstRangeClose) = first.split('-')
    val firstRange = firstRangeStart.toInt()..firstRangeClose.toInt()

    val (secondRangeStart, secondRangeClose) = second.split('-')
    val secondRange = secondRangeStart.toInt()..secondRangeClose.toInt()

    return firstRange to secondRange
}

fun solveFirst(lines: List<String>): Int {
    return lines
        .map { convertPairToRanges(it) }
        .map {
            val intersectSize = it.first.intersect(it.second).size
            intersectSize == it.first.count() || intersectSize == it.second.count()
        }
        .filter { it }
        .size
}

fun solveSecond(lines: List<String>): Int {
    return lines
        .map { convertPairToRanges(it) }
        .map { it.first.intersect(it.second) }
        .filter { it.isNotEmpty() }
        .size
}

fun main() {
    println(solveFirst(input)) // 2

    val lines = File("input.txt").readLines()
    println(solveFirst(lines)) // 576

    println(solveSecond(input)) // 4
    println(solveSecond(lines)) // 905
}

main()
