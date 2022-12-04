package pinkstack.day04

import zio.ZIO.{succeed, suspend}
import zio.test.*
import zio.test.Assertion.equalTo
import zio.test.TestAspect.{ignore, tag}
import zio.test.junit.JUnitRunnableSpec

import java.nio.file.Path

object CampCleanupSpec extends JUnitRunnableSpec:
  def spec = suite("Day 4: Camp Cleanup")(
    test("Works on given example") {
      assertZIO(
        CampCleanup.firstPartOverlap(Path.of("./data/day04-test-input.txt"))
      )(equalTo(2))
    } @@ tag("given"),
    test("Works on real example") {
      assertZIO(
        CampCleanup.firstPartOverlap(Path.of("./data/day04-input.txt"))
      )(equalTo(530))
    } @@ tag("real"),
    test("Second part on given example") {
      assertZIO(
        CampCleanup.secondPartOverlap(Path.of("./data/day04-test-input.txt"))
      )(equalTo(4))
    } @@ tag("given"),
    test("Second part on real data") {
      assertZIO(
        CampCleanup.secondPartOverlap(Path.of("./data/day04-input.txt"))
      )(equalTo(903))
    } @@ tag("real")
  )
