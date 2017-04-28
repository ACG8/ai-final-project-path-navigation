package algorithms

import model.State

import scala.collection.mutable

/**
  * Created by Ananda on 4/27/2017.
  *
  * The frontier is a list of lists of states, each state-list paired with a double representing the actual cost of 
  * getting to the head:
  * List[List[State],Double,Double]
  * The first double is the heuristic estimate of the cost (for sorting) and the third is the cost g to reach this state
  * The head of the list is the most recent state, and the last element is the first state in the path
  */
abstract class GBFStateOrder[T <: State[T]] extends Ordering[(List[T],Double)] {
	def compare(x:(List[T],Double),y:(List[T],Double)) = y._2 compare x._2 // ascending order by second element
}

object greedybestfirst {
	// start is the starting state and h is the heuristic function
  def greedybestfirst[T <: State[T]](start: T, h: T => Double): List[T] = {
  	// store the state-cost tuples in a priority queue ordered by cost ascending
    val frontier_ordering = new GBFStateOrder[T]() {}
    var frontier: mutable.PriorityQueue[(List[T], Double)] = mutable.PriorityQueue.empty(frontier_ordering)
    frontier += ((List(start),h(start))) //start at the start node
    var path: List[T] = List.empty //our solution
    // alternate between choosing a node to expand, checking to see if it is a goal state, and adding its successors to the frontier
    while (frontier.nonEmpty && path.isEmpty) {
    	val current_path: List[T] = frontier.head._1
    	val current_state = current_path.head
    	frontier = frontier.tail
    	//g is the cost of getting to the current state
    	if (current_state.isGoalState)
   			path = current_path.reverse
    	else
        frontier = frontier ++ current_state
          .successors
          .map{
            case (next,_) => (next::current_path, h(next))
          }
      }
    path
  }
}