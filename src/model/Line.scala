package model

/**
  * Created by agieg on 4/19/2017.
  */

object Line {
  def getPoints(lines: List[Line]): List[Point] = {
    lines.flatMap(l => List(l.start, l.end))
  }
}
class Line(_start: Point, _end: Point) {
  def start: Point = _start
  def end: Point = _end
  
  def vertices: Set[Point] = {
    Set(start, end)
  }

  def isParallel(line: Line): Boolean = {
    val x1 = this.start.x
    val y1 = this.start.y
    val x2 = this.end.x
    val y2 = this.end.y
    val x3 = line.start.x
    val y3 = line.start.y
    val x4 = line.end.x
    val y4 = line.end.y
    val denominator = (x1 - x2)*(y3-y4) - (y1-y2)*(x3-x4)

    // lines are parallel
    denominator == 0
  }

  def length: Double = {
    val x1 = this.start.x
    val y1 = this.start.y
    val x2 = this.end.x
    val y2 = this.end.y

    Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))
  }

  def intersects(line: Line): Boolean = {
    // Use x1-x4, y1-y4 to make it consistent with the source formula located here:
    // https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
    val x1 = this.start.x
    val y1 = this.start.y
    val x2 = this.end.x
    val y2 = this.end.y
    val x3 = line.start.x
    val y3 = line.start.y
    val x4 = line.end.x
    val y4 = line.end.y

    val denominator = (x1 - x2)*(y3-y4) - (y1-y2)*(x3-x4)

    // lines are parallel
    if (this.isParallel(line)) {
      return false
    }

    // Bits of these numerators could be factored out but I'm going to leave it as is so it's more explicit
    val xIntersectionNumerator = (x1*y2 - y1*x2)*(x3 - x4) - (x1 - x2)*(x3*y4 - y3*x4)
    val yIntersectionNumerator = (x1*y2 - y1*x2)*(y3 - y4) - (y1 - y2)*(x3*y4 - y3*x4)

    // calculate the (x, y) intersection
    val xIntersection = xIntersectionNumerator.toDouble/denominator.toDouble
    val yIntersection = yIntersectionNumerator.toDouble/denominator.toDouble

    // Decide if the X intersection occurs outside the line
    val thisGreaterX = Math.max(this.start.x, this.end.x)
    val thisLesserX = Math.min(this.start.x, this.end.x)
    // ends don't count as intersecting
    val thisXOutOfBounds = xIntersection <= thisLesserX || xIntersection >= thisGreaterX

    // Determine if the Y intersection occurs outside the line
    val thisGreaterY = Math.max(this.start.y, this.end.y)
    val thisLesserY = Math.min(this.start.y, this.end.y)
    val thisYOutOfBounds = yIntersection <= thisLesserY || yIntersection >= thisGreaterY
    // BREAK
    val lineGreaterX = Math.max(line.start.x, line.end.x)
    val lineLesserX = Math.min(line.start.x, line.end.x)
    // ends don't count as intersecting
    val lineXOutOfBounds = xIntersection <= lineLesserX || xIntersection >= lineGreaterX

    // Determine if the Y intersection occurs outside the line
    val lineGreaterY = Math.max(line.start.y, line.end.y)
    val lineLesserY = Math.min(line.start.y, line.end.y)
    val lineYOutOfBounds = yIntersection <= lineLesserY || yIntersection >= lineGreaterY
    
    

    // If one point on the intersection is outside this line then the intersection occurs on the line.
    // One of the points can be outside the line.
    val intersectsThis = !(thisXOutOfBounds && thisYOutOfBounds)
    val intersectsLine = !(lineXOutOfBounds && lineYOutOfBounds)
    val intersects = intersectsThis && intersectsLine
    intersects
  }

  // If a line cuts a polygon
  def cuts(p: Polygon, maxX: Int, maxY: Int): Boolean = {
    val minLength = 0.1
    val (a, b, point) = this.split()
    if (point.inside(p, maxX, maxY)) return true

    if (a.length < 0.1 || b.length < 0.1) return false
    a.cuts(p, maxX, maxY) || b.cuts(p, maxX, maxY)
  }

  def split(): (Line, Line, Point) = {
    val x1 = start.x
    val x2 = end.x
    val y1 = start.y
    val y2 = end.y

    val midpoint = new Point((x1+x2)/2, (y1+y2)/2)

    (new Line(start, midpoint), new Line(midpoint, end), midpoint)
  }

  override def toString: String = {
    "line("+this.start.toString+", "+this.end.toString+")"
  }

  // Two lines are equal if they have the same points. Lines are not directed so the end point on one line
  // can be the start point on the other.
  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: Line => ((this.start == obj.start) &&  (this.end == obj.end)) ||
        ((this.start == obj.end) && (this.end == obj.start))
      case _ => false
    }
  }
}
