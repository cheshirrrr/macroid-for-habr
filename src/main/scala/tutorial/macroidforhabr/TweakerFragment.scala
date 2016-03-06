package tutorial.macroidforhabr

import android.graphics.Color
import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup}
import android.widget.{Button, FrameLayout}
import macroid.FullDsl._
import macroid.akka.AkkaFragment
import macroid.contrib.TextTweaks._
import macroid.{Contexts, Ui}

import scala.util.Random

/**
  * Created by cheshirrrr on 27.12.2015.
  */
class TweakerFragment extends AkkaFragment with Contexts[AkkaFragment]{

  lazy val actorName = getArguments.getString("name")

  lazy val actor = Some(actorSystem.actorSelection(s"/user/$actorName"))

  var button = slot[Button]

  def receiveColor(textColor: Int) =
    button  <~ color(textColor)

  def tweak =
  Ui(actor.foreach(_ ! TweakerActor.TweakHim))

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = getUi {
    l[FrameLayout](
      w[Button] <~ wire(button) <~ text("TweakHim") <~ On.click(tweak)
    )
  }

}
