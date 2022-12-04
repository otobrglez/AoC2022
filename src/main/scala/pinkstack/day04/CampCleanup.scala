package pinkstack.day04

import zio.Task
import zio.stream.ZStream.fromPath as streamFromPath
import zio.stream.{Stream, ZPipeline}

import java.nio.file.Path

object CampCleanup:
  private val ranges: Path => Stream[Throwable, (Set[Int], Set[Int])] =
    path =>
      streamFromPath(path)
        .via(ZPipeline.utfDecode >>> ZPipeline.splitLines)
        .map(
          _.split("\\,", 2)
            .map(_.split("\\-", 2).map(_.toInt))
            .map(t => Tuple.fromArray(t).asInstanceOf[(Int, Int)])
        )
        .map(_.map((start, stop) => Range.inclusive(start, stop).toSet))
        .map(line => Tuple.fromArray(line).asInstanceOf[(Set[Int], Set[Int])])

  val firstPartOverlap: Path => Task[Int] =
    ranges(_)
      .map((first, last) => first.subsetOf(last) || last.subsetOf(first))
      .collect { case x if x => 1 }
      .runSum

  val secondPartOverlap: Path => Task[Int] =
    ranges(_)
      .map((first, second) => first.intersect(second).nonEmpty)
      .collect { case x if x => 1 }
      .runSum
