package pinkstack.day06
import java.nio.file.Path
import scala.io.Source

object TuningTrouble:
  private val readPath: Path => String = path =>
    val source = Source.fromFile(path.toFile)
    try source.mkString.strip
    finally source.close

  private def countWith(offset: Int)(line: String): Option[Int] =
    (0 to line.length)
      .map(i => (i, if i + offset > line.length then line.length else i + offset))
      .map((i, cut) => (line.substring(i, cut).toSet.mkString, i + offset))
      .map((unique, result) => Option.when(unique.length == offset)(result))
      .collectFirst { case Some(value) => value; }

  def firstCount: Path => Option[Int] =
    readPath andThen countWith(4)

  def secondCount: Path => Option[Int] =
    readPath andThen countWith(14)
