

fun main() {
    // Pair
    val equipment = "fish net" to "catching fish"
    println("${equipment.first} used for ${equipment.second}")

    val equipment1 = "fish net" to "catching fish"
    val (tool, use) = equipment
    println("$tool is used for $use")

    // Triples
    val numbers = Triple(6, 9, 42)
    println(numbers.toString())
    println(numbers.toList())
    println("${numbers.first}, ${numbers.second}, ${numbers.third}")

    val numbers1 = Triple(6, 9, 42)
    val (n1, n2, n3) = numbers
    println("$n1 $n2 $n3")
}