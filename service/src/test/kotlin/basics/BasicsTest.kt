package basics


class BasicsTest() {
/*
 * Aufgabe 1:
 * Entfernen Sie bitte die Kommentare von den hier drunter liegenden Tests und implementieren Sie unter diesem
 * Kommentar die Methoden `sum`, `getString` und `toPlainJson`.
 *
 * Das Ziel ist, Ihnen die Grundzüge der Kotlinsyntax näher zu bringen.
*/
    /*
    // sum
    @Test
    fun add() {
        assertEquals(3, sum(1, 2))
    }

    @Test
    fun `add negative`() {
        assertEquals(-1, sum(1, -2))
    }

    @Test
    fun `add zero`() {
        assertEquals(2, sum(0, 2))
    }

    @Test
    fun `add zero as default`() {
        assertEquals(4, sum(4))
    }

    // get string
    @Test
    fun `getString returns default`() {
        assertEquals("Hallo", getString())
    }

    @Test
    fun `getString adds a name`() {
        assertEquals("Hallo Max", getString("Max"))
    }

    @Test
    fun `getString adds a name and a lastname`() {
        assertEquals("Hallo Max Musterfrau", getString("Max", "Musterfrau"))
    }

    @Test
    fun `getString only adds a last name`() {
        assertEquals("Hallo Musterfrau", getString(lastname = "Musterfrau"))
    }

    // json factory
    @Test
    fun `getJson returns default`() {
        assertEquals("{}", toPlainJson(getJson()))
    }

    @Test
    fun `getJson adds a name`() {
        val expectedJson = """{"firstname":"Max"}"""
        assertEquals(expectedJson, toPlainJson(getJson("Max")))
    }

    @Test
    fun `getJson adds a name and a lastname`() {
        val expectedJson = """{"firstname":"Max","lastname":"Musterfrau"}"""
        assertEquals(expectedJson, toPlainJson(getJson("Max", "Musterfrau")))
    }

    @Test
    fun `getJson only adds a lastname`() {
        val expectedJson = """{"lastname":"Musterfrau"}"""
        assertEquals(expectedJson, toPlainJson(getJson(lastname = "Musterfrau")))
    }

    private fun toPlainJson(json: String): String {
        return json.replace("\\s+".toRegex(), "")
    }
    */
}