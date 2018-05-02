package batcher

import java.io.{File, RandomAccessFile}
import java.nio.channels.FileLock

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Lock(file: File, lock: FileLock) {
  def release(): Unit = {
    lock.release()
    lock.close()
    if (!file.delete()) {
      file.deleteOnExit()
    }
  }
}

object Locker {
  def lock(file: File): Future[Lock] = {
    val raf = new RandomAccessFile(file, "rw")
    val channel = raf.getChannel
    Future {
      new Lock(file, channel.lock())
    }
  }

  def withLock[Return](file: File)(f: => Return): Future[Return] = {
    lock(file).map { lock =>
      try {
        f
      } finally {
        lock.release()
      }
    }
  }
}