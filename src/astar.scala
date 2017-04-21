import model.State

/**
  * Created by Ananda on 4/18/2017.
  */

/**
  * The frontier is a list of lists of states, each state-list paired with a double representing the actual cost of 
  * getting to the head:
  * List[List[State],Double,Double]
  * The first double is the heuristic estimate of the cost (for sorting) and the third is the cost g to reach this state
  * The head of the list is the most recent state, and the last element is the first state in the path
  */
object StateOrder extends Ordering[(List[State],Double,Double)] {
	def compare(x:(List[State],Double,Double),y:(List[State],Double,Double)) = y._2 compare x._2 // ascending order by second element
}

object astar {
	// start is the starting state and h is the heuristic function
  def astar(start: State, h: State => Double): List[State] = {
  	// store the state-cost tuples in a priority queue ordered by cost ascending
    var frontier = scala.collection.mutable.PriorityQueue.empty(StateOrder)
    frontier += ((List(start),h(start),0.0)) //start at the start node
    var path = List.empty //our solution
    // alternate between choosing a node to expand, checking to see if it is a goal state, and adding its successors to the frontier
    while (!frontier.isEmpty && path.isEmpty) {
    	val current_path,_,g = frontier.head
    	val current_state = current_path._1.head
    	frontier = frontier.tail
    	//g is the cost of getting to the current state
    	if (current_state.isGoalState)
   			path = current_path._1.reverse
    	else
    		frontier += current_state.successors map({ case (s, c) => (s::current_path, c + g + h(s), c + g) })
    }
    path
  }
}