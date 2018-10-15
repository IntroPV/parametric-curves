package ar.pablitar.intropv

import ar.com.pablitar.libgdx.commons.traits.SimplePositioned
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.{Path, Vector2}

class PathTraveler(curve: Path[Vector2]) extends SimplePositioned(curve.valueAt(new Vector2(), 0)) {
  var traveling = false

  var t: Float = 0
  val traverseTime = 1.5f
  val speed = 200f

  def direction: Float = curve.derivativeAt(new Vector2(), t).angle()

  def toggleTraveling() = traveling = !traveling

  def update(delta: Float): Unit = {
    if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
      toggleTraveling()
    }

    if(traveling) {
      t += delta / traverseTime
//      t += (delta * speed) / curve.derivativeAt(new Vector2(), t).len()

      if (t >= 1) t = 0
    }

    position = curve.valueAt(this.position, t)
  }
}
