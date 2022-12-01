// Oto Brglez <otobrglez@gmail.com> - AoC2022

package pinkstack.day01

import zio.*
import zio.stream.{Stream, ZPipeline, ZStream}

import java.nio.file.Path

object CalorieCounter:
  private def topWith(f: List[Int] => Int)(path: Path): Task[Int] =
    ZStream
      .fromPath(path)
      .via(ZPipeline.utf8Decode >>> ZPipeline.splitLines)
      .runFold((0, Map.empty[Int, Array[Int]])) {
        case ((i, agg), c) if c.nonEmpty =>
          (i, agg + (i -> agg.get(i).map(_ ++ Array(c.toInt)).getOrElse(Array(c.toInt))))
        case ((i, agg), _)               => (i + 1, agg)
      }
      .map(_._2.view.mapValues(_.sum).toList.map(_._2))
      .map(f)

  val totalCalories: Path => Task[Int] = topWith(_.max)
  val topDeer: Path => Task[Int]       = topWith(_.sorted.reverse.take(3).sum)
