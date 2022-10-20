import java.time.LocalDate
import java.time.LocalDateTime


/**
 * In der Objektorientierung ist ein zentraler Bestandteil des Paradigmas die Kapselung.
 * In der Vergangenheit ist die Erkenntnis immer weiter gewachsen, dass der globale Zugriff auf alle
 * Variablen und Werten innerhalb eines Programms die Komplexität und somit die Fehleranfälligkeit stark steigert.
 *
 * Die Kapselung stellt nun sicher, dass Daten, welche für eine lokale Berechnung herangezogen werden auch nur von
 * eben dieser Berechnungslogik zugreifbar sind. In der Objektorientierung wird die Kapselung innerhalb der Klassen
 * umgesetzt. Daher gilt:
 *
 * Der State einer Klasse/Objekts muss immer vom äußeren Zugriff gekapselt werden.
 * Verändernder Zugriff soll ausschließlich über Methoden und gesichert erfolgen.
 *
 * Im unteren Beispiel wurden zwei Attribute mit dem Zugriffmodifikator `private` markiert. Ein Zugriff auf dieses
 * Attribut ist nach dem Erzeugen des Objekt nur noch von innerhalb der Klasse möglich. Also durch Klassen-Methoden.
 */
class Vertrag(
    val vertragsnummer: String,
    private var kuendigungsfrist: Int,
    private var laufzeit: LocalDateTime,
) {
    fun laufzeitVerlaengern(month: Long) {
        if (month > 24 || month < 0) {
            throw RuntimeException("More than two years are not allowed!")
        }

        this.laufzeit = this.laufzeit.plusMonths(month)
    }

    fun getLaufzeit() = laufzeit.minusSeconds(0)
}


fun zugriff2() {
    val vertrag1 = Vertrag("000001", 24, LocalDateTime.now().plusMonths(12))

    // Vertragsnummer ist hier ein `val`. Also nur Lesender Zugriff ist erlaubt. Das erfüllt die Kriterien
    // der Kapselung, sofern der Wert als solcher gelesen werden soll.
    // Die weiteren direkten Zugriffe sind untersagt und können nur über Methoden verändert werden.
    println(vertrag1.vertragsnummer)
    // println(vertrag1.laufzeit)   // <-- das geht nicht!
    println(vertrag1.getLaufzeit()) // <-- expliziter Lesender Zugriff
    vertrag1.laufzeitVerlaengern(4) // <-- expliziter Schreibender Zugriff
}


/**
 * Oft werden simple Typen über die Zeit mit immer mehr fachlichen Kontexten gefüllt und erweitert. Unterteilungen
 * oder Homonyme werden oft zu spät erkannt oder durch Zeitdruck und Projektstress ignoriert. Dieses Antipattern führt
 * bereits in kurzer Zeit zu einem starken Komplexitätsanstieg und somit zu erhöhtem Wartungs- und Testaufwand.
 *
 * Allein in diesem kleinen Beispiel sind mehere Methoden und Attribute notwendig und eine Menge If-Else Abfragen.
 */
class Customer(
    private var vorname: String,
    private var nachname: String,
    private var strasse: String,
    val geburtstag: String?,
    private var hausnummer: Int,
    private var hausnummerZusatz: String,
    private var plz: String,
    private var ort: String,
    val anrede: String,
    val firmenName: String?,
    val branche: String?,
    val handelsregisterEintrag: String?,
    val emailadresse: String?,
    val faxnummer: String?,
    val telefonnummer: String?,
    val energieart: String?,
    val haendlerId: String?,
    val registrationDate: LocalDateTime
) {
    fun getVorname() = vorname
    fun getNachname() = nachname

    fun getStrasse() = strasse
    fun getHausnummer() = hausnummer
    fun getHausnummerZusatz() = hausnummerZusatz
    fun getPlz() = plz
    fun getOrt() = ort

    fun isAdult(): Boolean = LocalDate.parse(geburtstag).isBefore(LocalDate.now().minusYears(18))

    fun isStromProvider(): Boolean = energieart == "Strom"

    fun adresseAendern(strasse: String, hausnummer: Int, hausnummerZusatz: String, plz: String, ort: String) {
        this.strasse = strasse
        this.hausnummer = hausnummer
        this.hausnummerZusatz = hausnummerZusatz
        this.plz = plz
        this.ort = ort
    }

    fun namensAenderung(vorname: String, nachname: String) {
        this.vorname = vorname
        this.nachname = nachname
    }

    fun getPrimaryContact(): String? {
        return if(isPrivateCustomer()) {
            telefonnummer
        } else {
            emailadresse
        }
    }

    fun isPrivateCustomer() = this.geburtstag != null && this.firmenName == null
}

/**
 * Ausgelagerte Domainlogik wie hier eine Validierung führt ebenfalls zu einem eklatanten Komplexitätsanstieg.
 * Mehrere fachliche Kontexte werden hier zusammen in einem Codeblock unterschieden und behandelt.
 */
fun validate(customer: Customer) {
    val isPrivateCustomer = customer.geburtstag != null && customer.firmenName == null

    val isEnergieversorger = !isPrivateCustomer && customer.energieart != null

    val hasValidContactInformation = if(isPrivateCustomer) {
        customer.emailadresse != null || customer.telefonnummer != null
    } else {
        customer.emailadresse != null || customer.telefonnummer != null || customer.faxnummer != null
    }

    val isGeburtstagValid = isPrivateCustomer &&
        LocalDate.parse(customer.geburtstag).plusYears(18).isBefore(LocalDate.now())
}


/**
 * Ein erster Ansatz ist die Typen/Klassentrennung der verschiedenen fachlichen Blöcken.
 * Der Vorteil ist der getrennte fachliche Scope der große Nachteil ist der stark duplizierte Code.
 */
class PrivateCustomer(
    val anrede: String,
    val vorname: String,
    val nachname: String,
    val strasse: String,
    val geburtstag: String?,
    val hausnummer: Int,
    val hausnummerZusatz: String,
    val plz: String,
    val ort: String,
    val telefonnummer: String?,
    val emailadresse: String?,
) {
    fun isGeburtstagValid(): Boolean = LocalDate.parse(geburtstag).isBefore(LocalDate.now().minusYears(18))
}

class CompanyCustomer(
    val firmenName: String?,
    val anrede: String,
    val vorname: String,
    val nachname: String,
    val strasse: String,
    val hausnummer: Int,
    val hausnummerZusatz: String,
    val plz: String,
    val ort: String,
    val faxnummer: String?,
    val telefonnummer: String?,
    val emailadresse: String?,
    val branche: String?,
    val handelsregisterEintrag: String?,
)

class ProviderCustomer(
    val firmenName: String?,
    val anrede: String,
    val vorname: String,
    val nachname: String,
    val strasse: String,
    val hausnummer: Int,
    val hausnummerZusatz: String,
    val plz: String,
    val ort: String,
    val faxnummer: String?,
    val telefonnummer: String?,
    val emailadresse: String?,
    val haendlerId: String?,
    val energieart: String?,
)


/**
 * Ein weiterer Schritt ist eine weitere Objektcomposition, in dem die Adresse- und Namensinformationen in eine eigene
 * Klasse ausgelagert werden. Hier können wir auch direkt Methoden, die genau diese Daten beschreiben auslagern.
 */
class Name2(
    private var anrede: String,
    private var vorname: String,
    private var nachname: String
) {
    fun getAnrede() = anrede
    fun getVorname() = vorname
    fun getNachname() = nachname

    fun namensAenderung(nachname: String) {
        this.nachname = nachname
    }
}

class Address(
    val strasse: String,
    val hausnummer: Int,
    val hausnummerZusatz: String?,
    val plz: String,
    val ort: String,
)


/**
 * Eine weitere Möglichkeit ist die Unterteilung und Abstrahierung der Contactinformationen.
 */
abstract class Contact(val contactInfo: String) {
    abstract val type: String
}
class Telephone(phoneNumber: String): Contact(phoneNumber) {
    override val type = "telephone"
}
class Email(emailAddress: String): Contact(emailAddress) {
    override val type = "email"
}
class Fax(faxNumber: String): Contact(faxNumber) {
    override val type = "fax"
}

/**
 * Ein abstrakter Kunde fasst alle gemeinsamen Elemente zusammen. Ein Interface bietet eine direkte API für Aufrufer.
 */
interface CustomerInterface {
    fun umzug(address: Address)
    fun namensAenderung(nachname: String)
    fun getPrimaryContact(): Contact
    fun isPrivateCustomer(): Boolean
}

abstract class Customer2(
    val name: Name2,
    private var address: Address,
    val contact: List<Contact>
): CustomerInterface {
    fun getAddress() = address

    override fun umzug(address: Address) {
        this.address = address
    }

    override fun namensAenderung(nachname: String) = name.namensAenderung(nachname)
}

/**
 * Der Privatkunden leitet nun von Customer2 ab, implementiert beide Methoden
 * und reichert sich selbst um weitere Elemente an.
 */
class PrivateCustomer2(
    name: Name2,
    address: Address,
    contact: List<Contact>,
    val geburtstag: LocalDate
): Customer2(name, address, contact) {
    fun isAdult(): Boolean = geburtstag.isBefore(LocalDate.now().minusYears(18))

    override fun getPrimaryContact(): Contact = contact.first { it is Telephone }

    override fun isPrivateCustomer(): Boolean = true
}

/**
 * Eine Zwischenklasse bietet den B2B Kunden eine gemeinsame Basis und implementiert
 * beide Methoden aus der Basisklasse.
 */
abstract class B2BCustomer(
    name: Name2,
    address: Address,
    contact: List<Contact>
): Customer2(name, address, contact) {
    override fun getPrimaryContact(): Contact = contact.first { it is Email }

    override fun isPrivateCustomer(): Boolean = false
}

/**
 * Beiden B2B Kunden leiten nun von B2BCustomer ab und erweitern ebenfalls ihre Klasse
 * um die eigenen Elemente.
 */
class CompanyCustomer2(
    name: Name2,
    address: Address,
    contact: List<Contact>,
    val branche: String,
    val handelsregisterEintrag: String,
): B2BCustomer(name, address, contact)

class ProviderCustomer2(
    name: Name2,
    address: Address,
    contact: List<Contact>,
    val haendlerId: String,
    val energieart: String,
): B2BCustomer(name, address, contact) {
    fun isStromProvider(): Boolean = energieart == "Strom"
}

/**
 *
 */
fun main() {
    val name = Name2("Frau", "Frauke", "Musterfrau")
    val adresse = Address("Fakestreet", 123, null, "53123", "Bonn")
    val tele: Contact = Telephone("0160 00000000")
    val email: Contact = Email("ich@inter.net")

    var customer: CustomerInterface = PrivateCustomer2(name, adresse, listOf(tele, email), LocalDate.now())
    println(customer.isPrivateCustomer())
    println(customer.getPrimaryContact().contactInfo)

    customer = CompanyCustomer2(name, adresse, listOf(tele, email), "Energie", "HBC134")
    println(customer.isPrivateCustomer())
    println(customer.getPrimaryContact().contactInfo)
}