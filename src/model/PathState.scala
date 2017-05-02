package model

import java.awt.image.BufferedImage
import java.awt.{Graphics2D,Color,Font,BasicStroke}
import java.awt.geom._

/**
  * Created by agieg on 4/18/2017.
  *
  * We assume that there is only 1 goal state, as per the instructions
  */

class PathState(_grid: Grid, _position: Point, _goal: Point) extends State[PathState] {
  val position:Point = _position
  val goal:Point = _goal
  val grid:Grid = _grid

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: PathState => this.grid == obj.grid && this.position == obj.position && this.goal == obj.goal
      case _ => false
    }
  }

  def isGoalState: Boolean = goal == position

  def size: Int = {
    grid.allVertices.count( _ => true)
  }
  // The second value is the cost of reaching the state from the current state.
  def successors: List[(PathState,Double)] = {
    grid.allVertices.filter(_ != position)
      .map(v => new Line(position, v)) // all lines from current position to possible vertices
      .filter(line => !grid.overlaps(line))
      .map(line => (new PathState(grid, line.end, goal), line.length)).toList
  }
  def String: String = position.toString

  def onSamePolygon(a: Point, b: Point): Boolean = {
    grid.getPolygonsContainingPoint(a).exists(poly => grid.getPolygonsContainingPoint(b).contains(poly))
  }

  def nonNeighborsOnSamePolygon(a: Point): Set[Point] = {
    grid.allVertices.filter(point => !isNeighbor(a, point) && onSamePolygon(a, point) && point != a)
  }

  def isNeighbor(a: Point, b: Point): Boolean = {
    grid.getNeighbors(a).contains(b)
  }

  override def toString: String = position.toString
}



object PathState {

  /**
    * Below are heuristic functions associated with PathState
    */
  val cartesianH: PathState => Double = (state) => {
    val (x0,y0) = (state.position.x,state.position.y)
    val (x1,y1) = (state.goal.x,state.goal.y)
    val (dx,dy) = (x1-x0,y1-y0)
    Math.sqrt((dx*dx+dy*dy).toDouble)
  }
  val lookAheadH: PathState => Double = (state) => {
    state.successors.map{ case(s,_) => cartesianH(s)}.min
  }



  /**
    * *code for drawing solutions is based on example from
    * https://raw.githubusercontent.com/otfried/cs109-scala/master/examples/drawing.scala
    *
    * This is not actually necessary, but helps us see what is happening
    */
  def drawSolution(title: String, caption:String, grid:Grid, path:List[PathState]): Unit = {
    val gridSpacing = Math.pow(10 ,Math.log10(List(grid.maxX.round, grid.maxY.round).max).floor-1)
    val start = path.head.position
    val end = path.last.position
    // TODO: Should adjust scale depending on size of inputs
    val scale = new Rational(1000)/grid.dimensions._1
    val goalsize = new Rational(3,10)

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
        g.draw(new Line2D.Double(x * scale.round.toInt, 0, x * scale.round.toInt, grid.maxY.round * scale.round.toInt))
      }
    }
    for (y <- 0 until grid.maxY.round.toInt) {
      if (y%gridSpacing == 0) {
        g.setStroke(new BasicStroke()) // reset to default
        g.draw(new Line2D.Double(0, y * scale.round.toInt, grid.maxX.round.toInt * scale.round, y * scale.round.toInt))
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
    if (path.nonEmpty) {
      g.setColor(Color.GREEN)
      for (line <- path.iterator.sliding(2)) {
        g.setStroke(new BasicStroke())  // reset to default
        val p0 = line.head.position
        val p1 = line.last.position
        g.draw(new Line2D.Double((p0.x*scale).round, (p0.y*scale).round, (p1.x*scale).round, (p1.y*scale).round))
      }
    }


    // draw endpoints of path
    val two = new Rational(2)
    g.setColor(Color.GREEN) // green = path start
    g.fill(new Ellipse2D.Double(((start.x-goalsize/two)*scale).round, ((start.y-goalsize/two)*scale).round, (goalsize*scale).round, (goalsize*scale).round))
    g.setColor(Color.RED) // red = path end
    g.fill(new Ellipse2D.Double(((end.x-goalsize/two)*scale).round, ((end.y-goalsize/two)*scale).round, (goalsize*scale).round, (goalsize*scale).round))

    // draw caption
    val fontsize = (scale/two).round
    val titleX = (scale*new Rational(1,10)).toFloat
    val titleY = size._2.toFloat-(scale*new Rational(1,10)).toFloat

    g.setColor(Color.BLACK) // a darker green
    g.setFont(new Font("Batang", Font.PLAIN, fontsize.toInt))
    g.drawString(title+", "+caption, titleX, titleY)

    // done with drawing
    g.dispose()

    // write image to a file
    println("  [graphical representation saved in output/"++title++".png]")
    javax.imageio.ImageIO.write(canvas, "png", new java.io.File("output/"++title++".png"))
  }
}