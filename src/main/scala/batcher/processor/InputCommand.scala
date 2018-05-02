package batcher.processor

object InputCommand extends CommandProcessor {
  val key: String = "input"

  override def command: String = "input"

  override val aliases: Set[String] = Set("in")

  override def process(): Unit = {
    val inputs = fileArgs(verifyExists = true)
    if (inputs.isEmpty) {
      exit("Input requires at least one file argument")
    }
    append(key, inputs)
  }
}