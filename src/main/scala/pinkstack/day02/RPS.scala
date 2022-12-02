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

  val his  = Map('A' -> Rock, 'B' -> Paper, 'C' -> Scissors)
  val mine = Map('X' -> Rock, 'Y' -> Paper, 'Z' -> Scissors)

  val won: List[Move] => Result =
    case (_ @Rock) :: (_ @Scissors) :: Nil  => Win
    case (_ @Scissors) :: (_ @Paper) :: Nil => Win
    case (_ @Paper) :: (_ @Rock) :: Nil     => Win
    case a :: b :: Nil if a == b            => Draw
    case _                                  => Lose

  def find(move: Move, result: Result): Move =
    if (result != Draw) Move.values.map(p => (won(List(p, move)), p)).find(_._1 == result).map(_._2).head else move

  def sumWith[A](interpretation: List[Char] => Task[A], mapping: A => ZIO[Any, Throwable, List[Move]])(
    path: Path
  ): Task[Int] =
    ZStream
      .fromPath(path)
      .via(utfDecode >>> splitLines)
      .mapZIO(line => attempt(line.split(" ", 2).map(_.charAt(0)).toList))
      .mapZIO(interpretation)
      .mapZIO(mapping)
      .map(moves => moves(1).ordinal + 1 + won(moves.reverse).ordinal * 3)
      .runSum

  val totalScore: Path => Task[Int] =
    sumWith(
      line => attempt(List(his, mine).zip(line).flatMap((m, c) => m.get(c))),
      moves => succeed(moves)
    )

  val totalScore2: Path => Task[Int] =
    sumWith(
      line => attempt(List(his, mine.keys.zip(Result.values).toMap).zip(line).flatMap((m, c) => m.get(c))),
      enm => attempt(List(enm.head.asInstanceOf[Move], find(enm.head.asInstanceOf[Move], enm(1).asInstanceOf[Result])))
    )
