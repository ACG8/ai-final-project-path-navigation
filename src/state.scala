trait State {
	def successors[T <: State](): List[T]
}