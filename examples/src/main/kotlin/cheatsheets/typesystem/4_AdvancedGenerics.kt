package cheatsheets.typesystem

import java.io.Serializable

open class Animal(var name: String)

class Dog(name: String, val chipped: Boolean) : Animal(name)
class Cat(name: String) : Animal(name)
open class Bird(name: String) : Animal(name)

class Eagle(name: String) : Bird(name)
class Canary(val green: Boolean) : Bird("Hansi")
class Penguin : Bird("n/a")


fun upperBounds() {
    // Ohne Upper bound, kann Cage alle Typen ennahmen, welche von
    // `Any` abgeleitet sind.
    class Cage<T>(private var animal: T, size: Int) {
        fun get(): T = animal
        fun put(animal: T) {
            this.animal = animal
        }
    }

    // z.B. ein String
    val cage = Cage<String>("Ein String?", 5)

    // --------------------------------------------------------------------------------------

    // Mit einem Upperbound (T: Supertype) kann man eine obere Typgrenze einführen:
    class UpperBoundCage<T: Animal>(private var animal: T, size: Int) {
        fun get(): T = animal
        fun put(animal: T) {
            this.animal = animal
        }
    }

    // Beispiel `Eagle`
    val bird: Bird = Canary(false)
    val dog: Dog = Dog("wuffi", false)

    val d = UpperBoundCage<Bird>(Eagle("Pupsi"), 5)
//    // schreiben
//    d.put(Eagle("Hansi"))
//    d.put(Canary(true))
//    d.put(bird)
//    d.put(dog)
//
//    // lesen
//    val a: Animal = d.get()
//    val b: Bird = d.get()
//    val c: Dog = d.get()
//    val e: Eagle = d.get()
//
//    // Polymorph?
//    val w: UpperBoundCage<Animal> = d
//    val y: UpperBoundCage<Bird> = d
//    val z: UpperBoundCage<Eagle> = d
//    val x: UpperBoundCage<Dog> = d
}

fun whereClause() {
    class WithWhere<T>() where T: Animal, T: Serializable {
        // ...
    }
}

// ============

interface Jsonable<T> {
    fun fromJson(value: String): T
}

class Customer: Jsonable<Customer> {
    override fun fromJson(value: String): Customer = TODO()
}

// Covariant
// Wenn T ein Subtyp von U ist, ist auch eine List<T> eine List<U>
// read only
// out

// Invariant
// Keine Subtistition möglich!

// Contravariant
// Wenn ein U der Supertyp von T ist, ist auch eine List<U> eine List<T>
// write only
// in