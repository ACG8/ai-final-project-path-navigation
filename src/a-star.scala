def astar[T <: State](state:T,heuristic:T=>Float): List[T] = {
	state.successors()
}