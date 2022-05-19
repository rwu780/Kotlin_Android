package PrimitiveTypes

fun main() {

    val a: String = "String"

    if (a is String){
        println(a)
    }

    val s: String? = "This should be a string" as? String

    s?.let {
        println(s)
    } ?: run {
        println("It is not a string")
    }

}
