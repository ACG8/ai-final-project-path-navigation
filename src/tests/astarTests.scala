package tests

import model.{Line, Point, Polygon, Grid, PathState, Rational}
import algorithms.{astar,greedybestfirst, hillClimb}


/**
  * Created by Ananda on 4/24/2017.
  */
object astarTests {

  def runTests(): Unit = {
    tests.foreach(test => timer.time{test.run()})
  }

  private type Heuristic = PathState => Double
  private type Algorithm = (PathState, Heuristic) => (List[PathState],Int,Double)
  var tests:List[Test.Test] = Nil
  for (a <- List[(Algorithm,String)]((astar.astar,"ASt"),(greedybestfirst.greedybestfirst,"GBF"),
                                     (hillClimb.kHillClimb[PathState](1), "Classic hillclimbing"),
                                     (hillClimb.kHillClimb[PathState](2), "2-lookahead hillclimbing"))) {
    for (h <- List[(Heuristic,String)]((PathState.cartesianH,"cartesian"),(PathState.lookAheadH,"lookahead"))) {
      tests ++= List(
        Test.test(grids.trivialCase,
          "Trivial Case ("+a._2+","+h._2+")","an empty grid",
          a._1,h._1),
        Test.test(grids.oneTriangle,
          "One Triangle ("+a._2+","+h._2+")","single triangular obstacle",
          a._1,h._1),
        Test.test(grids.centerSquare,
          "Center Square ("+a._2+","+h._2+")","square to test prohibition on intrashape traversal",
          a._1,h._1),
        Test.test(grids.grazedTriangle,
          "Grazed Triangle ("+a._2+","+h._2+")","triangle to test traversal of shape boundaries",
          a._1,h._1),
        Test.test(grids.backtrack,
          "Backtrack ("+a._2+","+h._2+")","test of algorithm's ability to navigate around obstacles",
          a._1,h._1),
        Test.test(grids.cruel,
          "Cruel ("+a._2+","+h._2+")","heuristics won't save you here...",
          a._1,h._1),

        Test.test(grids.easy0,
          "Easy0 ("+a._2+","+h._2+")","an easy course (5 polygons, 20x20)",
          a._1,h._1),
        Test.test(grids.easy1,
          "Easy1 ("+a._2+","+h._2+")","an easy course (5 polygons, 20x20)",
          a._1,h._1),
        Test.test(grids.easy2,
          "Easy2 ("+a._2+","+h._2+")","an easy course (5 polygons, 20x20)",
          a._1,h._1),
        Test.test(grids.easy3,
          "Easy3 ("+a._2+","+h._2+")","an easy course (5 polygons, 20x20)",
          a._1,h._1),
        Test.test(grids.easy4,
          "Easy4 ("+a._2+","+h._2+")","an easy course (5 polygons, 20x20)",
          a._1,h._1),
        Test.test(grids.easy5,
          "Easy5 ("+a._2+","+h._2+")","an easy course (5 polygons, 20x20)",
          a._1,h._1),

        Test.test(grids.med1,
          "Med1 ("+a._2+","+h._2+")","a medium course (8 polygons, 20x20)",
          a._1,h._1),
        Test.test(grids.med2,
          "Med2 ("+a._2+","+h._2+")","a medium course (8 polygons, 20x20)",
          a._1,h._1),
        Test.test(grids.med3,
          "Med3 ("+a._2+","+h._2+")","a medium course (8 polygons, 20x20)",
          a._1,h._1),
        Test.test(grids.med4,
          "Med4 ("+a._2+","+h._2+")","a medium course (8 polygons, 20x20)",
          a._1,h._1),

        Test.test(grids.dejaVu,
          "Deja Vu ("+a._2+","+h._2+")","the example obstacle course provided in the handout",
          a._1,h._1)
      )
    }
  }

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
