package Loop

fun main() {

    // Foreach loop
    val lists = listOf("a", "b", "c")
    for (s in lists)
        println(s)

    // foreach with Index
    for ((index, s) in lists.withIndex()) {
        println("$index $s")
    }

    // Iterating over range
    // Include upper bond
    for (i in 1..9) print(i)

    // Iterating over range
    // exclude upper bond
    for (i in 1 until 9) print(i)

    // Iterating with a step
    for (i in 9 downTo 1 step 2) print(i)

    for (i in 'd'..'g') print(i)

    //Iterating over map
    val map = mapOf<Int, String>(1 to "one", 2 to "two", 3 to "three")
    for((key: Int, value: String) in map){
        println("$key : $value")
    }

    // Iterate with Index
    val lists1 = listOf("a","b","c")
    for ((index, element) in lists1.withIndex()){
        println("$index: $element")
    }
}