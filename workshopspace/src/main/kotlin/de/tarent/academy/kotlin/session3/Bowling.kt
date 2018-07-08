package de.tarent.academy.kotlin.session3

data class Roll(val pins: Int)

sealed class Frame


sealed class IncompleteFrame : Frame() {
    abstract fun addRoll(roll: Roll): Frame
}

object NewFrame : IncompleteFrame() {
    override fun addRoll(roll: Roll): Frame {
        return if (roll.pins == 10) {
            Strike
        } else {
            FirstRoll(roll)
        }
    }
}

data class FirstRoll(val rollOne: Roll) : IncompleteFrame() {
    override fun addRoll(roll: Roll): Frame {
        return if (rollOne.pins + roll.pins == 10) {
            Spare(rollOne, roll)
        } else {
            NormFrame(rollOne, roll)
        }
    }
}


sealed class CompleteFrame : Frame() {
    abstract fun points(): Int
}


data class NormFrame(val rollOne: Roll, val rollTwo: Roll) : CompleteFrame() {
    override fun points(): Int = rollOne.pins + rollTwo.pins

}

object Strike : CompleteFrame() {
    override fun points(): Int = 10
}

data class Spare(val rollOne: Roll, val rollTwo: Roll) : CompleteFrame() {
    override fun points(): Int = 10
}


sealed class Game {
    abstract fun addRoll(roll: Roll): Game

    abstract fun getPoints(): Int
}

object NewGame : Game() {
    override fun getPoints(): Int = 0

    override fun addRoll(roll: Roll): Game = OpenGame(listOf(), NewFrame.addRoll(roll))
}

data class OpenGame(val finishedFrames: List<Frame> = listOf(), val actualFrame: Frame) : Game() {
    override fun getPoints(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addRoll(roll: Roll): Game = when(actualFrame) {
        is IncompleteFrame -> this.copy(actualFrame =  actualFrame.addRoll(roll))
        else -> TODO()
    }

/*
        val frame = NewFrame.addRoll(roll)

        when(frame) {
            is CompleteFrame -> finishedFrames = finishedFrames + frame
            is IncompleteFrame ->
        }*/
}

class FinishedGame : Game() {
    override fun getPoints(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addRoll(roll: Roll): Game {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}