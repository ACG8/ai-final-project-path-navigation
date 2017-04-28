package tests

import model.{Line, Point, Polygon, Grid, PathState}
import algorithms.astar._

/**
  * Created by Ananda on 4/24/2017.
  */
object astarTests {
  def runTests(): Unit = {
    tests.foreach(test => test.run())
  }

  class Test(name: String, test: () => Unit) {
    def run(): Unit = {
      println("Running "+name)
      try {
        test()
        println(name + " succeeded")
      } catch {
        case t: Throwable =>
          println(name + " failed: " + t.getMessage)
          t.printStackTrace()
      }
    }
  }

  val start: Point = point(1,1)
  val end: Point = point(9,9)

  val tests: List[Test] = List(
    new Test("Trivial Case", () => {
      val field = grid(10,10,polygon(start),polygon(end))
      val start_state = pathstate(field,start,end)
      val path = astar(start_state,PathState.cartesianH)
      println("  grid is empty 10x10 arena")
      println("  start: " + start)
      println("  goal: " + end)
      path match {
        case Nil => println("  no path found")
        case _ =>
          println("  found path: " + path)
          PathState.drawSolution("trivialcase",field,path)
      }
    }),
    new Test("One Triangle", () => {
      val field = grid(10,10,polygon(start),polygon(point(5,2),point(3,8),point(4,6)),polygon(end))
      val start_state = pathstate(field,start,end)
      val path = astar(start_state,PathState.cartesianH)
      println("  grid is a 10x10 arena with polygon (5,2),(3,8),(4,6)")
      println("  start: " + start)
      println("  goal: " + end)
      path match {
        case Nil => println("  no path found")
        case _ =>
          println("  found path: " + path)
          PathState.drawSolution("onetriangle",field,path)
      }
    }),
    new Test("Center Square", () => {
      val field = grid(10,10,polygon(start),polygon(point(3,3),point(7,3),point(7,7),point(3,7)),polygon(end))
      val start_state = pathstate(field,start,end)
      val path = astar(start_state,PathState.cartesianH)
      println("  grid is a 10x10 arena with a square in the middle")
      println("  start: " + start)
      println("  goal: " + end)
      path match {
        case Nil => println("  no path found")
        case _ =>
          println("  found path: " + path)
          PathState.drawSolution("centersquare",field,path)
      }
    }),
    new Test("Center squarish", () => {
      val field = grid(10,10,polygon(start),polygon(point(3,2),point(7, 3),point(7,7),point(3,7)),polygon(end))
      val start_state = pathstate(field,start,end)
      val path = astar(start_state,PathState.cartesianH)
      println("  grid is a 10x10 arena with a square in the middle")
      println("  start: " + start)
      println("  goal: " + end)
      path match {
        case Nil => println("  no path found")
        case _ =>
          println("  found path: " + path)
          PathState.drawSolution("centerdiamond",field,path)
      }
    }),
    new Test("Grazed Triangle", () => {
      val field = grid(10,10,polygon(start),polygon(point(3,3),point(7,3),point(7,7)),polygon(end))
      val start_state = pathstate(field,start,end)
      val path = astar(start_state,PathState.cartesianH)
      println("  grid is a 10x10 arena with a triangle grazing the optimal path")
      println("  start: " + start)
      println("  goal: " + end)
      path match {
        case Nil => println("  no path found")
        case _ =>
          println("  found path: " + path)
          PathState.drawSolution("grazedtriangle",field,path)
      }
    }),
    new Test("Grazed Triangle", () => {
      val field = grid(10,10,polygon(start),polygon(point(3,3),point(7,3),point(7,7)),polygon(end))
      val start_state = pathstate(field,start,end)
      val path = astar(start_state,PathState.cartesianH)
      println("  grid is a 10x10 arena with a triangle grazing the optimal path")
      println("  start: " + start)
      println("  goal: " + end)
      path match {
        case Nil => println("  no path found")
        case _ =>
          println("  found path: " + path)
          PathState.drawSolution("grazedtriangle",field,path)
      }
    }),
    new Test("Backtrack", () => {
      val field = grid(10,10,
        polygon(point(2,2)),
        polygon(point(5,6),point(2,3),point(1,3),point(1,5),point(3,5),point(5,7),
                point(7,5),point(5,3),point(5,1),point(3,1),point(3,2),point(6,5)),
        polygon(end))
      val start_state = pathstate(field,point(2,2),end)
      val path = astar(start_state,PathState.cartesianH)
      println("  grid is a 10x10 arena where the heuristic may lead to some false starts")
      println("  start: " + point(2,2))
      println("  goal: " + end)
      path match {
        case Nil => println("  no path found")
        case _ =>
          println("  found path: " + path)
          PathState.drawSolution("backtrack",field,path)
      }
    }),
    new Test("Deja Vu", () => {
      val field = grid(1000,572,
        polygon(point(62,487)),
        polygon(point(100,425),point(100,550),point(500,550),point(500,425)),
        polygon(point(60,196),point(203,66),point(295,189),point(220,351),point(83,322)),
        polygon(point(352,167),point(300,366),point(410,366)),
        polygon(point(417,78),point(514,67),point(583,137),point(417,233)),
        polygon(point(513,301),point(636,399),point(554,480)),
        polygon(point(605,80),point(770,80),point(770,340),point(605,340)),
        polygon(point(700,410),point(700,502),point(778,548),point(858,509),point(858,410),point(788,351)),
        polygon(point(791,115),point(791,115),point(851,77),point(897,127),point(873,364)),
        polygon(point(920,84)))
      val start_state = pathstate(field,point(62,487),point(920,84))
      val path = astar(start_state,PathState.cartesianH)
      println("  this test case looks familiar...we have a 1000x572 grid")
      println("  start: " + point(62,487))
      println("  goal: " + point(920,84))
      path match {
        case Nil => println("  no path found")
        case _ =>
          println("  found path: " + path)
          PathState.drawSolution("dejavu",field,path)
      }
    })
  )

  def point(x: Int, y: Int): Point = {
    new Point(x, y)
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
