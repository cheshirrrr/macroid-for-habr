package tutorial.macroidforhabr

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.{Button, LinearLayout, TextView}
import macroid._
import macroid.FullDsl._
import macroid.akka.AkkaActivity
import macroid.support.FragmentApi.modernFragmentApi._
import macroid.contrib.LpTweaks._

import scala.concurrent.ExecutionContext.Implicits.global

class MainActivity extends FragmentActivity with Contexts[FragmentActivity]  with AkkaActivity with Contacts with Tweaks with IdGeneration{

  var textView = slot[TextView]
  var layout = slot[LinearLayout]

  val actorSystemName = "tutorialsystem"

  lazy val tweakerOne = actorSystem.actorOf(TweakerActor.props(None), "tweakerOne")
  lazy val tweakerTwo = actorSystem.actorOf(TweakerActor.props(Some(tweakerOne)), "tweakerTwo")

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    // инициализируем акторы
    (tweakerOne,tweakerTwo)

    val view = l[LinearLayout](
      w[TextView] <~ text("Просто надпись") <~ wire(textView),
      w[Button] <~ text("Нажми меня") <~ On.click(changeTextAndShowFragment),
      l[LinearLayout](
        f[TweakerFragment].pass("name" -> "tweakerOne").framed(Id.tweakerOne, Tag.tweakerOne),
        f[TweakerFragment].pass("name" -> "tweakerTwo").framed(Id.tweakerTwo, Tag.tweakerTwo)
      ) <~ horizontal,
      l[LinearLayout](
        w[TextView] <~ text("Мигающий лэйаут"),
        f[ListableFragment].framed(Id.contactList, Tag.contactList) <~ matchWidth
      ) <~ wire(layout) <~ vertical <~ id(Id.mainLayout) <~ matchWidth
    ) <~ vertical

    setContentView(getUi(view))
  }

  override def onStart() = {
    super.onStart()

    tweakerOne ! TweakerActor.SetTweaked(tweakerTwo)
  }

  def changeTextAndShowFragment : Ui[Any] = {
    (textView <~ text("Помигаем?")) ~ (layout <~~ flashElement) ~~ (textView <~ text("Ну и хватит")) ~~ replaceListableWithSlotted
  }

  def replaceListableWithSlotted = Ui {
    activityManager(this).beginTransaction().replace(Id.contactList,new SlottedListableFragment,Tag.slottedList).commit()
  }
}
