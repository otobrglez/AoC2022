package pinkstack.day01

import zio.test.*
import zio.test.Assertion.equalTo
import zio.test.TestAspect.{ignore, tag}
import zio.test.junit.JUnitRunnableSpec

import java.nio.file.Path

object CalorieCounterSpec extends JUnitRunnableSpec:
  def spec = suite("Day 1: Calorie Counting")(
    test("Works with given example") {
      assertZIO(
        CalorieCounter.totalCalories(Path.of("./data/day01-test-input.txt"))
      )(equalTo(24_000))
    } @@ tag("given"),
    test("Works for my example") {
      assertZIO(
        CalorieCounter.totalCalories(Path.of("./data/day01-input.txt"))
      )(equalTo(69_177))
    } @@ tag("real"),
    test("Top deer") {
      assertZIO(
        CalorieCounter.topDeer(Path.of("./data/day01-test-input.txt"))
      )(equalTo(45_000))
    } @@ tag("given"),
    test("Top deer for my example") {
      assertZIO(
        CalorieCounter.topDeer(Path.of("./data/day01-input.txt"))
      )(equalTo(207_456))
    } @@ tag("real")
  )
