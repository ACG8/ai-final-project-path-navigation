package algorithms

import model.State

/**
  * Created by Richard on 4/24/2017
  * k-hill-climbing generates all the k-successors of the current state, and 
  * picks the one to expand with the greatest value of the heuristic function
  *
  */

object hillClimb {
  case class StateBundle[T <: State[T]](state : T, kHeuristic : Double, relCost : Double )
	//start is the starting state and h is the heuristic function

  def bestKDescendant[T <: State[T]](n : T, k : Int, h: T => Double): Double = k match {
    case 0 => h(n)
    case _ => {
      if (n.isGoalState) 0.0 else n.successors.map{case(m : T, _ : Double) => bestKDescendant(m, k - 1, h) }.min
    }
  }

  // a utility method
  def minState[T <: State[T]]( x1 : StateBundle[T], x2 : StateBundle[T]) : StateBundle[T] = {
    if (x1.kHeuristic < x2.kHeuristic) x1 else x2
  }

  // next returns a StateBundle containing the immediate successor of n
  // with best (k - 1) descendant, and the relative cost of moving to that node

  def next[T <: State[T]](n: T, k : Int, h: T => Double): StateBundle[T] = {
    val x: List[(T, Double)] = n.successors
    x.map { case (m: T, relCost : Double) => StateBundle[T](m, bestKDescendant(m, k - 1, h), relCost) }
      .reduceLeft {
        minState[T]
      }
  }
  // hillClimb returns the path,
  def hillClimb[T <: State[T]](start: T, k: Int, h: T => Double): (List[T], Int, Double) = {
	  def hillClimbHelper(h: T => Double, k: Int, path: List[T], numIter: Int, totalPathCost: Double): (List[T], Int, Double) = {
      val nxt = next(path.head, k, h)
      println("The current node's heuristic is " + h(path.head))
      println("The candidate next node's k-descendant heuristic is " + nxt.kHeuristic);
      nxt match {
        case StateBundle(s, _, relCost) if s.isGoalState  => ((s :: path).reverse, numIter, totalPathCost + relCost)
        case StateBundle(_, heur, _) if (heur >= h(path.head)) => (Nil, 0, 0.0)
        case StateBundle(s, _, relCost) => hillClimbHelper(h, k, s :: path, numIter + 1, totalPathCost + relCost)
      }
    }
    hillClimbHelper(h, k, List(start), 0, 0.0)
  }
  def kHillClimb[T <: State[T]](k : Int) : (T, T => Double) => (List[T], Int, Double)
    = (start : T, h : T => Double) => hillClimb(start, k, h)
}
