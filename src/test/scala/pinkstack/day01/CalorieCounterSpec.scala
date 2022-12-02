package pinkstack.day01

import zio.test.*
import zio.test.junit.JUnitRunnableSpec
import zio.test.Assertion.equalTo
import java.nio.file.Path
import zio.test.TestAspect.{ignore, tag}

object CalorieCounterSpec extends JUnitRunnableSpec:
  def spec = suite("Day 1: Calorie Counting")(
    test("Works with given example") {
      assertZIO(
        CalorieCounter.totalCalories(Path.of("./data/day01-test-input.txt"))
      )(equalTo(24_000))
    },
    test("Works for my example") {
      assertZIO(
        CalorieCounter.totalCalories(Path.of("./data/day01-input.txt"))
      )(equalTo(69_177))
    } @@ ignore,
    test("Top deer") {
      assertZIO(
        CalorieCounter.topDeer(Path.of("./data/day01-test-input.txt"))
      )(equalTo(45_000))
    },
    test("Top deer for my example") {
      assertZIO(
        CalorieCounter.topDeer(Path.of("./data/day01-input.txt"))
      )(equalTo(207_456))
    } @@ tag("real") @@ ignore
  )
