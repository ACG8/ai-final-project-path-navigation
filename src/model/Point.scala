package model

/**
  * Created by agieg on 4/19/2017.
  */
class Point(_x: Int, _y: Int ){
  def x: Int = _x
  def y: Int = _y

  override def toString: String = {
    "("+this.x+", "+this.y+")"
  }

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: Point => obj.x == this.x && obj.y == this.y
      case _ => false
    }
  }

  def inside(p :Polygon): Boolean = {
    val pointsInPolygon: Set[Point] = p.flatMap(line => List(line.start, line.end)).toSet
    val maxX = pointsInPolygon.map(p => p.x).max
    val maxY = pointsInPolygon.map(p => p.y).max
    if (pointsInPolygon.contains(this)) {
      // Points on a vertex do not count as being inside.
      return false
    }
    if (p.sides.exists(_.intersects(this))) {
      // Points on a line of the polygon are considered to be outside the polygon.
      return false
    }
    val ray: Line = new Line(this, new Point(maxX+1, maxY+1))
    // total number of intersections including, vertex intersection will only count once bec
    val intersections: Set[(Double, Double)] = p.filter(side => ray.intersects(side, includeEnds = true))
      .map(side => ray.getIntersection(side)).toSet

    // if the ray is starting on a vertex of the polygon
    val startOnVertex: Boolean = p.exists(side => this==side.start || this==side.end)
    // The total number of intersections, counting vertex intersections as one intersection instead of 2.
    val total = intersections.size - (if (startOnVertex) 1 else 0)
    total % 2 == 1
  }
}
