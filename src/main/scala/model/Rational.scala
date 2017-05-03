package main.scala.model

/**
  * Created by agieg on 4/28/2017.
  */
object Rational {
  def max(r1: Rational, r2: Rational): Rational = {
    if (r1 > r2) r1 else r2
  }
  def min(r1: Rational, r2: Rational): Rational = {
    if (r1 < r2) r1 else r2
  }
}
class Rational(_num: Long, _denom: Long) extends Ordered[Rational]{
  def this(n: Long) {
    this(n, 1)
  }

  def gcd(x: Long, y: Long): Long = {
    if(y == 0) x else gcd(y, x % y)
  }

  val g: Long = gcd(_num,_denom)
  val num: Long = _num/g
  val denom: Long =  _denom/g

  def +(r2: Rational): Rational = new Rational(this.num*r2.denom + r2.num*this.denom, this.denom*r2.denom)
  def -(r2: Rational): Rational = new Rational(this.num*r2.denom - r2.num*this.denom, this.denom*r2.denom)
  def *(r2: Rational): Rational = new Rational(this.num*r2.num, this.denom*r2.denom)
  def /(r2: Rational): Rational = new Rational(this.num*r2.denom, this.denom*r2.num)

  override def >(r2: Rational): Boolean = this.num*r2.denom > this.denom*r2.num
  override def <(r2: Rational): Boolean = this.num*r2.denom < this.denom*r2.num
  override def <=(r2: Rational): Boolean = this < r2 || this == r2
  override def >=(r2: Rational): Boolean = this > r2 || this == r2
  override def compare(that: Rational): Int = (this.num*that.denom).compare(this.denom*that.num)

  def toDouble: Double = this.num.toDouble/this.denom
  def toFloat: Float = this.num.toFloat/this.denom
  def round: Long = math.round(this.toDouble).toLong

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: Rational => this.num * obj.denom == this.denom * obj.num
      case _ => false
    }
  }

  override def toString: String = this.num + (if (this.denom ==1) "" else "/"+this.denom)
}
