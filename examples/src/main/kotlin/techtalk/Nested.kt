package techtalk

class A {
    class B {
        class C
    }
}

// These methods can now be called on very List object
fun <T> List<T>.head(): T = this.first()
fun <T> List<T>.tail(): List<T> = this.drop(1)

fun sumRecursive(list: List<Int>): Int {

    // list recursive
    tailrec fun step(acc: Int, list: List<Int>): Int {
        return if (list.isEmpty()) acc
               else step(acc + list.head(), list.tail())
    }

    return step(0, list)
}