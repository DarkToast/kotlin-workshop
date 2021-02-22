package basics

import io.kotest.core.spec.style.FeatureSpec

class BasicsTest : FeatureSpec() {
/*
 * Aufgabe 1:
 * Entfernen Sie bitte die Kommentare von den hier drunter liegenden Tests und implementieren Sie unter diesem
 * Kommentar die Methoden `sum`, `getString` und `toPlainJson`.
 *
 * Das Ziel ist, Ihnen die Grundzüge der Kotlinsyntax näher zu bringen.
*/

/*
    init {
        feature("sum") {
            scenario("add") {
                sum(1, 2).shouldBe(3)
            }

            scenario("add negative") {
                sum(1, -2).shouldBe(-1)
            }

            scenario("add zero") {
                sum(0, 2).shouldBe(2)
            }

            scenario("add zero as default") {
                sum(4).shouldBe(4)
            }
        }

        feature("get string") {
            scenario("returns default") {
                getString().shouldBe("Hallo")
            }

            scenario("Add a name") {
                getString("Max").shouldBe("Hallo Max")
            }

            scenario("Add a name and a lastname") {
                getString("Max", "Musterfrau").shouldBe("Hallo Max Musterfrau")
            }

            scenario("Only add a last name") {
                getString(lastname = "Musterfrau").shouldBe("Hallo Musterfrau")
            }
        }

        feature("json factory") {
            fun toPlainJson(json: String): String {
                return json.replace("\\s+".toRegex(), "")
            }

            scenario("returns default") {
                toPlainJson(getJson()).shouldBe("{}")
            }

            scenario("Add a name") {
                val expectedJson = """{"firstname":"Max"}"""
                toPlainJson(getJson("Max")).shouldBe(expectedJson)
            }

            scenario("Add a name and a lastname") {
                val expectedJson = """{"firstname":"Max","lastname":"Musterfrau"}"""
                val json = toPlainJson(getJson("Max", "Musterfrau")).shouldBe(expectedJson)
            }

            scenario("Only add a lastname") {
                val expectedJson = """{"lastname":"Musterfrau"}"""
                val json = toPlainJson(getJson(lastname = "Musterfrau")).shouldBe(expectedJson)
            }
        }
    }
 */
}