package batcher

import java.io.File

import batcher.processor.{CommandProcessor, InputCommand, OutputCommand}
import org.powerscala.io._
import profig.{ConfigType, Profig}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

object Batcher {
  val persistence: Profig = new Profig(None)
  val commands: Map[String, CommandProcessor] = List(
    InputCommand,
    OutputCommand
  ).map(c => c.command -> c).toMap

  def main(args: Array[String]): Unit = {
    Profig.loadDefaults()
    Profig.merge(args)

    val storage = new File(toPath(Profig("batcher.storage").as[String]))
    storage.mkdirs()

    // Load configuration from storage
    Set("config.json").foreach { fileName =>
      val file = new File(storage, fileName)
      if (file.exists()) {
        Profig.merge(file, ConfigType.Json)
      }
    }

    // Load persistence
    val persisted = new File(storage, "persisted.json")
    if (persisted.exists()) {
      persistence.merge(persisted, ConfigType.Json)
    }

    // Execute command
    val command = Profig("arg1").as[Option[String]].getOrElse("help")
    commands.get(command) match {
      case Some(processor) => processor.process()
      case None => exit(s"Command '$command' not supported")
    }

    // Write persistence back out
    val future = Locker.withLock(new File(storage, ".persist")) {
      IO.stream(persistence.json.spaces2, persisted)
    }
    Await.result(future, Duration.Inf)
  }

  def exit(message: String, status: Int = -1): Nothing = {
    println(message)
    sys.exit(status)
  }

  def toPath(s: String): String = {
    s.replaceAll("~", System.getProperty("user.home"))
  }
}