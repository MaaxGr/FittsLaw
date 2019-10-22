import java.io.FileWriter

class FittsLogger() {

    private val fileWriter = FileWriter("log-${System.currentTimeMillis()}.txt")

    fun log(message: Message) {
        //log to console
        println(message)

        //log to file
        fileWriter.append(message.asCSV()).append(System.lineSeparator()).flush()
    }

    data class Message(val roundId: Int, val millis: Long, val diff: Double, val scaleFactor: Double, val delay: Long) {

        fun asCSV(): String {
            return "$roundId;$millis;$diff;$scaleFactor,$delay"
        }

    }


}