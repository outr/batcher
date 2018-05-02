package batcher.processor

import java.io.File

import batcher.Batcher
import profig.Profig

trait CommandProcessor {
  protected def persistence: Profig = Batcher.persistence
  protected def toPath(s: String): String = Batcher.toPath(s)
  protected def exit(message: String, status: Int = -1): Nothing = Batcher.exit(message, status)

  def command: String

  def aliases: Set[String] = Set.empty

  def process(): Unit

  /**
    * Get all anonymous arguments past the command
    */
  protected def args(): List[String] = {
    def add(increment: Int): List[String] = Profig(s"arg$increment").as[Option[String]] match {
      case None => Nil    // Finished
      case Some(arg) => {
        arg :: add(increment + 1)
      }
    }
    add(2).reverse
  }

  /**
    * Gets all anonymous arguments past the command and converts them to files
    */
  protected def fileArgs(verifyExists: Boolean): List[String] = {
    args().map { path =>
      val file = new File(toPath(path))
      if (verifyExists && !file.exists()) {
        exit(s"Input file ${file.getAbsolutePath} does not exist")
      } else {
        file.getCanonicalPath
      }
    }
  }

  /**
    * Retrieves the existing list from `key`, appends `list`, and persists the concatenated, distinct list
    */
  protected def append(key: String, list: List[String]): List[String] = {
    val inputs = persistence(key).as[Option[List[String]]].getOrElse(Nil)
    val merged = (inputs ::: list).distinct
    persistence(key).store(merged)
    merged
  }
}