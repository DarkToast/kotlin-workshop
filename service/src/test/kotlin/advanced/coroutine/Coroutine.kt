package advanced.coroutine

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Telemetry(val value: String)
data class Measurement(val id: Int, val temp: Int)
data class Aggregate(val value: Int)
data class ViewModel(val aggregate: Aggregate, val message: String)

/**
 * Merges two channel to a new channel with help of a transform function
 */
fun <T, U, V> CoroutineScope.merge(
    ch1: ReceiveChannel<T>,
    ch2: ReceiveChannel<U>,
    channelSize: Int,
    transform: (T, U) -> V): ReceiveChannel<V> {

    val channel: Channel<V> = Channel(channelSize)
    GlobalScope.launch {
        val ch1Iter = ch1.iterator()
        val ch2Iter = ch2.iterator()

        while (ch1Iter.hasNext() && ch2Iter.hasNext()) {
            channel.send(transform(ch1Iter.next(), ch2Iter.next()))
        }
        ch1.cancel()
        ch2.cancel()
        channel.close()
    }
    return channel
}

/**
 * Generates continuously IDs. Channel has size 0
 */
class NumberGenerator() {
    fun generate(): ReceiveChannel<Int> {
        val channel = Channel<Int>()
        GlobalScope.launch(Dispatchers.Default) {
            println("Launching number generator")
            var i = 0
            while (!channel.isClosedForSend) {
                println("Sending id $i")
                channel.send(i++)
            }
        }

        return channel
    }
}

/**
 * Telemetry source. Continuously send temperature measurements.
 */
class TelemetrySource() {
    fun start(maxTemp: Int): ReceiveChannel<Telemetry> {
        val channel = Channel<Telemetry>()
        GlobalScope.launch(Dispatchers.Default) {
            println("Launching telemetry source")
            var temp = -10
            while (!channel.isClosedForSend && temp < maxTemp) {
                println("Sending telemetry $temp")
                channel.send(Telemetry((temp++).toString()))
                delay(200)
            }
            channel.close()
        }
        return channel
    }
}

/**
 * TODO: Implement the first computation. Merge two channels to Telemetry. Hint: You can use the merge method from above.
 */
class TelemetryToMeasurement(private val numberCh: ReceiveChannel<Int>, private val telemetryChan: ReceiveChannel<Telemetry>) {
    fun start(): ReceiveChannel<Measurement> {
        numberCh.cancel()
        telemetryChan.cancel()
        TODO()
    }
}

/**
 * TODO: Implement the Aggregator. Should aggregate all measurement and send an updated ViewModel
 */
class Aggregator(private val measurementCh: ReceiveChannel<Measurement>) {
    fun start(): ReceiveChannel<ViewModel> = TODO()
}

/**
 * TODO: Implement the Database. Should "persist" all Measurements
 */
class Database(private val measurementCh: ReceiveChannel<Measurement>) {
    val database: Map<Int, Measurement> = mapOf()
    fun start(): Unit = TODO()
}

/**
 * TODO: Implement the View. Should update the view for each ViewModel.
 */
class View(private val modelCh: ReceiveChannel<ViewModel>) {
    var view: String = ""
    fun start(): Unit = TODO()
}


// ==================================================

/**
 * The actual test. ;-)
 */
class ChannelSpec : FeatureSpec({

    /**
     * Number Generator ->      +                     + -> Database<Measurement>
     *  Telemetry<String> ->    + --> Measurement --> + -> Aggregator<>   --> View
     */
    feature("! A temperature IoT system") {
        val numberGenerator = NumberGenerator()
        val telSource = TelemetrySource()
        val service = TelemetryToMeasurement(numberGenerator.generate(), telSource.start(10))

        val measurementChannel = service.start()
        val aggregator = Aggregator(measurementChannel)

        val database = Database(measurementChannel)
        val view = View(aggregator.start())

        scenario("aggregates and shows") {
            database.start()
            view.start()

            while(!measurementChannel.isClosedForReceive) {
                delay(100)
            }

            database.database.size shouldBe 20
            view.view shouldBe "Aggregated temp is 0"
        }
    }
})
