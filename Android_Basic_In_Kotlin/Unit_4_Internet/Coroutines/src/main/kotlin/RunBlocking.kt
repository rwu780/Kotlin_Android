import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val formatter = DateTimeFormatter.ISO_LOCAL_TIME
val time = { formatter.format(LocalDateTime.now()) }

suspend fun getValue(): Double{

    println("entering getValue() at ${time()}")
    delay(3000)
    println("leaving getValue() at ${time()}")
    return Math.random()

}

fun main() {

    runBlocking {

        // Sequence order
        println("Sequential Order")
        val num1 = getValue()
        val num2 = getValue()
        println("result of num1 + num2 = ${num1 + num2}")

        println("Async Called")
        val num3 = async { getValue() }
        val num4 = async { getValue() }
        println("result of num3 + num4 = ${num3.await() + num4.await()}")
    }

}