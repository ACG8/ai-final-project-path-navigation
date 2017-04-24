package model

/**
  * Created by agieg on 4/18/2017.
  */
class PathState(grid: Grid, _position: Point, goals: Point*) extends State[PathState] {
  def position = _position

  def isGoalState: Boolean = goals.contains(position)

  // The second value is the cost of reaching the state from the current state.
  def successors: List[(PathState,Double)] = {
    grid.allVertices.map(v => new Line(position, v)) // all lines from current position to possible vertices
      .filter(line => !grid.overlaps(line))
      .map(line => (new PathState(grid, line.end, goals: _*), line.length)).toList
  }
  def asString: String = position.toString
}