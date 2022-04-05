
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {


    println("_____ Coroutines")

    repeat(3) {
        GlobalScope.launch {
            println("Hi from ${Thread.currentThread()}")
        }
    }
}