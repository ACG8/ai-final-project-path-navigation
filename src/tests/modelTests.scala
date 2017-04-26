package tests

import model.{Line, Point, Polygon}

/**
  * Created by agieg on 4/20/2017.
  */
object modelTests {

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
    new Test("nonConnectingEnds", () => {
      try {
        val first = line(point(0, 0), point(0, 4))
        val second = line(point(0, 4), point(2, 3))
        val third = line(point(2, 3), point(0, 1))
        new Polygon(first, second, third)
        assert(assertion = false, "The third line and the first line do not connect.")
      } catch {
        case e: IllegalArgumentException => // exception is supposed to be thrown so don't do anything
      }
    }),
    new Test("validTriangle", () => {
      try {
        poly(point(0,0), point(0,4), point(2,3))
      } catch {
        case e: Throwable => assert(assertion = false, "An exception should not have been thrown: "+e.getMessage)
      }
    }),
    new Test("linesIntersect", () => {
      val first = line(point(2, 2), point(2, 4))
      val second = line(point(1,3), point(3, 3))
      assert(first.intersects(second), "Lines should intersect.")
    }),
    new Test("linesParallel", () => {
      val first = line(point(2, 2), point(4, 2))
      val second = line(point(2, 1), point(4, 1))
      assert(!first.intersects(second), "Parallel lines should not intersect.")
    }),
    new Test("nonParallelNonIntersecting", () => {
      val first = line(point(1, 1), point(3, 1))
      val second = line(point(2, 2), point(2, 3))
      assert(!first.intersects(second), "These lines should not intersect.")
    }),
    new Test("overlapping lines dont intersect", () => {
      val first = line(point(1, 5), point(5, 5))
      val second = line(point(1, 5), point(5, 5))
      assert(!first.intersects(second), "Overlapping lines should not intersect.")
    }),
    new Test("lines that meet at the end dont intersect", () => {
      val first = line(point(1, 5), point(5, 5))
      val second = line(point(5, 5), point(2, 3))
      assert(!first.intersects(second), "Lines that meet at an end should not intersect.")
    }),
    new Test("polygons that touch at corner should not intersect.", () => {
      val a = poly(point(1, 1), point(3, 1), point(2, 3))
      val b = poly(point(2, 3), point(4, 3), point(3, 4))

      assert(!a.overlaps(b), "These polygons should not overlap.")
    }),
    new Test("polygons that share a line should not intersect.", () => {
      val a = poly(point(5,1), point(5,2), point(4,2))
      val b = poly(point(5,3), point(5,2), point(4,2))
      assert(!a.overlaps(b), "Polygons that share a line but do not overlap otherwise should not overlap.")
    }),
    new Test("overlapping polgyons", () => {
      val a = poly(point(0,2), point(2,2), point(2,0))
      val b = poly(point(0,3), point(0,1), point(2,1))
      assert(a.overlaps(b), "These polygons should overlap.")
    })
//    new Test("vertex not inside", () => {
//      val p = point(0,2)
//      val a = poly(p, point(2,2), point(2,0))
//      assert(!p.inside(a, 10 , 10), "vertex should not be inside")
//    }),
//    new Test("line cuts square", () => {
//      val square = poly(point(0, 0), point(0, 2), point(2, 2), point(2, 0))
//      val line: Line = new Line(new Point(0, 0), new Point(2, 2))
//      assert(line.cuts(square, 3, 3), "line should cut square")
//    }),
//    new Test("vertex inside", () => {
//      val square = poly(new Point(0, 0), new Point(0, 2), new Point(2, 2), new Point(2, 0))
//      val point: Point = new Point(1, 1)
//      assert(point.inside(square, 4, 4), "point should be inside square")
//    })
//    new Test("vertex outside 1", () => {
//      val square = poly(new Point(0, 0), new Point(0, 2), new Point(2, 2), new Point(2, 0))
//      val point: Point = new Point(2, 2)
//      assert(!point.inside(square, 4, 4), "point should be outside square")
//    }),
//    new Test("vertex outside 2", () => {
//      val square = poly(new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1))
//      val point: Point = new Point(1, 1)
//      assert(!point.inside(square, 0, 0), "point should be outside square")
//    }),
//    new Test("line intersects when ends count", () => {
//      val line2 = new Line(point(5, 2), point(5, 5))
//      val line = new Line(point(0, 0), point(5, 5))
//      assert(line.intersects(line2, includeEnds = true), "point should intersect when vertices matter")
//    })
  )

  def point(x: Int, y: Int): Point = {
    new Point(x, y)
  }

  def line(start: Point, end: Point): Line = {
    new Line(start, end)
  }

  def polygon(sides: Line*): Polygon = {
    new Polygon(sides:_*)
  }

  def poly(points: Point*): Polygon = {
    new Polygon(points.toList)
  }
}
