package pinkstack.day03

import zio.stream.ZPipeline
import zio.stream.ZStream.fromPath as streamFromPath
import zio.{Chunk, Task, ZIO}

import java.nio.file.Path

object RucksackReorganizer:
  val priorities = (('a' to 'z') ++ ('A' to 'Z')).zipWithIndex.map((c, i) => c -> (i + 1)).toMap

  def duplicates[B](splitWith: Chunk[String] => B,
                    intersection: B => Char,
                    chunkSize: Int = 1)(path: Path): Task[Int] =
    streamFromPath(path)
      .via(ZPipeline.utfDecode >>> ZPipeline.splitLines)
      .grouped(chunkSize)
      .map(splitWith)
      .map(intersection)
      .map(priorities)
      .runSum

  val duplicatesInFirstPart: Path => Task[Int] =
    duplicates(
      s => (s.head.substring(0, s.head.length / 2), s.head.substring(s.head.length / 2)),
      (first, second) => first.intersect(second).head
    )

  val duplicatesInSecondPart: Path => Task[Int] =
    duplicates(
      identity,
      _.reduce((a, b) => a.intersect(b)).head,
      3
    )
