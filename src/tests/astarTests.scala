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

  val tests: List[Test] = List(
    new Test("trivialCase", () => {
      val start = point(0,0)
      val end = point(10,10)
      val field = grid(10,10,polygon(start),polygon(end))
      val start_state = pathstate(field,start,end)
      val path = astar(start_state,PathState.cartesianH)
      println("  grid is empty 10x10 arena")
      println("  start: " + start)
      println("  goal: " + end)
      path match {
        case Nil => println("  no path found")
        case _ => println("  found path: " + path)
      }
    }),
    new Test("lesstrivialCase", () => {
      val start = point(0,0)
      val end = point(10,10)
      val field = grid(10,10,polygon(start),polygon(point(5,2),point(3,8),point(4,6)),polygon(end))
      val start_state = pathstate(field,start,end)
      val path = astar(start_state,PathState.cartesianH)
      println("  grid is 10x10 arena with polygon (5,2),(3,8),(4,6)")
      println("  start: " + start)
      println("  goal: " + end)
      path match {
        case Nil => println("  no path found")
        case _ => println("  found path: " + path)
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
