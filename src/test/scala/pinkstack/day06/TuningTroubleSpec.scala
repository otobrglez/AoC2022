package pinkstack.day06

import zio.*
import zio.ZIO.{succeed, suspend}
import zio.test.*
import zio.test.Assertion.{equalTo, isSome}
import zio.test.TestAspect.{ignore, tag}
import zio.test.junit.JUnitRunnableSpec

import java.nio.file.Path

object TuningTroubleSpec extends JUnitRunnableSpec:
  def spec = suite("Day 6: Tuning Trouble")(
    test("Works on given example") {
      assert(
        TuningTrouble.firstCount(Path.of("./data/day06-test-input.txt"))
      )(equalTo(Some(7)))
    } @@ tag("given"),
    test("Works on real example") {
      assert(
        TuningTrouble.firstCount(Path.of("./data/day06-input.txt"))
      )(equalTo(Some(1361)))
    } @@ tag("real"),
    test("Second part on given example") {
      assert(
        TuningTrouble.secondCount(Path.of("./data/day06-test-input.txt"))
      )(equalTo(Some(19)))
    } @@ tag("given"),
    test("Second part on real data") {
      assert(
        TuningTrouble.secondCount(Path.of("./data/day06-input.txt"))
      )(equalTo(Some(3263)))
    } @@ tag("real")
  )
