package tutorial.macroidforhabr

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.{Gravity, View}
import android.widget.{TextView, ImageView}
import macroid.FullDsl._
import macroid.{Tweak, AppContext, Snail}

trait Tweaks {
  def flashElement : Snail[View] = {
    fadeOut(500) ++ fadeIn(500) ++ fadeOut(500) ++ fadeIn(500)
  }

  def roundedDrawable(id: Int)(implicit appCtx: AppContext) = Tweak[ImageView]({
    val res = appCtx.app.getResources
    val rounded = RoundedBitmapDrawableFactory.create(res,BitmapFactory.decodeResource(res, id))
    rounded.setCornerRadius(Math.min(rounded.getMinimumWidth(),rounded.getMinimumHeight))
    _.setImageDrawable(rounded)
  })

  def drawable(id: Int)(implicit appCtx: AppContext) = Tweak[ImageView](_.setImageDrawable(loadDrawable(id)))
  def fitImage(implicit appCtx: AppContext) = Tweak[ImageView](_.setScaleType(ImageView.ScaleType.CENTER_INSIDE))
  def gravityCenter(implicit appCtx: AppContext) = Tweak[TextView](_.setGravity(Gravity.CENTER))

  def loadDrawable(id: Int)(implicit appCtx: AppContext) : Drawable = {
    val res = appCtx.app.getResources
    res.getDrawable(id, appCtx.app.getTheme)
  }
}
