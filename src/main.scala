import model.{Line, Point, Polygon}
import tests.modelTests

object main {
  def main(args: Array[String]): Unit = {
    runTests()
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

  def runTests(): Unit = {
    modelTests.runTests()
  }
}

