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
        case t: Throwable => {
          println(name + " failed: " + t.getMessage)
          t.printStackTrace()
        }
      }
    }
  }

  val start = point(1,1)
  val end = point(9,9)

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
        case _ => {
          println("  found path: " + path)
          PathState.drawSolution("trivialcase",field,path)
        }
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
        case _ => {
          println("  found path: " + path)
          PathState.drawSolution("onetriangle",field,path)
        }
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
        case _ => {
          println("  found path: " + path)
          PathState.drawSolution("centersquare",field,path)
        }
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
        case _ => {
          println("  found path: " + path)
          PathState.drawSolution("grazedtriangle",field,path)
        }
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
