package model

import scala.collection.mutable

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

  def getIntersection(line: Line): Point = {

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


    // Bits of these numerators could be factored out but I'm going to leave it as is so it's more explicit
    val xIntersectionNumerator = (x1*y2 - y1*x2)*(x3 - x4) - (x1 - x2)*(x3*y4 - y3*x4)
    val yIntersectionNumerator = (x1*y2 - y1*x2)*(y3 - y4) - (y1 - y2)*(x3*y4 - y3*x4)

    // calculate the (x, y) intersection
    val xIntersection = xIntersectionNumerator.toDouble/denominator.toDouble
    val yIntersection = yIntersectionNumerator.toDouble/denominator.toDouble
    new Point(xIntersection, yIntersection)
  }

  def intersects(point: Point): Boolean = {
    new Line(this.start, point).length + new Line(point, this.end).length == this.length
  }

  def intersects(line: Line): Boolean = {
    intersects(line, includeEnds = false)
  }
  def intersects(line: Line, includeEnds: Boolean): Boolean = {
    // lines are parallel
    if (this.isParallel(line)) {
      return false
    }

    val intersection = getIntersection(line)
    val xIntersection = intersection.x
    val yIntersection = intersection.y
    // Decide if the X intersection occurs outside the line
    val thisGreaterX = Math.max(this.start.x, this.end.x)
    val thisLesserX = Math.min(this.start.x, this.end.x)

    val leq: (Double, Double) => Boolean = { (a, b) => a <= b }
    val lt: (Double, Double) => Boolean = { (a, b) => a < b }
    val geq: (Double, Double) => Boolean = { (a, b) => a >= b }
    val gt: (Double, Double) => Boolean = { (a, b) => a > b }

    val gtOp = if (includeEnds) gt else geq
    val ltOp = if (includeEnds) lt else leq
    // ends don't count as intersecting
    val thisXOutOfBounds = ltOp(xIntersection, thisLesserX) || gtOp(xIntersection, thisGreaterX)

    // Determine if the Y intersection occurs outside the line
    val thisGreaterY = Math.max(this.start.y, this.end.y)
    val thisLesserY = Math.min(this.start.y, this.end.y)
    val thisYOutOfBounds = ltOp(yIntersection, thisLesserY) || gtOp(yIntersection, thisGreaterY)

    val lineGreaterX = Math.max(line.start.x, line.end.x)
    val lineLesserX = Math.min(line.start.x, line.end.x)

    // ends don't count as intersecting




    val lineXOutOfBounds = ltOp(xIntersection, lineLesserX) || gtOp(xIntersection, lineGreaterX)

    // Determine if the Y intersection occurs outside the line
    val lineGreaterY = Math.max(line.start.y, line.end.y)
    val lineLesserY = Math.min(line.start.y, line.end.y)
    val lineYOutOfBounds = ltOp(yIntersection, lineLesserY) || gtOp(yIntersection, lineGreaterY)

    // If one point on the intersection is outside this line then the intersection occurs on the line.
    // One of the points can be outside the line.
    val intersectsThis = !(thisXOutOfBounds && thisYOutOfBounds)
    val intersectsLine = !(lineXOutOfBounds && lineYOutOfBounds)
    val intersects = intersectsThis && intersectsLine
    intersects
  }

  // If a line cuts a polygon
  def cuts(p: Polygon, maxX: Int, maxY: Int): Boolean = {
    println("RUNNING CUTS")
    val minLength = 0.1
    val x = this.split()
    val queue: mutable.Queue[(Line, Line, Point)] = new mutable.Queue
    queue.enqueue(x)
    while (true) {
      val (a, b, point) = queue.dequeue()
      if (point.inside(p, maxX, maxY)) return true

      if (a.length < minLength || b.length < minLength) return false
      queue.enqueue(a.split())
      queue.enqueue(b.split())
    }
    throw new IllegalStateException("This should be unreachable.")
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

  // If either end of the polygon is a given point.
  def hasEndPoint(point: Point): Boolean = {
    this.start == point || this.end == point
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
