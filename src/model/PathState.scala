package model

/**
  * Created by agieg on 4/18/2017.
  *
  * We assume that there is only 1 goal state, as per the instructions
  */

class PathState(grid: Grid, _position: Point, _goal: Point) extends State[PathState] {
  def position = _position
  def goal = _goal

  def isGoalState: Boolean = goal == position

  // The second value is the cost of reaching the state from the current state.
  def successors: List[(PathState,Double)] = {
    grid.allVertices.map(v => new Line(position, v)) // all lines from current position to possible vertices
      .filter(line => !grid.overlaps(line))
      .map(line => (new PathState(grid, line.end, goal), line.length)).toList
  }
  def asString: String = position.toString

  /**
    * Below are heuristic functions associated with PathState
    */

  val cartesianH: PathState => Double = (state: PathState) => {
    val (x0,y0) = (state.position.x,state.position.y)
    val (x1,y1) = (state.goal.x,state.goal.y)
    val (dx,dy) = (x1-x0,y1-y0)
    Math.sqrt(dx*dx+dy*dy)
  }

}

