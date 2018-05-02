package batcher.processor

case class FileOperation(input: List[String], output: String, `type`: OperationType = OperationType.Copy)