import model.{Line, PathState, Point, Polygon}
import tests.modelTests

object main {
  def main(args: Array[String]): Unit = {
    //val x = new PathState(new Point(1, 2))
    //astar.astar(x, y => 1.0)
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

