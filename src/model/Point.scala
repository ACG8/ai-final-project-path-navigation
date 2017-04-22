package model

/**
  * Created by agieg on 4/19/2017.
  */
class Point(_x: Int, _y: Int) {
  def x: Int = _x
  def y: Int = _y

  override def toString: String = {
    "("+this.x+", "+this.y+")"
  }

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: Point => obj.x == this.x && obj.y == this.y
      case _ => false
    }
  }
}
