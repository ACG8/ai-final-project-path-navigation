package main.scala.tests

import java.io._
import scala.compat.Platform.EOL
import main.scala.model._

/**
  * Created by Ananda on 4/30/2017.
  */
object Test {

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

  //simple function to create a test.
  def test(grid: Grid, //first and last points must be start and end points
           name: String,
           description: String,
           algorithm: ((PathState, PathState => Double) => (List[PathState],Int,Double)),
           heuristic: PathState => Double): Test = {
    new Test(name, () => {
      val start = grid.polygons.head.vertices.head
      val end = grid.polygons.last.vertices.head
      val start_state = pathstate(grid,start,end)
      val solution = algorithm(start_state,PathState.cartesianH)
      println("  " + description)
      println("  start: " + start)
      println("  goal: " + end)
      val outputstr = solution match {
        case (Nil,_,_) =>
          println("  no path found")
          name+",no path found"
        case (path,iterations,length) =>
          println("  found path: " + path + " (length=" + length + ")")
          println("  " + iterations + " iterations taken")
          PathState.drawSolution(name,"length="+length+",iterations="+iterations,grid,path)
          name+","+length+","+iterations
      }
      // Write info to output file
      val write = new PrintWriter(new FileOutputStream(new File("output/output.txt"),true))
      write.write(outputstr + EOL)
      write.close()
    })
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
