package tutorial.macroidforhabr

import akka.actor.{ActorRef, Props}
import android.graphics.Color
import macroid.akka.FragmentActor
import macroid.akka.FragmentActor.{AttachUi, DetachUi}

import scala.util.Random

/**
  * Created by cheshirrrr on 27.12.2015.
  */
object TweakerActor{
  case object TweakHim
  case object TweakYou
  case class SetTweaked(target: ActorRef)

  def props(target: Option[ActorRef]) = Props(new TweakerActor(target))
}

class TweakerActor(var tweakTarget:Option[ActorRef]) extends FragmentActor[TweakerFragment]{
  import TweakerActor._

  def receive = receiveUi andThen {
    case TweakHim =>
      tweakTarget.foreach(_ ! TweakYou)

    case TweakYou =>
      val chosenColor = randomColor
      tweakTarget = Some(sender)
      withUi(f => f.receiveColor(chosenColor))

    case SetTweaked(target) => tweakTarget = Some(target)

    case AttachUi(_) =>

    case DetachUi =>
  }

  def randomColor: Int = {
    val random = new Random()
    val red = random.nextInt(255)
    val green = random.nextInt(255)
    val blue = random.nextInt(255)
    Color.rgb(red, green, blue)
  }


}
