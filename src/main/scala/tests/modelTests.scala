package main.scala.tests

import java.awt.geom.{Ellipse2D, Line2D}
import java.awt.{BasicStroke, Color}
import java.awt.image.BufferedImage

import main.scala.model._

/**
  * Created by agieg on 4/20/2017.
  */
object modelTests {

  def runTests(): Unit = {
    runTestsHelper(tests)
    runTestsHelper(centerSquareTests)
    runTestsHelper(insideTests)
    runTestsHelper(midpointIsInsideTests)
    runTestsHelper(intersectionTests)
    runTestsHelper(successorsTest)
  }

  def runTestsHelper(tests: List[Test]): Unit = {
    tests.foreach(_.run())
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

  val centerSquareTests: List[Test] = List(
    new Test("test1", () => {
      val l = new Line(point(3, 3), point(9, 9))
      assert(l.intersects(point(7,7)))
    })
  )

  // Tests for Point.inside()
  val insideTests: List[Test] = List(
    new Test("vertex not inside", () => {
      val p = point(0,2)
      val a = poly(p, point(2,2), point(2,0))
      assert(!p.inside1(a), "vertex should not be inside")
    }),
    new Test("vertex inside", () => {
      val square = poly(new Point(0, 0), new Point(0, 2), new Point(2, 2), new Point(2, 0))
      val point: Point = new Point(1, 1)
      assert(point.inside1(square), "point should be inside square")
    }),
    new Test("vertex outside 1", () => {
      val square = poly(new Point(0, 0), new Point(0, 2), new Point(2, 2), new Point(2, 0))
      val point: Point = new Point(2, 2)
      assert(!point.inside1(square), "point should be outside square")
    }),
    new Test("vertex outside 2", () => {
      val square = poly(new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1))
      val point: Point = new Point(1, 1)
      assert(!point.inside1(square), "point should be outside square")
    }),
    new Test("point on line is outside", () => {
      val square = poly(new Point(1, 1), new Point(3, 1), new Point(3, 3), new Point(1, 3))
      val point: Point = new Point(2, 1)
      assert(!point.inside1(square))
    }),
    new Test("diamond polygon inside test", () => {
      val square = poly(new Point(1, 2), new Point(2, 1), new Point(3, 2), new Point(2, 3))
      val point: Point = new Point(2, 2)
      assert(point.inside1(square), "point should be inside square")
    })
  )
  val midpointIsInsideTests = List(
    new Test("Backtrack polygon test", () => {
      val p = new Polygon(List(point(5, 6), point(2, 3), point(1, 3), point(1, 5), point(3, 5), point(5, 7),
        point(7, 5), point(5, 3), point(5, 1), point(3, 1), point(3, 2), point(6, 5)))

      val l = new Line(point(6, 5), point(7, 5))
      assert(l.midpoint.inside1(p))
    }),
    new Test("Backtrack polygon test2", () => {
      val p = new Polygon(List(point(5, 6), point(2, 3), point(1, 3), point(1, 5), point(3, 5), point(5, 7),
        point(7, 5), point(5, 3), point(5, 1), point(3, 1), point(3, 2), point(6, 5)))

      val l = new Line(point(2, 3), point(5, 7))
      assert(l.midpoint.inside1(p), "midpoint should be inside backtrack polygon")
    }),
    new Test("deja vu square test", () => {
      val square = poly(point(100,425),point(100,550),point(500,550),point(500,425))
      val a = new Line(new Point(100, 425), new Point(410, 366))
      assert(!a.midpoint.inside1(square), "midpoint should not be in square")
    }),
    new Test("deja vu triangle test", () => {
      val hexagon= poly(point(700,410),point(700,502),point(778,548),point(858,509),point(858,410),point(788,351))

      val a = new Line(new Point(100, 425), new Point(410, 366))

      assert(!a.midpoint.inside1(hexagon), "midpoint should not be in hexagon")
    }),
    new Test("deja vu pentagon test", () => {
      val pentagon = poly(point(60,196),point(203,66),point(295,189),point(220,351),point(83,322))

      val a = new Line(point(60,196), point(295,189))

      assert(a.midpoint.inside1(pentagon), "midpoint should be in pentagon")
    }),
    new Test("deja vu pentagon test", () => {
      //val pentagon = poly(point(60,196),point(203,66),point(295,189),point(220,351),point(83,322))
      val x: List[Polygon] = List()
      //val grid = new Grid(400, 400, x:_*)
      val a = new Line(point(60,196), point(295,189))
      val side = line(point(220,351),point(295,189))
      val ray = new Line(a.midpoint, new Point(3907, 5825))
      //draw("albert-test", grid, List(side, ray), List(new Point(100, 100), side.getIntersection(ray)))

      assert(side.intersects(ray, includeEnds = true),
        "line should intersect pentagon")
    })
  )

  val successorsTest = List(
    new Test("deja vu successors", () => {
      val field = grids.dejaVu
      val state = new PathState(field, new Point(100, 425), point(920,84))
      assert(state.successors.exists{case (ps, _) => ps.position == point(410,366)})
    })
  )

  val intersectionTests = List(
    new Test("intersection test", () => {
      val l1 = new Line((new Rational(13, 2), new Rational(5)), (new Rational(8), new Rational(8)))
      val l2 = line((1, 5), (3, 5))

      assert(!l1.intersects(l2, includeEnds = true))
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
    })
  )
  val tests: List[Test] = List(
    new Test("nonConnectingEnds", () => {
      try {
        val first = line(point(0, 0), point(0, 4))
        val second = line(point(0, 4), point(2, 3))
        val third = line(point(2, 3), point(0, 1))
        new Polygon(first, second, third)
        assert(assertion = false, "The third line and the first line do not connect.")
      } catch {
        case _: IllegalArgumentException => // exception is supposed to be thrown so don't do anything
      }
    }),
    new Test("validTriangle", () => {
      try {
        poly(point(0,0), point(0,4), point(2,3))
      } catch {
        case e: Throwable => assert(assertion = false, "An exception should not have been thrown: "+e.getMessage)
      }
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
    }),
    new Test("line intersects when ends count", () => {
      val line2 = new Line(point(5, 2), point(5, 5))
      val line = new Line(point(0, 0), point(5, 5))
      assert(line.intersects(line2, includeEnds = true), "point should intersect when vertices matter")
    }),
    new Test("line intersects point", () => {
      val vertex = point(1,1)
      val intersector = line(point(0,0),point(2,2))
      assert(intersector.intersects(vertex), "lines should intersect vertices")
    })
  )

  def point(x: Int, y: Int): Point = {
    new Point(x, y)
  }

  def line(start: Point, end: Point): Line = {
    new Line(start, end)
  }

  def line(start: (Int, Int), end: (Int, Int)): Line = {
    new Line(start match {case (a,b) => new Point(a,b)}, end match {case (a,b) => new Point(a,b)})
  }

  def polygon(sides: Line*): Polygon = {
    new Polygon(sides:_*)
  }

  def poly(points: Point*): Polygon = {
    new Polygon(points.toList)
  }

  def draw(title: String,grid: Grid, lines: List[Line] = List(), points: List[Point] = List()): Unit = {
    val gridSpacing = Math.pow(10 ,Math.log10(List(grid.maxX.round, grid.maxY.round).max).floor-1)
    // TODO: Should adjust scale depending on size of inputs
    val scale = new Rational(1000)/grid.dimensions._1
    val goalsize = new Rational(3,10)*List(grid.maxX, grid.maxY).max/new Rational(20)

    // Size of image
    val size = (grid.dimensions._1*scale, grid.dimensions._2*scale)

    // create an image
    val canvas: BufferedImage = new BufferedImage(size._1.round.toInt, size._2.round.toInt, BufferedImage.TYPE_INT_RGB)

    // get Graphics2D for the image
    val g = canvas.createGraphics()

    // clear background
    g.setColor(Color.WHITE)
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    g.setColor(Color.LIGHT_GRAY)
    for (x <- 0 until grid.maxX.round.toInt) {
      if (x%gridSpacing == 0) {
        g.setStroke(new BasicStroke()) // reset to default
        g.draw(new Line2D.Double(x * scale.round.toInt, 0, x * scale.round.toInt, grid.maxY.round.toInt * scale.round.toInt))
      }
    }
    for (y <- 0 until grid.maxY.round.toInt) {
      if (y%gridSpacing == 0) {
        g.setStroke(new BasicStroke()) // reset to default
        g.draw(new Line2D.Double(0, y * scale.round, grid.maxX.round * scale.round, y * scale.round))
      }
    }

    // enable anti-aliased rendering (prettier lines and circles)
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
      java.awt.RenderingHints.VALUE_ANTIALIAS_ON)

    // draw the polygons
    g.setColor(Color.BLUE)
    for (poly <- grid)
      for (line <- poly) {
        g.setStroke(new BasicStroke())  // reset to default
        g.draw(new Line2D.Double((line.start.x*scale).round, (line.start.y*scale).round, (line.end.x*scale).round, (line.end.y*scale).round))
      }

    // draw the path in green
    g.setColor(Color.GREEN)
    for (line <- lines) {
      g.setStroke(new BasicStroke())  // reset to default
      val p0 = line.start
      val p1 = line.end

      g.draw(new Line2D.Double((p0.x*scale).round, (p0.y*scale).round, (p1.x*scale).round, (p1.y*scale).round))
    }
    val two = new Rational(2)
    g.setColor(Color.RED) // red = path end
    points.foreach(end => {
      println("drawing point")
      g.fill(new Ellipse2D.Double(((end.x-goalsize/two)*scale).round,
        ((end.y-goalsize/two)*scale).round,(goalsize*scale).round,(goalsize*scale).round))
    })
    // done with drawing
    g.dispose()
    // write image to a file
    javax.imageio.ImageIO.write(canvas, "png", new java.io.File("output/"++title++".png"))
  }

}
