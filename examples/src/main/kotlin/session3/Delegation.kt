package session3

interface Printer {
    fun print(value: String)
}

class StdOutPrinter: Printer {
    override fun print(value: String) = println(value)
}

class PrinterFacade(printer: StdOutPrinter): Printer by printer

fun main(args: Array<String>) {
    val printer = PrinterFacade(StdOutPrinter())
    printer.print("Hallo Welt")
}