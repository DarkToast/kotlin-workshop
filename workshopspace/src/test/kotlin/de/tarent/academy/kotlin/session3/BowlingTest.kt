package de.tarent.academy.kotlin.session3

import org.junit.Assert
import org.junit.Test

class BowlingTest {
    @Test
    fun `a new game starts`() {
        val game = NewGame.addRoll(Roll(4))

        Assert.assertTrue(game is OpenGame)
        Assert.assertTrue( (game as OpenGame).actualFrame is FirstRoll)
        Assert.assertTrue( game.finishedFrames.isEmpty())
    }

    @Test
    fun `first frame`() {
        val game = NewGame
                .addRoll(Roll(4))
                .addRoll(Roll(2))

        Assert.assertTrue(game is OpenGame)
        Assert.assertTrue( (game as OpenGame).finishedFrames.isEmpty())

        val frame = game.actualFrame
        Assert.assertTrue( frame is NormFrame)
        Assert.assertEquals(6, (frame as NormFrame).points())
    }
}