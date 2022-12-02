package pinkstack.day02

import zio.*
import zio.ZIO
import ZIO.{attempt, fromOption, succeed, suspend}
import zio.Console.printLine
import zio.stream.{ZPipeline, ZSink, ZStream}
import zio.stream.ZPipeline.{splitLines, utfDecode}

import java.nio.file.Path
import scala.collection.immutable.Map

object RPS:
  enum Move   { case Rock, Paper, Scissors }; import Move.*
  enum Result { case Lose, Draw, Win       }; import Result.*

  private val his                         = Map('A' -> Rock, 'B' -> Paper, 'C' -> Scissors)
  private val mine: Map[Char, Move]       = Map('X' -> Rock, 'Y' -> Paper, 'Z' -> Scissors)
  private val mineToBe: Map[Char, Result] = mine.keys.zip(Result.values).toMap

  val won: List[Move] => Result =
    case (_ @Rock) :: (_ @Scissors) :: Nil  => Win
    case (_ @Scissors) :: (_ @Paper) :: Nil => Win
    case (_ @Paper) :: (_ @Rock) :: Nil     => Win
    case a :: b :: Nil if a == b            => Draw
    case _                                  => Lose

  def results(moves: List[Move]) =
    (
      (moves.head, won(moves), moves.head.ordinal + 1, won(moves.reverse).ordinal * 3),
      (moves(1), won(moves.reverse), moves(1).ordinal + 1, won(moves.reverse).ordinal * 3)
    )

  def find(move: Move, result: Result): Move =
    if (result != Draw)
      Move.values
        .map(p => (won(List(p, move)), p))
        .find(_._1 == result)
        .map(_._2)
        .head
    else move

  private def sumWith[A](interpretation: List[Char] => Task[A], mapping: A => ZIO[Any, Throwable, List[Move]])(
    path: Path
  ): Task[Int] =
    ZStream
      .fromPath(path)
      .via(utfDecode >>> splitLines)
      .mapZIO(line => attempt(line.split(" ", 2).map(_.charAt(0)).toList))
      .mapZIO(interpretation)
      .mapZIO(mapping)
      .map(results)
      .map(result => result._2._3 + result._2._4)
      .runSum

  val totalScore: Path => Task[Int] =
    sumWith(
      (line: List[Char]) =>
        attempt(
          List(his, mine)
            .zip(line)
            .map((m, c) => m.getOrElse(c, throw new RuntimeException(s"No symbol $c")))
        ),
      (moves: List[Move]) => succeed(moves)
    )

  val totalScore2: Path => Task[Int] =
    sumWith(
      line =>
        attempt(
          List(his, mineToBe)
            .zip(line)
            .map((m, c) => m.getOrElse(c, throw new RuntimeException(s"No symbol $c")))
        ),
      enm =>
        attempt(
          List[Move](enm.head.asInstanceOf[Move], find(enm.head.asInstanceOf[Move], enm(1).asInstanceOf[Result]))
        )
    )
