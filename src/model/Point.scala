package model

/**
  * Created by agieg on 4/19/2017.
  */
class Point(_x: Double, _y: Double ){
  def x: Double = _x
  def y: Double = _y

  override def toString: String = {
    "("+this.x+", "+this.y+")"
  }

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: Point => obj.x == this.x && obj.y == this.y
      case _ => false
    }
  }

  def inside(p :Polygon, maxX: Int, maxY: Int): Boolean = {
    val ray: Line = new Line(this, new Point(maxX+1, maxY+1))
    val intersections = p.map(side => ray.intersects(side)).count(x => x)
    intersections % 2 == 1
  }
}
