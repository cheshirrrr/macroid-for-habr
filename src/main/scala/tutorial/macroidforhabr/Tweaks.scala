package tutorial.macroidforhabr

import android.view.View
import macroid.FullDsl._
import macroid.{ Snail}

trait Tweaks {
  def flashElement : Snail[View] = {
    fadeOut(500) ++ fadeIn(500) ++ fadeOut(500) ++ fadeIn(500)
  }
}
