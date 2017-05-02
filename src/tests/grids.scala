package tests

import model._
import tests.astarTests._

/**
  * Created by agieg on 4/29/2017.
  */
object grids {

  // commonly used points
  private val start1_1 = point(1,1)
  private val end9_9 = point(9,9)
  private val end19_19 = point(19,19)
  // Do not change this variable, make a new one if you need it. Tests rely on this being the way it is.

  // Grids for basic tests of functionality (10x10), with 1 polygon
  val trivialCase: Grid = grid(10,10,polygon(start1_1),polygon(end9_9))
  val oneTriangle: Grid = grid(10,10,polygon(start1_1),polygon(point(3,6),point(7,5),point(5,2)),polygon(end9_9))
  val centerSquare: Grid = grid(10,10,polygon(start1_1),polygon(point(3,3),point(7,3),point(7,7),point(3,7)),polygon(end9_9))
  val grazedTriangle: Grid = grid(10,10,polygon(start1_1),polygon(point(3,3),point(7,3),point(7,7)),polygon(end9_9))
  val backtrack: Grid = grid(10,10,
    polygon(point(2,2)),
    polygon(point(5,6),point(2,3),point(1,3),point(1,5),point(3,5),point(5,7),
            point(7,5),point(5,3),point(5,1),point(3,1),point(3,2),point(6,5)),
    polygon(end9_9))

  val cruel: Grid = grid(20,20,
    polygon(start1_1),
    rectangle(1,16,4,19),
    rectangle(9,1,12,4),
    rectangle(16,1,19,4),
    polygon(point(1,9),point(1,13),point(4,13),point(5,11)),
    polygon(point(2,4),point(2,5),point(8,11),point(5,14),point(5,17),point(10,17),point(12,15),point(17,19),
      point(19,17),point(15,12),point(18,9),point(15,4),point(11,8),point(5,2),point(4,3),point(18,17),point(17,18),
      point(3,4))
  )
  // Fairly easy grids (20x20) with 5 polygons
  val easy0: Grid = grid(20,20,
    polygon(start1_1),
    polygon(point(1,9),point(1,12),point(7,12),point(12,5),point(12,1),point(9,1),point(9,4),point(5,9)),
    rectangle(1,19,13,14),
    rectangle(9,13,11,10),
    rectangle(14,18,19,13),
    polygon(point(12,12),point(19,12),point(19,1),point(16,1),point(16,5),point(12,9)),
    polygon(end19_19)
  )
  val easy1: Grid = grid(20,20,
    polygon(start1_1),
    rectangle(2,2,4,4),
    rectangle(1,5,4,10),
    rectangle(5,2,6,4),
    rectangle(7,7,18,18),
    rectangle(7,2,14,6),
    polygon(end19_19)
  )
  val easy2: Grid = grid(20,20,
    polygon(start1_1),
    rectangle(3,3,5,5),
    polygon(point(1,7),point(1,18),point(10,18),point(10,14),point(8,14),point(8,16),point(4,16),point(4,15),
      point(2,10),point(2,7)),
    polygon(point(7,1),point(7,5),point(5,7),point(5,15),point(7,15),point(7,8),point(9,6),point(12,6),point(16,2),
      point(13,2),point(11,4),point(9,1)),
    rectangle(9,13,16,8),
    rectangle(12,17,19,15),
    polygon(end19_19)
  )
  val easy3: Grid = grid(20,20,
    polygon(start1_1),
    polygon(point(2,6),point(3,8),point(7,3),point(5,2)),
    rectangle(1,16,12,10),
    rectangle(13,17,18,15),
    rectangle(14,14,17,11),
    rectangle(9,9,19,1),
    polygon(end19_19)
  )
  val easy4: Grid = grid(20,20,
    polygon(start1_1),
    rectangle(1,16,6,9),
    rectangle(8,16,19,14),
    rectangle(12,8,19,12),
    polygon(point(7,13),point(10,13),point(13,2),point(7,10)),
    polygon(point(1,8),point(7,8),point(9,4),point(1,4)),
    polygon(end19_19)
  )
  val easy5: Grid = grid(20,20,
    polygon(start1_1),
    rectangle(13,17,19,13),
    rectangle(9,1,19,7),
    polygon(point(1,8),point(7,8),point(7,1),point(4,1),point(5,6),point(1,4)),
    polygon(point(9,9),point(11,13),point(13,12),point(13,9)),
    polygon(point(1,10),point(1,12),point(5,12),point(5,18),point(12,18),point(8,10)),
    polygon(end19_19)
  )


  val med1: Grid = grid(20,20,
    polygon(point(6,6)),
    rectangle(11,1,15,6),
    rectangle(6,14,9,19),
    rectangle(10,17,16,19),
    rectangle(17,17,18,19),
    polygon(point(4,6),point(3,8),point(6,10),point(10,5),point(10,1),point(7,2),point(6,4),point(8,5),point(6,8)),
    polygon(point(10,14),point(10,16),point(19,16),point(19,7),point(14,7),point(14,10),point(17,10),point(17,14)),
    rectangle(9,7,10,10),
    polygon(point(1,11),point(1,13),point(13,13),point(13,7),point(11,7),point(11,11)),
    polygon(end19_19)
  )
  val med2: Grid = grid(20,20,
    polygon(point(9,10)),
    polygon(point(8,11),point(10,13),point(12,8),point(8,7),point(8,10),point(10,9),point(10,11)),
    polygon(point(11,13),point(12,13),point(12,11)),
    polygon(point(13,9),point(14,10),point(14,8)),
    polygon(point(13,10),point(15,14),point(7,16),point(5,4),point(13,4),point(13,8),point(7,5),point(7,14),
      point(13,14)),
    polygon(point(4,11),point(5,19),point(17,17),point(15,1),point(2,1),point(4,9),point(4,2),point(14,3),point(16,15),
      point(6,18)),
    rectangle(7,20,11,19),
    rectangle(15,20,16,18),
    polygon(point(17,19),point(19,17),point(18,17),point(17,18)),
    polygon(end19_19)
  )
  val med3: Grid = grid(20,20,
    polygon(start1_1),
    rectangle(4,5,9,2),
    rectangle(1,8,11,6),
    rectangle(10,4,11,1),
    rectangle(3,17,10,10),
    rectangle(12,17,14,2),
    rectangle(15,20,16,15),
    rectangle(15,3,19,7),
    rectangle(15,9,19,14),
    polygon(end19_19)
  )
  val med4: Grid = grid(20,20,
    polygon(point(6,6)),
    polygon(point(1,3),point(1,9),point(9,9),point(9,1),point(2,1),point(2,2),point(8,2),point(8,8),point(2,8),
      point(2,3)),
    rectangle(3,3,5,5),
    rectangle(1,10,6,15),
    rectangle(2,16,5,19),
    rectangle(7,11,14,16),
    rectangle(10,4,19,9),
    rectangle(15,11,19,16),
    rectangle(9,17,13,19),
    polygon(end19_19)
  )

  // Grids for fun
  val dejaVu: Grid = grid(1000,572,
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

  private def point(x: Int, y: Int): Point = new Point(new Rational(x), new Rational(y))

//  private def line(start: Point, end: Point): Line = new Line(start, end)

  private def polygon(points: Point*) = new Polygon(points.toList)

  private def grid(maxX: Int, maxY: Int, polygons: Polygon*) = new Grid(maxX, maxY, polygons:_*)

  // simple shape functions for convenience
  private def rectangle(x0: Int, y0: Int, x1: Int, y1: Int) =
    polygon(point(x0,y0),point(x0,y1),point(x1,y1),point(x1,y0))

}
