package tests

import model.{Line, Point, Polygon, Grid, PathState, Rational}
import algorithms.{astar,greedybestfirst}

/**
  * Created by Ananda on 4/24/2017.
  */
object astarTests {

  def runTests(): Unit = {
    tests.foreach(test => timer.time{test.run()})
  }

  val tests: List[Test.Test] =
    List(
      Test.test(grids.trivialCase,
        "Trivial Case (AStar,cartesian)","an empty grid",
        astar.astar,PathState.cartesianH),
      Test.test(grids.oneTriangle,
        "One Triangle (AStar,cartesian)","single triangular obstacle",
        astar.astar,PathState.cartesianH),
      Test.test(grids.centerSquare,
        "Center Square (AStar,cartesian)","square to test prohibition on intrashape traversal",
        astar.astar,PathState.cartesianH),
      Test.test(grids.grazedTriangle,
        "Grazed Triangle (AStar,cartesian)","triangle to test traversal of shape boundaries",
        astar.astar,PathState.cartesianH),
      Test.test(grids.backtrack,
        "Backtrack (AStar,cartesian)","test of algorithm's ability to navigate around obstacles",
        astar.astar,PathState.cartesianH),

      Test.test(grids.dejaVu,
        "Deja Vu (AStar,cartesian)","the example obstacle course provided in the handout",
        astar.astar,PathState.cartesianH)
    )

  def point(x: Int, y: Int): Point = {
    new Point(new Rational(x), new Rational(y))
  }

  def line(start: Point, end: Point): Line = {
    new Line(start, end)
  }

  def polygon(points: Point*): Polygon = {
    new Polygon(points.toList)
  }

  def grid(maxX: Int, maxY: Int, polygons: Polygon*): Grid = {
    new Grid(maxX, maxY, polygons:_*)
  }

  def pathstate(grid: Grid, position: Point, goal: Point): PathState = {
    new PathState(grid, position, goal)
  }
}
