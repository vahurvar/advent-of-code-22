import java.io.File

val input = """1000
2000
3000

4000

5000
6000

7000
8000
9000

10000"""

fun getCaloriesByElf(input: String): List<Int> {
    return input.split("\n\n") // Empty line
        .map {
            it.split("\n").sumOf { line -> line.toInt() }
        }
}

fun main() {
    val testCalories = getCaloriesByElf(input)
    println(testCalories)
    println(testCalories.max())
    assert(testCalories.max() == 24000)

    val textInput = File("input.txt").readText().trim()
    val caloriesPerElf = getCaloriesByElf(textInput)

    println(caloriesPerElf.max()) // 69795
    println(caloriesPerElf.sortedDescending().take(3).sum()) // 208437
}

main()