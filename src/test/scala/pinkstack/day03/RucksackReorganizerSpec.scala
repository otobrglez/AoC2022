package pinkstack.day03

import zio.ZIO.{succeed, suspend}
import zio.test.*
import zio.test.Assertion.equalTo
import zio.test.TestAspect.{ignore, tag}
import zio.test.junit.JUnitRunnableSpec
import java.nio.file.Path

object RucksackReorganizerSpec extends JUnitRunnableSpec:
  def spec = suite("Day 3: Rucksack Reorganization")(
    test("Works on given example") {
      assertZIO(
        RucksackReorganizer.duplicatesInFirstPart(Path.of("./data/day03-test-input.txt"))
      )(equalTo(157))
    } @@ tag("given"),
    test("Works on real example") {
      assertZIO(
        RucksackReorganizer.duplicatesInFirstPart(Path.of("./data/day03-input.txt"))
      )(equalTo(8_123))
    } @@ tag("real"),
    test("Second part on given example") {
      assertZIO(
        RucksackReorganizer.duplicatesInSecondPart(Path.of("./data/day03-test-input.txt"))
      )(equalTo(70))
    } @@ tag("given"),
    test("Second part on real data") {
      assertZIO(
        RucksackReorganizer.duplicatesInSecondPart(Path.of("./data/day03-input.txt"))
      )(equalTo(2620))
    } @@ tag("given")
  )
