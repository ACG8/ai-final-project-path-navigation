package model

/**
  * Created by agieg on 4/19/2017.
  */
class Line(_start: Point, _end: Point) {
  def start(): Point = _start
  def end(): Point = _end
  def intersects(line: Line): Boolean = {
    intersectsHelper(line, excludeEnds = false)
  }

  def intersectsInside(line: Line): Boolean = {
    intersectsHelper(line, excludeEnds = true)
  }

  override def toString: String = {
    "line("+this.start().toString+", "+this.end().toString+")"
  }
  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: Line => this._start == this._end
      case _ => false
    }
  }

  def intersectsHelper(line: Line, excludeEnds: Boolean): Boolean = {
    // Use x1-x4, y1-y4 to make it consistent with the source formula located here:
    // https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
    val x1 = this.start().x()
    val y1 = this.start().y()
    val x2 = this.end().x()
    val y2 = this.end().y()
    val x3 = line.start().x()
    val y3 = line.start().y()
    val x4 = line.end().x()
    val y4 = line.end().y()

    val denominator = (x1 - x2)*(y3-y4) - (y1-y2)*(x3-x4);

    // lines are parallel
    if (denominator == 0) {
    return false
  }

    // Bits of these numerators could be factored out but I'm going to leave it as is so it's more explicit
    val xIntersectionNumerator = (x1*y2 - y1*x2)*(x3-x4) - (x1 - x2)*(x3*y4 - y3*x4)
    val yIntersectionNumerator = (x1*y2 - y1*x2)*(y3-y4) - (y1 - y2)*(x3*y4 - y3*x4)

    // calculate the (x, y) intersection
    val xIntersection = xIntersectionNumerator.toDouble/denominator.toDouble
    val yIntersection = yIntersectionNumerator.toDouble/denominator.toDouble

    // Decide if the X intersection occurs outside the line
    val greaterX = Math.max(this.start().x(), this.end().x())
    val lesserX = Math.min(this.end().x(), this.end().x())
    val xOutOfBounds = xIntersection < lesserX || xIntersection > greaterX

    // Determine if the Y intersection occurs outside the line
    val greaterY = Math.max(this.start().y(), this.end().y())
    val lesserY = Math.min(this.start().y(), this.end().y())
    val yOutOfBounds = yIntersection < lesserY || yIntersection > greaterY

    // If neither point on the intersection is outside this line then the intersection occurs on the line.
    !(xOutOfBounds || yOutOfBounds)
  }
}
