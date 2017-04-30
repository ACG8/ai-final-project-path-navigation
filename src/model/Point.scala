package model

import scala.util.Random

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
    val points = p.sides.flatMap(side => List(side.start, side.end))
    val maxX: Rational = points.map(v => v.x).max
    val maxY: Rational = points.map(v => v.y).max
    val minX: Rational = points.map(v => v.x).min
    val minY: Rational = points.map(v => v.y).min
    p.sides.foreach(side => {
      // points on a side are not intersecting
      if (side.intersects(this)) return false
      // points on vertices are not intersecting
      if (side.start ==  this || side.end == this) return false
    })

    if(this.x >= maxX || this.x <= minX || this.y >= maxY || this.y <= minY ) {
      return false
    }

    var ray: Line = null
    var intersectsVertex = true
    var intersections: Set[Point] = null
    while (intersectsVertex) {
      ray = new Line(this, new Point(new Rational(Random.nextInt(10000)+maxX.round+1), new Rational(Random.nextInt(10000)+maxY.round+1)))
      intersections = p.filter(side => ray.intersects(side, includeEnds = true))
        .map(side => ray.getIntersection(side)).toSet
      intersectsVertex = p.flatMap(side => List(side.start, side.end)).exists(v => intersections.contains(v))
    }
    // total number of intersections including, vertex intersection will only count once since we're using
    // a set

//    // if the ray is starting on a vertex of the polygon
//    val startOnVertex: Boolean = p.exists(side => ray.start==side.start || ray.start==side.end)
//    // Don't count the vertex we start on as an intersection.
//    val total = intersections.size - (if (startOnVertex) 1 else 0)
      intersections.size % 2 == 1
  }
}
