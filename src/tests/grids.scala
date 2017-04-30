package tests

import model._

/**
  * Created by agieg on 4/29/2017.
  */
object grids {
  // Do not change this variable, make a new one if you need it. Tests rely on this being the way it is.
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
