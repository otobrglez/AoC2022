package pinkstack.day05

import zio.{Task, ZIO}
import zio.stream.ZStream.fromPath as streamFrom
import zio.stream.{Stream, ZPipeline, ZSink, ZStream}
import zio.stream.ZPipeline.{splitLines, splitOn, utfDecode}
import ZIO.succeed

import java.nio.file.Path

object SupplyStacks:
  case class Move(size: Int, from: Int, to: Int)
  type Moves  = Array[Move]
  type Stacks = Array[Array[Char]]

  private val readArrangement: Path => Task[(Stacks, Moves)] = path =>
    for
      split  <- streamFrom(path).via(utfDecode >>> splitOn("\n\n")).runCollect
      rows   <- succeed(
        split.head.replaceAll(" " * 4, "[ ]").split("\\n")
          .map("""\[(\w|\s)]""".r.findAllMatchIn).map(_.map(_.group(1)).map(_.charAt(0)).toArray)
          .filterNot(_.isEmpty)
      )
      stacks <- succeed(
        0.until("\\d+".r.findAllMatchIn(split.head).toArray.last.matched.toInt)
          .map(i => rows.map(_(i)).filter(_ != ' ').reverse)
          .filterNot(_.isEmpty).toArray
      )
      moves  <- ZStream.from(split.tail).via(splitLines)
        .map("""\d+""".r.findAllMatchIn).map(_.map(_.group(0).toInt).toArray)
        .map(m => Move.apply(m.head, m(1), m.last)).runCollect.map(_.toArray)
    yield (stacks, moves)

  def rearrange(withF: (Stacks, Move) => Stacks)(path: Path): Task[String] =
    readArrangement(path)
      .flatMap((stacks, moves) => succeed(moves.foldLeft(stacks)(withF)))
      .map(_.map(_.last).mkString)

  val arrangeFirstPart: Path => Task[String] =
    rearrange((stacks, move) =>
      stacks
        .updated(move.to - 1, stacks(move.to - 1) ++ stacks(move.from - 1).takeRight(move.size).reverse)
        .updated(move.from - 1, stacks(move.from - 1).dropRight(move.size))
    )

  val arrangeSecondPart: Path => Task[String] =
    rearrange((stacks, move) =>
      stacks
        .updated(move.to - 1, stacks(move.to - 1) ++ stacks(move.from - 1).takeRight(move.size))
        .updated(move.from - 1, stacks(move.from - 1).dropRight(move.size))
    )
