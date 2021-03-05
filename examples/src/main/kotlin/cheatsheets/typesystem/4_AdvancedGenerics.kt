package cheatsheets.typesystem

open class Animal(var name: String)

class Dog(name: String, val chipped: Boolean) : Animal(name)
class Cat(name: String) : Animal(name)
open class Bird(name: String) : Animal(name)

class Eagle(name: String) : Bird(name)
class Canary(val green: Boolean) : Bird("Hansi")
class Penguin : Bird("n/a")


fun invariantGenerics() {
    class InvariantCage<T>(private var animal: T, size: Int) {
        fun get(): T = animal

        fun rename(animal: T) {
            (this.animal as Animal).name = (animal as Animal).name
        }
    }

    val dog = Dog("Wuffi", false)
    var anyCage: InvariantCage<Any> = InvariantCage(Object(), 6)
    var dogCage: InvariantCage<Dog> = InvariantCage(dog, 5)

    // anyCage = dogCage // --> funktioniert nicht
    // var animalCage: InvariantCage<Animal> = dogCage
}

fun covariantGenerics() {
    class CovariantCage<out T: Animal>(private var animal: T, size: Int) {
        fun get(): T = animal

        fun rename(animalName: String) {
            (this.animal as Animal).name = animalName
        }
    }

    val dog = Dog("Wuffi", false)

    var dogCage: CovariantCage<Dog> = CovariantCage(dog, 5)
    var animalCage: CovariantCage<Animal> = dogCage
}

fun contravariantGenerics() {
    class ContravariantCage<in T: Animal>(private var animal: T, size: Int) {
        fun getName(): String = animal.name

        fun rename(animal: T) {
            this.animal.name = animal.name
        }
    }

    val dog = Dog("Wuffi", false)

    var dogCage: ContravariantCage<Dog> = ContravariantCage(dog, 5)
    // var animalCage: ContravariantCage<Animal> = dogCage
}

