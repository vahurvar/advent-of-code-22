import java.io.File

val input1 = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw""".split("\n")

val input2 = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw""".split("\n")

fun getItemPriority(item: Char): Int {
    val a = 1
    a + 2
    return when (item) {
        in 'a'..'z' -> (item - 'a') + 1
        in 'A'..'Z' -> (item - 'A') + 27
        else -> throw RuntimeException("Invalid item $item")
    }
}

fun getCompartments(items: String): Pair<String, String> {
    val pivot = items.length / 2
    return items.substring(0, pivot) to items.substring(pivot)
}

fun findCommonElementsInBothCompartments(compartments: Pair<String, String>): Set<Char> {
    val firstItems = compartments.first.toSet()
    val secondItems = compartments.second.toSet()
    return firstItems.intersect(secondItems)
}

fun solveFirst(lines: List<String>): Int {
    return lines.map { getCompartments(it) }
        .map { findCommonElementsInBothCompartments(it) }
        .sumOf { it.sumOf(::getItemPriority) }
}

fun findCommon(list: List<String>): Char {
    return list.map { it.toSet() }
        .reduce { acc, it -> acc.intersect(it) }
        .first()
}

fun solveSecond(lines: List<String>): Int {
    return lines.windowed(size = 3, step = 3)
        .map { findCommon(it) }
        .sumOf { getItemPriority(it) }
}

fun main() {
    val testAnswer = solveFirst(input1)
    println(testAnswer)
    assert(testAnswer == 157)

    val lines = File("input1.txt").readLines()
    println(solveFirst(lines)) // 7674

    val lines2 = File("input2.txt").readLines()
    println(solveSecond(lines2)) // 2805
}

main()
