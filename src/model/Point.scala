package model

/**
  * Created by agieg on 4/19/2017.
  */
object Point {

}
class Point(_x: Rational, _y: Rational ){
  def this(x: Int, y: Int) {
    this(new Rational(x), new Rational(y))
  }
  def x: Rational = _x
  def y: Rational = _y

  override def toString: String = {
    "("+this.x+", "+this.y+")"
  }

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: Point => obj.x == this.x && obj.y == this.y
      case _ => false
    }
  }


  def inside1(p :Polygon): Boolean = {
    var maxX: Rational = new Rational(0)
    var maxY: Rational = new Rational(0)
    p.sides.foreach(side => {
      // points on a side are not intersecting
      if (side.intersects(this)) return false
      // points on vertices are not intersecting
      if (side.start ==  this || side.end == this) return false
      maxX = if (side.start.x > maxX) side.start.x else maxX
      maxX = if (side.end.x > maxX) side.end.x else maxX
      maxY = if (side.start.y > maxY) side.start.y else maxY
      maxY = if (side.end.y > maxY) side.end.y else maxY
    })
    val ray: Line = new Line(this, new Point(maxX+new Rational(1), maxY+new Rational(1)))

    // total number of intersections including, vertex intersection will only count once since we're using
    // a set
    val intersections: Set[Point] = p.filter(side => ray.intersects(side, includeEnds = true))
      .map(side => ray.getIntersection(side)).toSet
    // if the ray is starting on a vertex of the polygon
    val startOnVertex: Boolean = p.exists(side => this==side.start || this==side.end)
    // Don't count the vertex we start on as an intersection.
    val total = intersections.size - (if (startOnVertex) 1 else 0)
    total % 2 == 1
  }
}
