package pinkstack.day05

import zio.*
import zio.ZIO.{succeed, suspend}
import zio.test.*
import zio.test.Assertion.equalTo
import zio.test.TestAspect.{ignore, tag}
import zio.test.junit.JUnitRunnableSpec

import java.nio.file.Path

object SupplyStacksSpec extends JUnitRunnableSpec:
  def spec = suite("Day 5: Supply Stacks")(
    test("Works on given example") {
      assertZIO(
        SupplyStacks.arrangeFirstPart(Path.of("./data/day05-test-input.txt"))
      )(equalTo("CMZ"))
    } @@ tag("given"),
    test("Works on real example") {
      assertZIO(
        SupplyStacks.arrangeFirstPart(Path.of("./data/day05-input.txt"))
      )(equalTo("SHMSDGZVC"))
    } @@ tag("real"),
    test("Second part on given example") {
      assertZIO(
        SupplyStacks.arrangeSecondPart(Path.of("./data/day05-test-input.txt"))
      )(equalTo("MCD"))
    } @@ tag("given"),
    test("Second part on real data") {
      assertZIO(
        SupplyStacks.arrangeSecondPart(Path.of("./data/day05-input.txt"))
      )(equalTo("VRZGHDFBQ"))
    } @@ tag("real")
  )
