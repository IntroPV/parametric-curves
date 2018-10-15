package ar.pablitar.intropv

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

trait DragStatus {
  def update(delta: Float): Unit = {

  }
}

case object NotDragging extends DragStatus

case class Dragging(screen: ParametricCurvesScreen, point: Vector2) extends DragStatus {
  override def update(delta: Float): Unit = {
    if (!Gdx.input.isTouched) {
      screen.dragStatus = NotDragging
    } else {
      point.x = Gdx.input.getX()
      point.y = screen.SCREEN_HEIGHT - Gdx.input.getY()
    }
  }
}
