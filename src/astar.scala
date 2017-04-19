import model.State

/**
  * Created by Ananda on 4/18/2017.
  */

object StateOrder extends Ordering[(State,Double)] {
	def compare(x:(State,Double),y:(State,Double)) = x._2 compare y._2
}

object astar {
  //def astar[T <: State](state: T, heuristic: T => Double): List[T] = {
  //  state.successors()
  //}
  def astar(start: State, heuristic: State => Double): List[State] = {
  	// store the state-cost tuples in a priority queue ordered by cost
    var frontier = scala.collection.mutable.PriorityQueue.empty(StateOrder)
    // alternate between choosing a node to expand, checking to see if it is a goal state, and adding its successors to the frontier
  }
}