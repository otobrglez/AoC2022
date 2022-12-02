package pinkstack.day02

import zio.ZIO.{succeed, suspend}
import zio.test.*
import zio.test.Assertion.equalTo
import zio.test.junit.JUnitRunnableSpec
import zio.test.TestAspect.{ignore, tag}

import java.nio.file.Path

class RPSSpec extends JUnitRunnableSpec:
  def spec = suite("Day 2: Rock Paper Scissors")(
    test("Works with given example") {
      assertZIO(
        RPS.totalScore(Path.of("./data/day02-test-input.txt"))
      )(equalTo(15))
    } @@ tag("basic"),
    test("Works with my input") {
      assertZIO(
        RPS.totalScore(Path.of("./data/day02-input.txt"))
      )(equalTo(10624))
    } @@ tag("real") @@ ignore,
    test("2nd part") {
      assertZIO(
        RPS.totalScore2(Path.of("./data/day02-test-input.txt"))
      )(equalTo(12))
    } @@ tag("basic"),
    test("2nd part") {
      assertZIO(
        RPS.totalScore2(Path.of("./data/day02-input.txt"))
      )(equalTo(12))
    } @@ tag("real") @@ ignore
  )
