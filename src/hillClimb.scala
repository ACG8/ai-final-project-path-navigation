import model.State

import scala.collection.mutable

/**
  * Created by Richard on 4/24/2017
  * k-hill-climbing generates all the k-successors of the current state, and 
  * picks the one to expand with the greatest value of the heuristic function
  *
  */

object hillClimb {
	//start is the starting state and h is the heuristic function
  def bestKDescendant[T <: State[T]](n : T, k : Int, h: T => Double): Double = k match {
    case 0 => h(n)
    case _ => n.successors().map( m => bestKDescendant(m, k - 1, h) ).min
  }
  // next returns the best k-descendant of n with heuristic h
  def next[T <: State[T]](n: T, k : Int, h: T => Double): T = {
    val x : List[(T, Double)] = n.successors()
    x.map{ case (m: T, _ : Double) => (m, bestKDescendant(m, k-1, h) ) }
      .reduceLeft{ case ((m1 : T, h1 : Double), (m2 : T, h2 : Double)) => if (h1 < h2) (m1, h1) else (m2, h2) }._1
  }
  def hillClimb[T <: State[T]](start: T, k: Int, h: T => Double): List[T] = {
	  def hillClimbHelper(h: T => Double, k: Int, path: List[T]): List[T] = {
      val nxt = next(path.head, k, h)
      nxt match {
        case s if s.isGoalState  => (s :: path).reverse
        case s => if (h(s) >= h(path.head)) path.reverse else hillClimbHelper(h, k, s :: path)
      }
    }
    hillClimbHelper(h, k, List(start))
  }

}
