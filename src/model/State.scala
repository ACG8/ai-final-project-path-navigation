package model

trait State {
	// This signature won't work because successors has to handle ANY kind of state (whatever T is)
	// a "PathState" can't return a list of "SudokuStates" as successors.
	//def successors[T <: State](): List[T]

	def successors(): List[State]
}