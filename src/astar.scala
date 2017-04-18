import model.State

object astar {
  //def astar[T <: State](state: T, heuristic: T => Double): List[T] = {
  //  state.successors()
  //}
  def astar(state: State, heuristic: State => Double): List[State] = {
    state.successors()
  }
}