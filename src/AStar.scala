import model.State

// put in class to make it compile
class AStar {
  def astar[T <: State](state: T, heuristic: T => Double): List[T] = {
    // stub to make method compile
    List(state)
  }
}