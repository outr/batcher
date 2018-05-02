package batcher.processor

import io.circe.Json
import profig.JsonUtil

object OutputCommand extends CommandProcessor {
  override def command: String = "output"

  override def aliases: Set[String] = Set("out")

  override def process(): Unit = {
    val output = fileArgs(verifyExists = false)
    if (output.isEmpty) {
      exit("Output requires one directory argument")
    } else if (output.length > 1) {
      exit(s"Output expects exactly one directory argument, but ${output.length} provided")
    } else {
      val inputs = persistence(InputCommand.key)
        .as[Option[List[String]]]
        .getOrElse(exit("Inputs must be defined before the output can be assigned"))
      val operation = FileOperation(inputs, output.head)
      val ops = persistence("operations").as[Option[List[Json]]].getOrElse(Nil)
      val json = JsonUtil.toJson(operation)
      persistence("operations").store(ops ::: List(json))
      persistence(InputCommand.key).remove()
    }
  }
}