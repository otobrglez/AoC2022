package pinkstack.day01

object FPSApp extends App:
  def addA(x: Int, y: Int) = x + y
  val addB                 = addA _

  def negate[T](f: T => Boolean): T => Boolean = x => !f(x)
  val ~-                                       = negate[Int] _

  println(
    1 :: 2 :: 3 :: 4 :: Nil map ~-(_ % 2 == 0)
  )
