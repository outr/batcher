package batcher.processor

sealed trait OperationType

object OperationType {
  case object Copy extends OperationType
  case object Move extends OperationType
  case object Synchronize extends OperationType
}