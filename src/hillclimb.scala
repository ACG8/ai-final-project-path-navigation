import model.State

import scala.collection.mutable

/**
  * Created by Richard on 4/24/2017
  * k-hill-climbing generates all the k-successors of the current state, and 
  * picks the one to expand with the greatest value of the heuristic function
  *
  */

object hillclimb {
	// start is the starting state and h is the heuristic function
  def bestKDescendant[T <: State[T]](n : T, k : Int, h: T => Double): Double = k match {
    case 0 => h(n)
    case j => n.successors().map( m => bestKDescendant(m, k - 1, h) ).min()
  }
  def next[T <: State[T]](n: T, k : Int, h: T => Double): List[T] = {
    n.successors().map( m => (m, bestKDescendant(m, k-1, h) ).reduceLeft( case ((m1, h1), (m2, h2)) => if (h1 < h2) (m1, h1) else (m2, h2) )
  }
  def hillclimb[T <: State[T]](start: T, h: T => Double): List[T] = {
    Nil
  }
}
