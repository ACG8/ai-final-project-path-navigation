package tests

import model.{Line, Point, Polygon}

/**
  * Created by agieg on 4/20/2017.
  */
object modelTests {
  def runTests(): Unit = {
    nonConnectingEnds()
    validTriangle()
    linesIntersect()
    linesParallel()
  }
  def nonConnectingEnds(): Unit = {
    try {
      val first = line(point(0, 0), point(0, 4))
      val second = line(point(0, 4), point(2, 3))
      val third = line(point(2, 3), point(0, 1))
      new Polygon(first, second, third)
      assert(assertion = false, "The third line and the first line do not connect.")
    } catch {
      case e: IllegalArgumentException =>
    }
  }

  def validTriangle(): Unit = {
    try {
      val first = line(point(0, 0), point(0, 4))
      val second = line(point(0, 4), point(2, 3))
      val third = line(point(2, 3), point(0, 0))
      new Polygon(first, second, third)
    } catch {
      case e: Throwable => assert(assertion = false, "An exception should not have been thrown: "+e.getMessage)
    }
  }

  def linesIntersect(): Unit = {
    val first = line(point(2, 2), point(2, 4))
    val second = line(point(1,3), point(3, 3))
    assert(first.intersects(second), "Lines should intersect.")
  }

  def linesParallel(): Unit = {
    val first = line(point(2, 2), point(4, 2))
    val second = line(point(2, 1), point(4, 1))
    assert(!first.intersects(second), "Parallel lines should not intersect.")
  }

  def point(x: Int, y: Int): Point = {
    new Point(x, y)
  }

  def line(start: Point, end: Point): Line = {
    new Line(start, end)
  }

  def polygon(sides: Line*): Polygon = {
    new Polygon(sides:_*)
  }
}
