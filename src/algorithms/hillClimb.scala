package algorithms

import model.State

/**
  * Created by Richard on 4/24/2017
  * k-hill-climbing generates all the k-successors of the current state, and 
  * picks the one to expand with the greatest value of the heuristic function
  *
  */

object hillClimb {
  val DELTA = 0.0001
  // a record type to make things easier to read.
  case class StateBundle[T <: State[T]](state : T, kHeuristic : Double, relCost : Double )
	//start is the starting state and h is the heuristic function

  def bestKDescendant[T <: State[T]](n : T, k : Int, h: T => Double): Double = k match {
    case 0 => h(n)
    case _ => {
      if (n.isGoalState) 0.0 else n.successors.map{case(m : T, _ : Double) => bestKDescendant(m, k - 1, h) }.min
    }
  }

  // a utility method

  // next returns a StateBundle containing the immediate successor of n
  // with best (k - 1) descendant, and the relative cost of moving to that node

  def next[T <: State[T]](n: T, k : Int, h: T => Double): StateBundle[T] = {
    def minState( x1 : StateBundle[T], x2 : StateBundle[T]) : StateBundle[T] = {
      if ( Math.abs(x1.kHeuristic - x2.kHeuristic) < DELTA)
        if(h(x1.state) < h(x2.state)) x1 else x2
      else
      if(x1.kHeuristic < x2.kHeuristic) x1 else x2
    }
    val x: List[(T, Double)] = n.successors
    for (s <- x) {
      if (s._1.isGoalState) return StateBundle(s._1, -1.0, s._2)
    }
    x.map { case (m: T, relCost: Double) => StateBundle[T](m, bestKDescendant(m, k - 1, h), relCost) }
        .reduceLeft {
          minState
        }

  }
  // hillClimb returns the path, the number of iterations used, and the total cost of the path.
  // In case of failure, it returns (Nil, 0, 0.0)
  def hillClimb[T <: State[T]](start: T, k: Int, h: T => Double): (List[T], Int, Double) = {
	  def hillClimbHelper(h: T => Double, k: Int, path: List[T], numIter: Int, totalPathCost: Double, timeout: Int): (List[T], Int, Double) = {
      if(timeout == 0) return (Nil, 0, 0.0)
      var nxt = StateBundle(start,0,0.0)
      try {
        nxt = next(path.head, k, h)
      } catch {
        case _ : Throwable => return (Nil, 0, 0.0)
      }
        println("The current node is " + path.head.toString)
        println("The candidate next node is " + nxt.state.toString)
        println("The current node's heuristic is " + h(path.head))
        println("The candidate next node's k-descendant heuristic is " + nxt.kHeuristic)
        nxt match {
          case StateBundle(s, _, relCost) if s.isGoalState  => ((s :: path).reverse, numIter, totalPathCost + relCost)
          case StateBundle(_, heur, _) if (heur >= h(path.head)) => (Nil, 0, 0.0)
          case StateBundle(s, _, relCost) => hillClimbHelper(h, k, s :: path, numIter + 1, totalPathCost + relCost, timeout - 1)
        }
      }

    hillClimbHelper(h, k, List(start), 0, 0.0, start.size)
  }
  // kHillClimb, given an int k, gives the k-lookahead algorithm
  def kHillClimb[T <: State[T]](k : Int) : (T, T => Double) => (List[T], Int, Double)
    = (start : T, h : T => Double) => hillClimb(start, k, h)
}
